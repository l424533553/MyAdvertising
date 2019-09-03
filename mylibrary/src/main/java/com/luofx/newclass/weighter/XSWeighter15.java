package com.luofx.newclass.weighter;


import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
/**
 * 香山秤15.6 黑色屏
 * 使用步骤：1.初始化， 然后 open()
 */
public class XSWeighter15 extends MyBaseWeighter {

    public XSWeighter15(Handler handler) {
        this.handler = handler;
    }

    public static String TAG = "XSWeighter15";
    private ReadThread readThread;

    /**
     * 打开串口
     */
    public boolean open() {
        int baudrate = 19200;
        String path = "/dev/ttyS4";
        if (!super.open(path, baudrate)) {
            return false;
        }
        readThread = new ReadThread();
        readThread.start();//开始线程监控是否有数据要接收

        //new SendThread().start(); //开始线程发送指令
        return true;
    }

    /**
     * 关闭串口
     */
    public void closeSerialPort() {
        if (readThread != null) {
            readThread.stopRun();
        }
        super.closeSerialPort();
    }

    /**
     * 设置重置按钮
     */
    public void resetBalance() {
        String zer = "<ZER >";
        sendString(zer);
    }

    /**
     * private String wei = "<WEI >";  称重指令
     * private String zer = "<ZER >";  置零指令
     * private String tar = "<TAR >";  去皮指令
     * 发送串口指令
     */
    private void sendString(String data) {
        byte[] sendData = data.getBytes(); //string转byte[]
        if (sendData.length > 0) {
            write(sendData);
            //outputStream.write('\n');
            //outputStream.write('\r'+'\n');
            flush();
        }
    }

    /**
     * private String wei = "<WEI >";  称重指令
     * private String zer = "<ZER >";  置零指令
     * private String tar = "<TAR >";  去皮指令
     * 发送串口指令
     */
    public void sendZer() {
        String zer = "<ZER >";
        byte[] sendData = zer.getBytes(); //string转byte[]
        write(sendData);
        flush();
    }

    /**
     * 发送数据
     */
    public void sendByteData() {
        byte[] sendData = {0x3C, 0x43, 0x41, 0x4C, 0x30, 0x30, 0x32, 0x30, (byte) 0x92, 0x3E};
        write(sendData);
        flush();
    }

    /**
     * 单开一线程，来读数据
     */
    //写一个自定义线程来继承自Thread类
    private class ReadThread extends Thread {
        private boolean isRun; //是否一直循环

        @Override
        public synchronized void start() {
            isRun = true;
            super.start();
        }

        private void stopRun() {
            isRun = false;
            interrupt();
        }

        @Override
        public void run() {
            super.run();
            //判断进程是否在运行，更安全的结束进程
            try {
                while (isRun) {
                    byte[] buffer = new byte[100];
                    int size; //读取数据的大小
                    size = getSerialPortPrinter().getInputStream().read(buffer);
                    if (size > 32) {
                        String str = new String(buffer, 0, size).trim();
                        //传输的数据是数据加 空数据间隔 传送
                        if (!TextUtils.isEmpty(str)) {
//                          MyLog.myInfo("收到的数据" + str);
                            if (handler != null) {
                                Message msg = handler.obtainMessage(WEIGHT_NOTIFY_XS15, str);
                                handler.sendMessage(msg);
                            }
                        }
                    }
                    Thread.sleep(150);
                }
            } catch (IOException e) {
                Log.e(TAG, "run: 数据读取异常：" + e.toString());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 单开一线程，来发数据
     */
    //写一个自定义线程来继承自Thread类
    private class SendThread extends Thread {
        private boolean isRun; //是否一直循环

        @Override
        public synchronized void start() {
            isRun = true;
            super.start();
        }

        private void stopRun() {
            isRun = false;
            interrupt();
        }

        @Override
        public void run() {
            super.run();
            //判断进程是否在运行，更安全的结束进程
            while (!isRun) {
                Log.d(TAG, "进入线程run");
                try {
                    sendString("<WEI >");
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


