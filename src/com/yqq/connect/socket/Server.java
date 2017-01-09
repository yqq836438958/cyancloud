
package com.yqq.connect.socket;

import android.net.Credentials;
import android.net.LocalServerSocket;
import android.net.LocalSocket;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class Server implements ISocket {
    private ServerFinally mServerRun = null;
    private String mSocketAddr = null;

    public Server(String socketaddr, Handler handler) {
        mServerRun = new ServerFinally(handler);
        mSocketAddr = socketaddr;
    }

    public void send(byte[] bs) {
        if (mServerRun != null) {
            mServerRun.send(bs);
        }
    }

    public class ServerFinally implements Runnable {
        private static final String TAG = "ServerFinally";
        LocalServerSocket server;
        LocalSocket client;
        DataInputStream inBytesReader;
        DataOutputStream outBytesWriter;

        Handler handler;

        /**
         * 此处不将连接代码写在构造方法中的原因： 我在activity的onCreate()中创建示例，如果将连接代码
         * 写在构造方法中，服务端会一直等待客户端连接，界面没有去描绘，会一直出现白屏。
         * 直到客户端连接上了，界面才会描绘出来。原因是构造方法阻塞了主线程，要另开一个线程。在这里我将它写在了run()中。
         */
        ServerFinally(Handler handler) {
            this.handler = handler;
        }

        // 发数据
        public void send(byte[] buf) {
            if (outBytesWriter != null) {
                try {
                    outBytesWriter.write(buf);
                    outBytesWriter.flush();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        // 接数据
        @Override
        public void run() {
            Log.i(TAG, "Server=======打开服务=========");
            try {
                server = new LocalServerSocket(mSocketAddr);

                client = server.accept();
                Log.i(TAG, "Server=======客户端连接成功=========");
                Credentials cre = client.getPeerCredentials();
                Log.i(TAG, "===客户端ID为:" + cre.getUid());
                outBytesWriter = new DataOutputStream(client.getOutputStream());
                inBytesReader = new DataInputStream(client.getInputStream());
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
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
                if (client != null) {
                    client.close();
                }
                if (server != null) {
                    server.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }
}
