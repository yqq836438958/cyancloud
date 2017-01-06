package com.yung.timebutton;

import android.util.Log;

public class SubClass extends Base {
    private int a = 0;
    {
        Log.e("yqq", "SubClass block1"); 
    }
    public SubClass(){
        super();
        Log.e("yqq", "SubClass construct"); 
        Log.e("yqq", "SubClass constrcut ,a = " +a);
    }
    @Override
    public void onCall(){
        
        a = 10;
        Log.e("yqq", "SubClass onCall,a = " +a);
    }
}
