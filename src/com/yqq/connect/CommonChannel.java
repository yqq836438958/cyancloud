
package com.yqq.connect;

import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.os.Parcel;

import com.yqq.connect.socket.Client;
import com.yqq.connect.socket.ISocket;
import com.yqq.connect.socket.Server;

import java.nio.ByteBuffer;

public class CommonChannel extends CmdChannel implements Callback {
    ISocket mISocket = null;

    private boolean mIsClient = false;
    private static Object mLockObj = new byte[0];
    private Object mConObj = new byte[1];
    private Bundle mResultBundle = null;

    public CommonChannel(boolean isClient) {
        Handler handler = new Handler(this);
        if (isClient) {
            mISocket = new Client("aaa", handler);
        } else {
            mISocket = new Server("aaa", handler);
        }
        mIsClient = isClient;
    }

    @Override
    public Bundle send(Bundle msg) {
        Parcel parcel = Parcel.obtain();
        parcel.writeBundle(msg);
        synchronized (mLockObj) {
            mISocket.send(parcel.marshall());
            try {
                mConObj.wait(1000 * 10);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return mResultBundle;
    }

    @Override
    public Bundle recv(byte[] dat) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean handleMessage(Message arg0) {
        int what = arg0.what;
        if (what == 1) {
            ByteBuffer buffer = (ByteBuffer) arg0.obj;
            synchronized (mLockObj) {
                mResultBundle = onHandleRecvData(buffer.array());
                mConObj.notify();
            }
        }
        return true;
    }

}
