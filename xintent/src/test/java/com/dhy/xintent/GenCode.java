package com.dhy.xintent;

import android.app.Dialog;
import android.view.View;

import com.dhy.xintent.interfaces.IFindViewById;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

public class GenCode {
    @Test
    public void genCode() throws FileNotFoundException {
//        File file = getJavaFileByClass(XCommonBase.class);
//        if (file.exists()) {
//            FileInputStream in = new FileInputStream(file);
//            CompilationUnit cu = new JavaParser().parse(in).getResult().get();
//            Map<String, StringBuilder> map = new HashMap<>();
//            for (MethodDeclaration method : cu.getType(0).getMethods()) {
//                if (isGenCode(method)) printGenCode(map, method);
//            }
//            printGenCode(map);
//        } else {
//            System.out.println("file not found");
//        }
    }

    private void printGenCode(Map<String, StringBuilder> map, MethodDeclaration m) {
        StringBuilder sb = map.get(m.getName().toString());
        if (sb == null) {
            sb = new StringBuilder();
            map.put(m.getName().toString(), sb);
        }
        if (m.getComment().isPresent()) {
            m.removeComment();
        }
        MethodDeclaration method = m.clone();
        Parameter parameter = method.getParameter(0);
        for (Class cls : classes) {
            parameter.setType(cls);
            sb.append("\n").append(method.toString());
        }
    }

    private void printGenCode(Map<String, StringBuilder> map) {
        StringBuilder sb = new StringBuilder();
        for (String key : map.keySet()) {
            sb.append("\n\t//region " + key);
            sb.append(map.get(key));
            sb.append("\n\t//endregion ");
        }
        sb.deleteCharAt(0);
        String s = sb.toString();
        String name = com.dhy.xintent.annotation.GenCode.class.getSimpleName();
        s = s.replaceAll("@" + name, "");
        System.out.println(s);
    }

    private boolean isGenCode(MethodDeclaration method) {
        return method.getAnnotationByClass(com.dhy.xintent.annotation.GenCode.class).isPresent();
    }

    private static final Class[] classes = {Dialog.class, View.class, IFindViewById.class};

    private File getJavaFileByClass(Class cls) {
        final String rootPath = getModulePath() + ".src.main.java";
        String path = rootPath + "." + cls.getName();
        path = path.replace(".", File.separator);
        return new File(path + ".java");
    }

    /**
     * @return the path of module end with no File.separator
     */
    private String getModulePath() {
        String path = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        return path.substring(0, path.indexOf("build") - 1);
    }

    @Test
    public void testFile() {
        System.out.println(getJavaFileByClass(XCommon.class));
    }
}
