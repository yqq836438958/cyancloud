
package com.yqq.connect.socket;

import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class Client implements ISocket {
    private ClientRunnable mClientRunnable = null;
    private String mSocketAddr = null;

    public Client(String socketAddr, Handler handler) {
        mSocketAddr = socketAddr;
        mClientRunnable = new ClientRunnable(handler);
    }

    @Override
    public void send(byte[] data) {
        mClientRunnable.send(data);
    }

    public class ClientRunnable implements Runnable {
        private static final String TAG = "ClientFinally";
        private int timeout = 30000;
        LocalSocket clientSocket;

        DataOutputStream outBytesWriter;
        DataInputStream inBytesReader;

        Handler handler;

        ClientRunnable(Handler handler) {
            this.handler = handler;
        }

        // 发数据
        public void send(byte[] data) {
            if (outBytesWriter != null) {
                try {
                    outBytesWriter.write(data);
                    outBytesWriter.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void run() {
            clientSocket = new LocalSocket();
            try {
                clientSocket.connect(new LocalSocketAddress(mSocketAddr));// 连接服务器
                Log.i(TAG, "Client=======连接服务器成功=========");
                clientSocket.setSoTimeout(timeout);
                outBytesWriter = new DataOutputStream(clientSocket.getOutputStream());
                inBytesReader = new DataInputStream(clientSocket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            int read = 0;
            byte[] buff = new byte[4096];
            byte[] realData = null;
            try {
                while ((read = inBytesReader.read(buff, 0, 4096)) > 0) {
                    // swapStream.write(buff, 0, read);
                    realData = new byte[read];
                    System.arraycopy(buff, 0, realData, 0, read);
                    Message msg = handler.obtainMessage();
                    msg.what = 1;
                    msg.obj = ByteBuffer.wrap(realData);
                    handler.sendMessage(msg);
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        public void close() {
            try {
                if (outBytesWriter != null) {
                    outBytesWriter.close();
                }
                if (inBytesReader != null) {
                    inBytesReader.close();
                }
                if (clientSocket != null) {
                    clientSocket.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }
}
