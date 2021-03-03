package com.example.annotation_compiler;

import com.example.annotation.BindPath;
import com.google.auto.service.AutoService;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.annotation.processing.Processor;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 * author : quzongyang
 * e-mail : quzongyang@xiaohe.com
 * time   : 2021/03/02
 * desc   : 创建注解处理器，去代码中找到专门用来标记Activity的注解，
 *          得到它所标记的类。并生成ActivityUtil
 * version: 1.0
 */

@AutoService(Processor.class)
public class AnnotationCompiler extends AbstractProcessor {

    //生成java文件的工具
    Filer filer;
    Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnvironment.getFiler();
        messager = processingEnvironment.getMessager();
    }

    /**
     * 生命支持的java版本
     * @return
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return processingEnv.getSourceVersion();
    }

    /**
     * 声明这个注解处理器需要找的注解,其他注解过滤掉
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new HashSet<>();
        types.add(BindPath.class.getCanonicalName());
        return types;
    }

    /**
     * 这就是核心方法，去程序中找标记了注解的实现都在这个方法
     * @param set
     * @param roundEnvironment
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
       //得到所有依赖了注解类库的模块中标记了BindPath注解的内容
        Set<? extends Element> elementsAnnotation = roundEnvironment.getElementsAnnotatedWith(BindPath.class);
        //      PackageElement  类节点
        //      VariableElement  成员变量节点
        //      ExecutableElement   方法节点
        //      TypeElement 包节点
        Map<String,String> map = new HashMap<>();
        for(Element element : elementsAnnotation){
            TypeElement typeElement = (TypeElement) element;
            String key = typeElement.getAnnotation(BindPath.class).key();
            //获取到包名和类名
            String activityName = typeElement.getQualifiedName().toString();
            map.put(key , activityName);
        }
        //生成文件的操作
        if(map.size()>0){
            //生成文件
            //createClass(map);
            Writer writer = null;
            try {
                String utilName = "ActivityUtil" + System.currentTimeMillis();
                //去生成java文件
                JavaFileObject sourceFile = filer.createSourceFile(
                        "com.example.util." + utilName);
                writer = sourceFile.openWriter();
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("package com.example.util;\n");
                stringBuffer.append("import com.example.arouter.IRouter;\n");
                stringBuffer.append("import com.example.arouter.ARouter;\n");
                stringBuffer.append("public class " + utilName + " implements IRouter {\n");
                stringBuffer.append("@Override\n");
                stringBuffer.append("public void putActivity() {\n");
                Iterator<String> iterator = map.keySet().iterator();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    String activityName = map.get(key);
                    stringBuffer.append("ARouter.getInstance().addActivity(\"" + key + "\"," + activityName + ".class);");
                }
                stringBuffer.append("}\n}");
                writer.write(stringBuffer.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
        return false;
    }

    /*private void createClass(Map<String,String> map) {
        try {
            //创建一个方法
            MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("putActivity")
                    .addModifiers(Modifier.PUBLIC)
                    .returns(void.class);
            //创建方法体
            Iterator<String> iterator = map.keySet().iterator();
            while (iterator.hasNext()){
                String key = iterator.next();
                String activityName = map.get(key);
                methodBuilder.addStatement("com.example.arouter.ARouter.getInstance().addActivity(\""+key+"\","+activityName+")");
            }
            MethodSpec methodSpec = methodBuilder.build();

            //获取到接口类
            ClassName iRouter = ClassName.get(" com.example.arouter","IRouter");

            //创建类对象  并添加接口  方法体
            TypeSpec typeSpec = TypeSpec.classBuilder("ActivityUtil"+System.currentTimeMillis())
                    .addModifiers(Modifier.PUBLIC)
                    .addSuperinterface(iRouter)
                    .addMethod(methodSpec)
                    .build();

            //构建目录对象
            JavaFile javaFile = JavaFile.builder("com.example.util",typeSpec).build();
            javaFile.writeTo(filer);
        } catch (IOException e) {
            System.out.println("IOException---->");
            e.printStackTrace();
        }
    }*/
}
