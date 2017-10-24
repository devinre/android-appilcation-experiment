package com.example.dh.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private final static String PIC_URL="http://ww2.sinaimg.cn/large/7a8aed7bgw1evshgr5z3oj20hs0qo0vq.jpg";
    private final static String HTML_URL="http://www.cc98.org";
    private Bitmap bitmap;
    private String detail="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.one:
                Log.i("detail","oh yeah");
                new Thread(){
                    public void run(){
                        try{
                            byte[] data=GetData.getImage(PIC_URL);
                            bitmap = BitmapFactory.decodeByteArray(data,0,data.length);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        handler.sendEmptyMessage(0x001);
                    }
                }.start();
                break;
            case R.id.two:
                new Thread(){
                    public void run(){
                        try{
                            detail=GetData.getHtml(HTML_URL);
                            Log.i("detail",detail);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        handler.sendEmptyMessage(0x002);
                    }
                }.start();
                break;
            case R.id.three:
                Log.i("detail",detail);
                if(detail.equals("")){
                    Toast.makeText(MainActivity.this,"先请求HTML先嘛~",Toast.LENGTH_SHORT).show();
                }else{
                    handler.sendEmptyMessage(0x003);
                }
                break;
        }
    }
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0x001:
                    hideAllWidget();
                    wb_tobaidu.setVisibility(View.GONE);
                    tv_tobaiduhtml.setVisibility(View.GONE);
                    iv_getimage.setVisibility(View.VISIBLE);
                    iv_getimage.setImageBitmap(bitmap);
                    Toast.makeText(HttpURLConnectionActivity.this, "图片加载完毕", Toast.LENGTH_SHORT).show();
                    break;
                case 0x002:
                    wb_tobaidu.setVisibility(View.GONE);
                    iv_getimage.setVisibility(View.GONE);
                    tv_tobaiduhtml.setVisibility(View.VISIBLE);
                    tv_tobaiduhtml.setText(detail);
                    Toast.makeText(HttpURLConnectionActivity.this, "HTML代码加载完毕", Toast.LENGTH_SHORT).show();
                    break;
                case 0x003:
                    tv_tobaiduhtml.setVisibility(View.GONE);
                    iv_getimage.setVisibility(View.GONE);
                    wb_tobaidu.setVisibility(View.VISIBLE);
                    wb_tobaidu.loadDataWithBaseURL("", detail, "text/html", "UTF-8", "");
                    Toast.makeText(HttpURLConnectionActivity.this, "网页加载完毕", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }

        ;
    };
}
