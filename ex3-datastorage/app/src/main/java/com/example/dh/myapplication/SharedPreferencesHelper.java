package com.example.dh.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dh on 2017/10/16.
 */

public class SharedPreferencesHelper {
    private Context context;
    private SharedPreferences sharedPreferences=null;
    private SharedPreferences.Editor editor=null;
    private Map<String,String> map= new HashMap<>();
    public SharedPreferencesHelper(Context context,String name){
        init(context,Context.MODE_PRIVATE,name);
    }
    public void init(Context context,int mode,String name){
        this.context=context;
        sharedPreferences=context.getSharedPreferences(name,mode);
        editor=sharedPreferences.edit();
    }
    public void addKeyValuePair(String key,String value){
        map.put(key,value);
    }
    public void save(){
        for(Map.Entry<String,String> entry:map.entrySet()){
            editor.putString(entry.getKey(),entry.getValue());
        }
        editor.commit();
    }
    public Map<String,?> read(){
        return sharedPreferences.getAll();
    }
    public String getSPValueByKey(String key){
        return sharedPreferences.getString(key,null);
    }

}
