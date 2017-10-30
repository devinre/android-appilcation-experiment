package com.example.dh.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private static final String PATH_URL =
            "http://images.cnblogs.com/cnblogs_com/plokmju/550907/o_hand.jpg";
    private static final String PATH_URL2 =
            "http://ww1.sinaimg.cn/bmiddle/88ff29e8jw1e7pjnpfxbrj20dp0a90tb.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        Button btnRunOnUiThread = findViewById(R.id.btnRunOnUiThread);
        Button btnError = findViewById(R.id.btnError);
        Button btnPost = findViewById(R.id.btnPost);

        btnError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 增加一个线程访问网络
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // 获取网络路径下的图片
                        Bitmap btm = loadImageFromNetwork(PATH_URL);
                        imageView.setImageBitmap(btm);
                    }
                }).start();
            }
        });

        btnRunOnUiThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // 获取网络路径下的图片
                        final Bitmap btm = loadImageFromNetwork(PATH_URL);
                        // 在 UI 线程上操作 Bitmap 组件
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                imageView.setImageBitmap(btm);
                                Toast.makeText(getApplicationContext(),
                                        "RunOnUiThread", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).start();
            }
        });

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final Bitmap btm =loadImageFromNetwork(PATH_URL2);
                        // 将操作 Bitmap 的方式发布到 UI 线程上
                        imageView.post(new Runnable() {
                            @Override
                            public void run() {
                                imageView.setImageBitmap(btm);
                                Toast.makeText(getApplicationContext(),
                                        "Post", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).start();
            }
        });
    }

    private Bitmap loadImageFromNetwork(String uri) {
        // 根据 URI 地址，获取器地址的图片资源
        Bitmap bitmap = null;
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(uri);
        HttpResponse httpResponse;
        try {
            httpResponse = httpClient.execute(httpGet);
            if (200 == httpResponse.getStatusLine().getStatusCode()) {
                byte[] data =
                        EntityUtils.toByteArray(httpResponse.getEntity());
                bitmap = BitmapFactory.decodeByteArray(data, 0,
                        data.length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
