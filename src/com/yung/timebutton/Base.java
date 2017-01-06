package com.yung.timebutton;

import android.util.Log;

public class Base {
    {
        Log.e("yqq", "Base block1");
    }
    public Base(){
        Log.e("yqq", "Base construct"); 
        onCall();
    }
    public Base(int i){
        Log.e("yqq", "Base construct with param"); 
        onCall();
    }
    protected void onCall(){
        Log.e("yqq", "base send call");
    }
}
