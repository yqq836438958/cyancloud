
package com.yung.timebutton;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;

/**
 * ����������
 * 
 * @author yung
 *         <P>
 *         2015��1��14�� 13:00:26
 */
public class MainActivity extends Activity implements OnClickListener {

    private TimeButton v;
    private Button mStartBtn = null;
    private Button mStopBtn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        v = (TimeButton) findViewById(R.id.button1);
        v.onCreate(savedInstanceState);
        v.setTextAfter("s后重试").setTextBefore("获取验证码").setLenght(10 * 1000);
        v.setOnClickListener(this);
        mStartBtn = (Button) findViewById(R.id.start);
        mStopBtn = (Button) findViewById(R.id.stop);
        mStartBtn.setOnClickListener(this);
        mStopBtn.setOnClickListener(this);
        int i = 120;
        Log.d("yqq", Integer.toHexString(120));
        setText1();
    }

    private void setText1() {
        String sErrDesc = String.format(getString(R.string.wallet_errcode_desc), "110");
        TextView tView = (TextView) findViewById(R.id.tvtv);
        tView.setText(Html.fromHtml(sErrDesc));
         tView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.start:
                startServ();
                break;
            case R.id.stop:
                stopServ();
                break;
            case R.id.button1:
                Toast.makeText(MainActivity.this, "set onclicklistnenr",
                        Toast.LENGTH_SHORT).show();
                // SubClass subClass = new SubClass();
                 gotoDm();
                // isBeijingTongExist(this);
//                setCachedCardList("aaaaaaaaaaaaaaaaaaaaaddd");
//                isBleServerOn(this);
                break;
            default:
                break;
        }

    }

    private void startServ() {
        Intent intent = new Intent();
        intent.setClassName("com.pacewear.walletble", "com.ble.service.BootupService");
        intent.putExtra("bleserver_enable", 1);
        startService(intent);
    }

    private void stopServ() {
        Intent intent = new Intent();
        intent.setClassName("com.pacewear.walletble", "com.ble.service.BootupService");
        intent.putExtra("bleserver_enable", 0);
        stopService(intent);
    }

    private void gotoDm() {
        Intent intent = new Intent();
        String format = "tsmclient://card?type=%s&action=%s";
        String url = String.format(format, "BMAC", "issue");
        intent.setData(Uri.parse(url));
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        v.onDestroy();
        super.onDestroy();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            // Success
            Log.d("aa", "bb");
        } else {
            // Failed
            Log.d("aa", "cc");
        }
    }

    public static boolean isBeijingTongExist(Context context) {
        try {
            Context otherCtx = context.createPackageContext("com.tencent.tws.walletserviceproxy",
                    Context.CONTEXT_IGNORE_SECURITY);// com.tencent.tws.walletserviceproxy
            SharedPreferences sharf = otherCtx.getSharedPreferences("cache_card_list",
                    Context.MODE_WORLD_READABLE);
            String carList = sharf.getString("card_list", "");
            Log.d("yqq", "getsharf:" + carList);
            if (TextUtils.isEmpty(carList)) {
                return false;
            }
            return carList.contains("9156000014010001");
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    public void setCachedCardList(String list) {
        SharedPreferences preferences = this.getSharedPreferences("cache_card_list",
                Context.MODE_WORLD_READABLE);
        Editor editor = preferences.edit();
        if (list == null) {
            editor.remove("card_list");
        } else {
            editor.putString("card_list", list);
        }
        editor.commit();
    }

    public static boolean isBleServerOn(Context _context) {
        try {
            Context context = _context.getApplicationContext().createPackageContext(
                    "com.ble.gattserver",
                    Context.CONTEXT_IGNORE_SECURITY | Context.CONTEXT_INCLUDE_CODE);
            SharedPreferences sharedPreferences = context.getSharedPreferences("ble_server",
                    Context.MODE_WORLD_READABLE);
            Log.d("yqq", "from service:" + sharedPreferences.getInt("bleserver_enable", 0));
            Log.d("yqq", "again2 :" + sharedPreferences.getString("test", ""));
            // return sharedPreferences.getInt("bleserver_enable", 0) == 1;
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // String path = "/data/data/com.ble.gattserver/shared_prefs/ble_server.xml";
        // File file = new File(path);
        // FileInputStream inputStream = new FileInputStream(file);
        // // 获取的是一个xml字符串
        // String data = new FileService().read(inputStream);
        // Log.i(TAG, data);
        try {
            Context context = _context.getApplicationContext().createPackageContext(
                    "com.ble.gattserver",
                    Context.CONTEXT_IGNORE_SECURITY | Context.CONTEXT_INCLUDE_CODE);
            SharedPreferences sharedPreferences = context.getSharedPreferences("haha",
                    Context.MODE_WORLD_READABLE);
            Log.d("yqq", "again :" + sharedPreferences.getString("why", ""));
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        Log.d("yqq", "on backpressed!!");
        super.onBackPressed();
    }
}
