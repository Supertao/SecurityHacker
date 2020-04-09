package com.buglab;

import java.io.InputStream;


public class RuntimeUtils {


    public static InputStream runExec(String[] cmd) {
        InputStream inStream = null;
        Runtime runtime = Runtime.getRuntime();
        try {
            Process process = runtime.exec(cmd);
            inStream = process.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return inStream;

    }


}
