package com.example.dh.myapplication;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity implements Runnable{
    private TextView textView2;
    private String content="";
    private EditText editText;
    private Button btnsend;
    private Button btnconnect;
    private Socket socket=null;
    private static final String HOST="10.0.2.2";
    private static final int PORT=12345;
    private BufferedReader in=null;
    private PrintWriter out=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView2=(TextView)findViewById(R.id.textView2);
        editText=(EditText)findViewById(R.id.editText);
        btnsend=(Button)findViewById(R.id.btnsend);
        btnconnect=(Button)findViewById(R.id.btnconnect);
        btnconnect.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                new Thread(){
                    public void run(){
                        try{
                            socket=new Socket(HOST,PORT);
                            in=new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
                            out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                        new Thread(MainActivity.this).start();
                    }
                }.start();
                btnconnect.setEnabled(false);
            }
        });
        btnsend.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String msg=editText.getText().toString();
                if(socket.isConnected()){
                    if(!socket.isOutputShutdown()){
                        out.println(msg);
                    }
                }
            }
        });
    }
    public Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(msg.what==0x123){
                textView2.setText(textView2.getText().toString()+content);
            }
        }
    };
    @Override
    public void run(){
        try{
            while(true){
                if(socket.isConnected()){
                    if(!socket.isInputShutdown()){
                        if((content=in.readLine())!=null){
                            content+="\n";
                            handler.sendEmptyMessage(0x123);
                        }
                    }
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
