
package com.yqq.framework;

import android.os.Bundle;

import com.yqq.connect.CmdChannel;
import com.yqq.connect.IChannel;

public class RpcServiceImp implements IRpcService {

    private IChannel mChannel = null;

    public RpcServiceImp() {
        mChannel = CmdChannel.get();
    }

    @Override
    public Bundle doTransact(Bundle data) {
        return mChannel.send(data);
    }

}
