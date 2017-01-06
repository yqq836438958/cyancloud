
package com.yqq.connect;

import android.os.Bundle;
import android.os.Parcel;
import android.os.RemoteException;

import com.yqq.framework.RemoteProxyClient;

public abstract class CmdChannel implements IChannel {
    private RemoteProxyClient mRemoteProxyClient = null;
    private static CmdChannel sInstance = null;

    public CmdChannel() {
        mRemoteProxyClient = new RemoteProxyClient();
    }

    public static IChannel get() {
        if (sInstance == null) {
            synchronized (CmdChannel.class) {
                sInstance = new DmaBtChannel();
            }
        }
        return sInstance;
    }

    public Bundle onHandleRecvData(byte[] byteRecvDat) {
        if (byteRecvDat == null) {
            return null;
        }
        Parcel parcel = Parcel.obtain();
        parcel.unmarshall(byteRecvDat, 0, byteRecvDat.length);
        Bundle argsBundle = parcel.readBundle();
        try {
            return mRemoteProxyClient.transact(argsBundle);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }
}
