package com.dhy.xintent;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.view.View;

import com.dhy.xintent.interfaces.IFindViewById;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Optional;

public class GenCode {
    @Test
    public void testMe() throws FileNotFoundException {
        File file = getJavaFileByClass(XCommonBase.class);
        if (file.exists()) {
            FileInputStream in = new FileInputStream(file);
            CompilationUnit cu = JavaParser.parse(in);
            cu.getType(0).getMethods();
            cu.accept(new MethodVisitor(), null);
        }else {
            System.out.println("file not found");
        }
    }

    private static class MethodVisitor extends VoidVisitorAdapter<Void> {
        @Override
        public void visit(MethodDeclaration n, Void arg) {
            /* here you can access the attributes of the method.
             this method will be called for all methods in this
             CompilationUnit, including inner class methods */
            Optional<AnnotationExpr> annotation = n.getAnnotationByClass(com.dhy.xintent.annotation.GenCode.class);
            if (annotation.isPresent()) {
                testF(n);
            }

            super.visit(n, arg);
        }
    }

    static void testF(MethodDeclaration m) {
        if (m.getComment().isPresent()) {
            m.removeComment();
        }
        StringBuilder sb = new StringBuilder();
        MethodDeclaration method = m.clone();
        Parameter parameter = method.getParameter(0);
        for (Class cls : classes) {
            parameter.setType(cls);
            sb.append("\n").append(method.toString());
        }
        sb.deleteCharAt(0);
        String s = sb.toString();
        s = s.replaceAll("@GenCode", "");
        System.out.println(s);
    }

    private static final Class[] classes = {Dialog.class, View.class, IFindViewById.class};

    File getJavaFileByClass(Class cls) {
        final String rootPath = new File("").getAbsolutePath() + "\\xintent\\src\\main\\java";
        String path = cls.getName().replaceAll("\\.", "\\\\") + ".java";
        return new File(rootPath + "\\" + path);
    }

}
