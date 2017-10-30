package com.example.dh.myapplication;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class MainActivity extends AppCompatActivity {
    private ImageView ivImage;
    private static String image_path =
            "http://ww4.sinaimg.cn/bmiddle/786013a5jw1e7akotp4bcj20c80i3aao.jpg";
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnDown = findViewById(R.id.btnDown);
        ivImage = findViewById(R.id.ivSinaImage);

        // 声明一个等待框以提示用户等待
        dialog=new ProgressDialog(this);
        dialog.setTitle("提示信息");
        dialog.setMessage("正在下载，请稍后...");

        btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 执行一个异步任务，并把图片地址以参数的形式传递进去
                new MyTask().execute(image_path);
            }
        });
    }
    // 以 String 类型的参数， Void 表示没有进度信息， Bitmap 表示异步任务返回一个位图
    public class MyTask extends AsyncTask<String, Void, Bitmap> {
        // 表示任务执行之前的操作
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //显示等待框
            dialog.show();
        }
        //主要是完成耗时操作
        @Override
        protected Bitmap doInBackground(String... params) {
            HttpClient httpClient=new DefaultHttpClient();
            HttpGet httpGet=new HttpGet(params[0]);
            Bitmap bitmap=null;
            try {
                //从网络上下载图片
                HttpResponse httpResponse =httpClient.execute(httpGet);
                if(httpResponse.getStatusLine().getStatusCode()==200){
                    HttpEntity httpEntity = httpResponse.getEntity();
                    byte[] data= EntityUtils.toByteArray(httpEntity);
                    bitmap= BitmapFactory.decodeByteArray(data, 0,
                            data.length);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }
        //完成更新 UI 操作
        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            //设置 ImageView 的显示图片
            ivImage.setImageBitmap(result);
            // 销毁等待框
            dialog.dismiss();
        }
    }
}


