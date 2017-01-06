
package com.yqq;

import android.app.Application;

import com.limpoxe.support.servicemanager.ServiceManager;
import com.yqq.common.Constant;
import com.yqq.framework.RpcServiceImp;

public class RpcApp {
    private static boolean bRpcOpen = false;
    public static final int ENV_CLIENT = 1;
    public static final int ENV_CLIENT_PROXY = 2;
    public static final int ENV_REMOTE_CLIENT_CLIENT = 3;
    public static final int ENV_SRV = 4;

    public static Application getApp() {
        return null;
    }

    public static boolean isRPCEnable() {
        return bRpcOpen;
    }

    public static void config(int runEnv) {
        ServiceManager.init(RpcApp.getApp());
        switch (runEnv) {
            case ENV_CLIENT_PROXY:
                ServiceManager.publishService(Constant.RPC_SERVICE, RpcServiceImp.class.getName());
                break;

            default:
                break;
        }

    }
}
