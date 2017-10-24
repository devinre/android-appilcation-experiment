package com.example.dh.myapplication;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class SQLiteActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "openimage";
    Context mContext;
    private EditText nameEditView;
    private EditText scoreEditView;
    private ImageView imgView;
    private TextView txtView;
    private SQLiteDatabase db;
    private static final int READ_REQUEST_CODE=0;
    MyDBOpenHelper mydbopenhelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);
        mContext=this;
        Button btninsert=(Button)findViewById(R.id.btn_insert);
        Button btnquery=(Button)findViewById(R.id.btn_query);
        Button btnupdate=(Button)findViewById(R.id.btn_update);
        Button btnopenImg=(Button)findViewById(R.id.btn_openImg);
        txtView=(TextView)findViewById(R.id.txtView);
        imgView=(ImageView)findViewById(R.id.imgView);
        nameEditView=(EditText)findViewById(R.id.nameEditView) ;
        scoreEditView=(EditText)findViewById(R.id.scoreEditView);
        mydbopenhelper=new MyDBOpenHelper(mContext,"test",null,1);
        db=mydbopenhelper.getWritableDatabase();
        btninsert.setOnClickListener(this);
        btnquery.setOnClickListener(this);
        btnupdate.setOnClickListener(this);
        btnopenImg.setOnClickListener(this);
        imgView.setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_insert:
                ContentValues values1=new ContentValues();
                String name1=nameEditView.getText().toString();
                int score1=Integer.parseInt(scoreEditView.getText().toString());
                values1.put("name",name1);
                values1.put("score",score1);
                db.insert("student",null,values1);
                Toast.makeText(mContext,"插入数据完毕",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_query:
                StringBuilder sb=new StringBuilder();
                Cursor cursor=db.query("student",null,null,null,null,null,null);
                if(cursor.moveToFirst()){
                    do{
                        int stuId=cursor.getInt(cursor.getColumnIndex("stuId"));
                        String name=cursor.getString(cursor.getColumnIndex("name"));
                        int score=cursor.getInt(cursor.getColumnIndex("score"));
                        sb.append("stuId: "+stuId+": "+name+": "+score+"\n");
                    }while(cursor.moveToNext());
                }
                cursor.close();
                Toast.makeText(mContext,sb.toString(),Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_update:
                ContentValues values2=new ContentValues();
                values2.put("name",nameEditView.getText().toString());
                values2.put("score",Integer.parseInt(scoreEditView.getText().toString()));
                db.update("student",values2,"name = ?",new String[]{nameEditView.getText().toString()});
                break;
            case R.id.btn_openImg:
                Intent intent=new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent,READ_REQUEST_CODE);
                break;
            case R.id.imgView:
                Toast.makeText(mContext,"插入数据库完毕",Toast.LENGTH_SHORT).show();
                try{
                    ByteArrayOutputStream outs=new ByteArrayOutputStream();
                    ((BitmapDrawable)(imgView.getDrawable())).getBitmap().compress(Bitmap.CompressFormat.PNG,100,outs);
                    Object[] args=new Object[]{
                            nameEditView.getText().toString(),
                            Integer.parseInt(scoreEditView.getText().toString()),outs.toByteArray()
                    };
                    db.execSQL("INSERT INTO student(name,score,img) values(?,?,?)",args);
                    outs.close();
                }catch (Exception e){
                    e.printStackTrace();
                    Log.e(TAG,e.toString());
                }
                break;
        }
    }
    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent resultData){
        if(requestCode==READ_REQUEST_CODE&&resultCode== Activity.RESULT_OK){
            if(resultData!=null){
                Uri uri=resultData.getData();
                Log.i(TAG,uri.toString());
                AsyncTask<Uri,Void,Bitmap> imageLoadAsyncTask=new AsyncTask<Uri, Void, Bitmap>() {
                    @Override
                    protected Bitmap doInBackground(Uri... uris) {
                        dumpImageMetaData(uris[0]);
                        return getBitmapFromUri(uris[0]);
                    }
                    @Override
                    protected void onPostExecute(Bitmap bitmap){
                        txtView.setText("点击图片插入数据库");
                        imgView.setImageBitmap(bitmap);
                    }
                };
                try {
                    imageLoadAsyncTask.execute(uri);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    public Bitmap getBitmapFromUri(Uri uri) {
        try{
            Bitmap bitmap= MediaStore.Images.Media.getBitmap(mContext.getContentResolver(),uri);
            return bitmap;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public void dumpImageMetaData(Uri uri) {
        Cursor cursor=this.getContentResolver().query(uri,null,null,null,null,null);
        try{
            if(cursor!=null&&cursor.moveToFirst()){
                String displayName=cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                Log.i("[important]","Display Name: "+displayName);
                int sizeIndex=cursor.getColumnIndex(OpenableColumns.SIZE);
                String size=null;
                if(!cursor.isNull(sizeIndex)){
                    size=cursor.getString(sizeIndex);
                }else{
                    size=String.valueOf(R.string.unknown);
                }
                Log.i("[important]","Size: "+size);
                //Toast.makeText(mContext,"Display Name : "+displayName+"\n"+"Size : "+size+"\n"+"Uri : "+uri.toString(),Toast.LENGTH_SHORT).show();
            }
        }finally {
            cursor.close();
        }
    }
}
