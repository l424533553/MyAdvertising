package com.luofx.newclass.weighter;

import android.os.Handler;
import android_serialport_api.SerialPort;

import java.io.File;
import java.io.IOException;

/**
 * 作者：罗发新
 * 时间：2018/12/6 0006    17:06
 * 邮件：424533553@qq.com
 * 说明：基本称重数据
 */
public abstract class MyBaseWeighter {
    protected Handler handler;
    public static final int WEIGHT_NOTIFY_XS15 = 2115;// 香山秤15.6屏  称重数据改变 通知
    public static final int WEIGHT_NOTIFY_ST = 2013;//商通的称重数据改变  通知
    private String path;
    private int baudrate;
    protected SerialPort serialPort;

    public boolean open() {
        return false;
    }

    protected boolean open(String path, int baudrate) {
        try {
            this.path = path;
            this.baudrate = baudrate;
            serialPort = getSerialPortPrinter();
            if (serialPort.getOutputStream() == null || serialPort.getInputStream() == null) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    /**
     * 获得打印机的串口 控制
     *
     * @return 打印机串口
     */
    public SerialPort getSerialPortPrinter() throws IOException {
        if (serialPort == null) {
            serialPort = new SerialPort(new File(path), baudrate, 0);
        }
        return serialPort;
    }

    //关闭
    public void closeSerialPort() {
        if (serialPort != null) {
            try {
                serialPort.getOutputStream().close();
                serialPort.getInputStream().close();
//                serialPort.close();

                serialPort = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected void write(byte[] by) {
        try {
            getSerialPortPrinter().getOutputStream().write(by);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void flush() {
        try {
            getSerialPortPrinter().getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract void resetBalance();

}
