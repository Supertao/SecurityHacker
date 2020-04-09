package com.buglab;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class  RuntimeUtils {


    public static InputStream runExec(String[] cmd){
        InputStream inStream=null;
        Runtime runtime=Runtime.getRuntime();
        try{
            Process process=runtime.exec(cmd);
            InputStream errStream=process.getErrorStream();
            inStream=process.getInputStream();
        }catch (Exception e){
            e.printStackTrace();
        }

        return inStream;

    }


}
