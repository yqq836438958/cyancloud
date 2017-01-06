
package com.yqq.connect;

import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;

import com.tencent.tws.framework.common.DevMgr;
import com.tencent.tws.framework.common.Device;
import com.tencent.tws.framework.common.ICommandHandler;
import com.tencent.tws.framework.common.MsgSender;
import com.tencent.tws.framework.common.MsgSender.MsgSendCallBack;

import qrom.component.log.QRomLog;

import com.tencent.tws.framework.common.TwsMsg;

public class DmaBtChannel extends CmdChannel implements ICommandHandler {
    private final int TRANSACT_CMD = 9383;
    private static final String TAG = "DmaBtChannel";

    @Override
    public Bundle send(Bundle msg) {
        Device device = DevMgr.getInstance().connectedDev();
        Parcel parcel = Parcel.obtain();
        parcel.writeBundle(msg);
        MsgSender.getInstance().sendCmd(device, TRANSACT_CMD, parcel.marshall(),
                new MsgSendCallBack() {

                    @Override
                    public void onLost(int arg0, long arg1) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onSendResult(boolean arg0, long arg1) {
                        // TODO Auto-generated method stub

                    }
                });
        return null;
    }

    @Override
    public Bundle recv(byte[] dat) {
        return null;
    }

    @Override
    public boolean doCommand(TwsMsg oMsg, Device arg1) {
        boolean handle = false;
        try {
            if (oMsg == null) {
                return handle;
            }

            QRomLog.d(TAG, "doCommand|cmd=" + oMsg.cmd());

            switch (oMsg.cmd()) {
                case TRANSACT_CMD:
                    onHandleRecvData(oMsg.msgByte());
                    handle = true;
                    break;
                default:
                    break;
            }

        } catch (Exception e) {
            Log.e(TAG, "doCommand|exp:" + e.getMessage());
        }
        return handle;
    }

}
