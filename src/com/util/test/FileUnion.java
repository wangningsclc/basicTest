package com.util.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * @Auth wn
 * @Date 2018/12/26
 */
public class FileUnion {

    public static void main(String[] args) {
        String dirPath = "E:\\资料\\公司资料\\需求任务\\P20181226\\lcs";
        File file = new File(dirPath);
        File unionFile = new File("E:\\资料\\公司资料\\需求任务\\P20181226\\all.sql");
        try {
            FileOutputStream outputStream = new FileOutputStream(unionFile);
            for(File fil : file.listFiles()){
                FileInputStream inputStream = new FileInputStream(fil);
                byte[] bytes = new byte[100];
                int len = 100;
                while ((len = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes,0, len);
                }
            }
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
