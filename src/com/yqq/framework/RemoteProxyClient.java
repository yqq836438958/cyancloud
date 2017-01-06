
package com.yqq.framework;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;

import com.limpoxe.support.servicemanager.ProcessBinder;
import com.limpoxe.support.servicemanager.ServiceProvider;
import com.limpoxe.support.servicemanager.compat.BundleCompat;
import com.limpoxe.support.servicemanager.compat.ContentProviderCompat;
import com.yqq.common.Constant;

//真正执行与Server的通信逻辑
public class RemoteProxyClient {

    private IBinder mBinder = null;

    private String mDescriptor = null;

    private int getRemoteBinder(Bundle argsBundle) {
        IBinder binder = null;
        Bundle queryResult = ContentProviderCompat.call(ServiceProvider.buildUri(),
                ServiceProvider.QUERY_SERVICE, Constant.BUNDLE_KEY_SRV_NAME, argsBundle);
        if (queryResult != null) {
            mBinder = BundleCompat.getBinder(queryResult,
                    ServiceProvider.QUERY_SERVICE_RESULT_BINDER);
            mDescriptor = queryResult.getString(ServiceProvider.QUERY_SERVICE_RESULT_DESCRIPTOR);
            return 0;
        }
        return -1;
    }

    public Bundle transact(Bundle argsBundle)
            throws RemoteException {
        if (getRemoteBinder(argsBundle) < 0) {
            return null;
        }
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        Bundle _result;
        try {
            _data.writeInterfaceToken(mDescriptor);
            if ((argsBundle != null)) {
                _data.writeInt(1);
                argsBundle.writeToParcel(_data, 0);
            } else {
                _data.writeInt(0);
            }
            mBinder.transact(ProcessBinder.FIRST_CODE, _data, _reply, 0);
            _reply.readException();
            if ((0 != _reply.readInt())) {
                _result = Bundle.CREATOR.createFromParcel(_reply);
            } else {
                _result = null;
            }
        } finally {
            _reply.recycle();
            _data.recycle();
        }
        return _result;
    }
}
