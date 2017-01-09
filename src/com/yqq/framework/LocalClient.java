
package com.yqq.framework;

import android.os.Bundle;

import com.limpoxe.support.servicemanager.ServiceManager;
import com.yqq.common.Constant;

//app 自行调用
public class LocalClient {
    private static LocalClient sClient = null;

    public static LocalClient get() {
        return sClient;
    }

    private LocalClient() {
        sClient = new LocalClient();
    }

    public Bundle doTransact(Bundle bundle) {
        IRpcService service = (IRpcService) ServiceManager.getService(Constant.RPC_SERVICE);
        return service.doTransact(bundle);
    }
}
