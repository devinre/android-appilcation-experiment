package com.example.dh.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    /*GridLayout gridLayout;
    //定义 16 个按钮的文本
    String[] chars = new String[]
            {
                    "+", "1", "2", "3",
                    "-", "4", "5", "6",
                    "*", "7", "8", "9",
                    "/", ".", "0", "="
            };*/
    private boolean[] checkedItems;//记录各列表项的状态
    private String[] items;//各列表项要显示的内容
    private AlertDialog.Builder setPositiveButton(AlertDialog.Builder builder)
    {
        return builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "您单击了【确定】按钮！ ",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
    private AlertDialog.Builder setNegativeButton(AlertDialog.Builder builder)
    {
        return builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "您单击了【取消】按钮！ ",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*gridLayout = (GridLayout)findViewById(R.id.activity_main);
        for(int i = 0;i < chars.length; i++)
        {
            Button bn = new Button(this);
            bn.setText(chars[i]);
//设置按钮的字号大小
            bn.setTextSize(40);
//设置按钮四周的空白区域
            bn.setPadding(5, 35, 5, 35);
//指定组件所在的行
            GridLayout.Spec rowSpec = GridLayout.spec(i/4 + 2);
//指定组件所在的列
            GridLayout.Spec columnSpec = GridLayout.spec(i%4);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, columnSpec);
            gridLayout.addView(bn, params);
        }*/
        /*ListView list1 = (ListView) findViewById(R.id.list1);
//定义一个数组
        String[] arr = {"Activity", "Service", "BroadcastReceiver", "ContentProvider"};
//将数组包装为 ArrayAdapter
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.array_item,
                arr);
//为 ListView 设置 Adapter
        list1.setAdapter(adapter1);*/
        //获取布局文件中的 Spinner 组件
        /*Spinner spinner = (Spinner) findViewById(R.id.spinner);
        String[] arr = {"红楼梦","西游记","三国演义","水浒传"};
//创建 ArrayAdapter 对象
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, arr);
//为 Spinner 设置 Adapter
        spinner.setAdapter(adapter);*/
        /*Button simple = (Button) findViewById(R.id.simple);
//为按钮的单击事件绑定事件监听器
        simple.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
//创建一个 Toast 提示信息并设置其持续时间
                Toast.makeText(MainActivity.this, "简单的提示信息",
                        Toast.LENGTH_SHORT).show();
            }
        });
        Button bn = (Button) findViewById(R.id.bn);
//为按钮的单击事件绑定事件监听器
        bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//创建一个 Toast 提示消息
                Toast toast = new Toast(MainActivity.this);
//设置 Toast 的显示位置
                toast.setGravity(Gravity.CENTER, 0, 0);
//创建一个 ImageView
                ImageView image = new ImageView(MainActivity.this);
                image.setImageResource(R.drawable.flower);
//创建一个线性布局容器
                LinearLayout ll = new LinearLayout(MainActivity.this);
//向线性布局容器里添加图片、文本
                ll.addView(image);
                TextView textView = new TextView(MainActivity.this);
                textView.setText("带图片的提示消息");
                textView.setTextSize(24);
                textView.setTextColor(Color.MAGENTA);
                ll.addView(textView);
//设置 Toast 显示自定义 View
                toast.setView(ll);
//设置 Toast 显示时间
                toast.setDuration(Toast.LENGTH_LONG);
                toast.show();
            }
        });*/
        //获取该 Activity 里面的 TabHost 组件
        /*TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
//初始化 TabHost 组件
        tabHost.setup();
//添加第一个标签页
        TabHost.TabSpec tab1 = tabHost.newTabSpec("tab1")
                .setIndicator("未接来电")//设置标题
                .setContent(R.id.tab01); //设置内容
        tabHost.addTab(tab1);
//添加第二个标签页
        TabHost.TabSpec tab2 = tabHost.newTabSpec("tab2")
                .setIndicator("已接来电")
                .setContent(R.id.tab02);
        tabHost.addTab(tab2);*/
        // 简单文本对话框
        Button button = (Button)findViewById(R.id.button);//获取“简单文本对话框”按钮
//添加单击事件监听器
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("简单文本对话框");
                builder.setIcon(R.drawable.img1);
                builder.setMessage("Hello World!\n 我爱 Android!");
                setPositiveButton(builder);//添加“确定”按钮
                setNegativeButton(builder);//添加“取消”按钮
                builder.create().show();
            }
        });
// 简单列表对话框
        Button button2 = (Button) findViewById(R.id.button2); // 获取“简单列表对话框”按钮
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] items = new String[] { "跑步", "羽毛球", "乒乓球", "网球",
                        "体操" };
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setIcon(R.drawable.img1); //设置对话框的图标
                builder.setTitle("请选择您喜欢的运动项目： "); //设置对话框的标题
//添加列表项
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this,
                                "您选择了" + items[which], Toast.LENGTH_SHORT).show();
                    }
                });
                setPositiveButton(builder);//添加“确定”按钮
                setNegativeButton(builder);//添加“取消”按钮
                builder.create().show(); // 创建对话框并显示
            }
        });
// 单选列表对话框
        Button button3 = (Button) findViewById(R.id.button3); // 获取“单选列表对话框”按钮
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] items = new String[] { "标准", "无声", "会议", "户外",
                        "离线" };
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setIcon(R.drawable.img1); //设置对话框的图标
                builder.setTitle("请选择要使用的情景模式： "); //设置对话框的标题
                builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this,
                                "您选择了" + items[which], Toast.LENGTH_SHORT).show(); //显示选择结果
                    }
                });
                setPositiveButton(builder);//添加“确定”按钮
                setNegativeButton(builder);//添加“取消”按钮
                builder.create().show(); // 创建对话框并显示
            }
        });
// 多选列表对话框
        Button button4 = (Button) findViewById(R.id.button4); // 获取“多选列表对话框”按钮
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkedItems= new boolean[] { false, true, false, true, false}; //记录各列表项的状态
                        items = new String[] { "植物大战僵尸", "愤怒的小鸟", "泡泡龙", "开心农场",
                        "超级玛丽" }; //各列表项要显示的内容
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setIcon(R.drawable.img1); //设置对话框的图标
                builder.setTitle("请选择您喜爱的游戏： "); //设置对话框标题
                builder.setMultiChoiceItems(items, checkedItems,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which, boolean isChecked) {
                                checkedItems[which]=isChecked; //改变被操作列表项的状态
                            }
                        });
                //为对话框添加“确定”按钮
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String result=""; //用于保存选择结果
                        for(int i=0;i<checkedItems.length;i++){
                            if(checkedItems[i]){ //当选项被选择时
                                result+=items[i]+"、 "; //将选项的内容添加到 result 中
                            }
                        }
//当 result 不为空时，通过消息提示框显示选择的结果
                        if(!"".equals(result)){
                            result=result.substring(0, result.length()-1); //去掉最后面添加的“、”号
                            Toast.makeText(MainActivity.this, "您选择了"+result+"!",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
                setNegativeButton(builder);//添加“取消”按钮
                builder.create().show(); // 创建对话框并显示
            }
        });
    }
}
