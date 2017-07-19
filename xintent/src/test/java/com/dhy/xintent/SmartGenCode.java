package com.dhy.xintent;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.regex.Pattern;

public class SmartGenCode {
    String rootPath = new File("").getAbsolutePath() + "\\xintent\\src\\main\\java";

    File getJavaFileByClass(Class cls) {
        String path = cls.getName().replaceAll("\\.", "\\\\") + ".java";
        return new File(rootPath + "\\" + path);
    }

    @Test
    public void testMe() throws IOException {
        File file = getJavaFileByClass(XCommonBase.class);
        String fileData = FileUtils.readFileToString(file, Charset.defaultCharset());
        String[] lines = fileData.split("\r\n");
        Pattern p = Pattern.compile("\\s+public\\sstatic\\s\\w+\\s\\w+\\(Activity\\sactivity.+");
        System.out.println("lines.length:" + lines.length);
        for (String line : lines) {
            if (line.contains("Activity activity")) {
                if (p.matcher(line).matches()) {
                    System.out.println(line);
                }
            }
        }
    }
}
