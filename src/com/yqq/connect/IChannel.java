
package com.yqq.connect;

import android.os.Bundle;

public interface IChannel {
    public Bundle send(Bundle msg);

    public Bundle recv(byte[] dat);
}
