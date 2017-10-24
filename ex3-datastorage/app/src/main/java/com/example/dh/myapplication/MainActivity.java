package com.example.dh.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText editTextName;
    private EditText editTextContent;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext=this;
        Button btnReadInternal = (Button) findViewById(R.id.readbutton);
        Button btnWriteInternal = (Button) findViewById(R.id.writebutton);
        Button btnReadSd=(Button)findViewById(R.id.sdreadbutton);
        Button btnWriteSd=(Button)findViewById(R.id.sdwritebutton);
        Button btnwspbutton=(Button)findViewById(R.id.wspbutton);
        Button btnrspbutton=(Button)findViewById(R.id.rspbutton);
        Button btnjumpToSQLiteActivity=(Button)findViewById(R.id.jumpToSQLiteActivity);
        editTextName=(EditText)findViewById(R.id.editText);
        editTextContent=(EditText)findViewById(R.id.editText2);
        btnWriteInternal.setOnClickListener(this);
        btnReadInternal.setOnClickListener(this);
        btnReadSd.setOnClickListener(this);
        btnWriteSd.setOnClickListener(this);
        btnwspbutton.setOnClickListener(this);
        btnrspbutton.setOnClickListener(this);
        btnjumpToSQLiteActivity.setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
        int id=v.getId();
        switch(id){
            case R.id.readbutton:
                FileHelper fh2=new FileHelper(mContext);
                try{
                    String fname=editTextName.getText().toString();
                    String fcontent=fh2.read(fname);
                    Toast.makeText(mContext,fcontent,Toast.LENGTH_SHORT).show();
                }catch (IOException e){
                    e.printStackTrace();
                    Toast.makeText(mContext,"数据读取失败",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.writebutton:
                FileHelper fh=new FileHelper(mContext);
                String filename=editTextName.getText().toString();
                String fileContent =editTextContent.getText().toString();
                try{
                    fh.save(filename,fileContent);
                    Toast.makeText(getApplicationContext(),"数据写入成功",Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"数据写入失败",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.sdwritebutton:
                String filename2=editTextName.getText().toString();
                String fileContent2 =editTextContent.getText().toString();
                if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                    try {
                        filename2 = Environment.getExternalStorageDirectory().getCanonicalPath() + "/" + filename2;
                        FileOutputStream output = new FileOutputStream(filename2);
                        output.write(fileContent2.getBytes());
                        output.close();
                        Toast.makeText(getApplicationContext(),"数据写入成功",Toast.LENGTH_SHORT).show();
                    }catch(Exception e){
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),"数据写入失败",Toast.LENGTH_SHORT).show();
                    }
                }else
                    Toast.makeText(mContext,"SD卡不存在或者不可读写",Toast.LENGTH_LONG).show();
                break;
            case R.id.sdreadbutton:
                String fname2=editTextName.getText().toString();
                if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    try {
                        fname2 = Environment.getExternalStorageDirectory().getCanonicalPath() + "/" + fname2;
                        FileInputStream input = new FileInputStream(fname2);
                        byte[] temp = new byte[1024];
                        int num = 0;
                        StringBuilder sb = new StringBuilder();
                        while ((num = input.read(temp)) > 0) {
                            sb.append(new String(temp, 0, num));
                        }
                        input.close();
                        Toast.makeText(mContext, sb.toString(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(mContext, "数据读取失败", Toast.LENGTH_SHORT).show();
                    }
                }else
                    Toast.makeText(mContext,"SD卡不存在或者不可读写",Toast.LENGTH_LONG).show();
                break;
            case R.id.wspbutton:
                try{
                    SharedPreferencesHelper sph=new SharedPreferencesHelper(mContext,"Test_SharedPreferencesFile");
                    sph.addKeyValuePair("key1","value1");
                    sph.addKeyValuePair("key2","value2");
                    sph.addKeyValuePair(editTextName.getText().toString(),editTextContent.getText().toString());
                    sph.save();
                    Toast.makeText(mContext,"写入SP成功",Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    e.printStackTrace();
                    Log.i("Lin","Write SharedPreferences error"+e.toString());
                    Toast.makeText(mContext,"数据写入失败",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.rspbutton:
                try{
                    int cnt=0;
                    SharedPreferencesHelper sph2=new SharedPreferencesHelper(mContext,"Test_SharedPreferencesFile");
                    for(Map.Entry<String,?> entry:sph2.read().entrySet()){
                        cnt+=1;
                        Log.i("Lin",entry.getKey()+":"+entry.getValue());
                    }
                    Toast.makeText(mContext,"数据读取成功，一共"+cnt+" pair keyvalues",Toast.LENGTH_SHORT).show();
                    Toast.makeText(mContext,editTextName.getText().toString()+
                    " : "+
                    sph2.getSPValueByKey(editTextName.getText().toString()),
                            Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    e.printStackTrace();
                    Log.i("Lin","Read from SharedPreferences error: "+e.toString());
                    Toast.makeText(mContext,"数据读取失败",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.jumpToSQLiteActivity:
                startActivity(new Intent(MainActivity.this,SQLiteActivity.class));
                Toast.makeText(mContext,"旋转~跳跃~",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
