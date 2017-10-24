package com.example.dh.myapplication;
import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * Created by dh on 2017/10/16.
 */

public class FileHelper {
    private Context mcontext;

    public FileHelper() {
    }

    public FileHelper(Context mcontext) {

        this.mcontext = mcontext;
    }

    //寫入資料
    public void save(String filename,String filemessage) throws Exception{


        FileOutputStream output = mcontext.openFileOutput(filename,mcontext.MODE_PRIVATE);
        output.write(filemessage.getBytes());
        output.close();

    }
    public String read(String filename) throws IOException{
        FileInputStream input = mcontext.openFileInput(filename);
        byte[] temp = new byte[1024];
        int num = 0;
        StringBuilder sb = new StringBuilder();
        while ((num =input.read(temp))>0){
            sb.append(new String(temp,0,num));
        }
        input.close();
        return sb.toString();

    }
}
