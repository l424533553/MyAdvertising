package com.luofx.newclass.printer;
import com.luofx.other.tid.OrderBean;
import com.luofx.other.tid.OrderInfo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * 炜煌打印机，支持E39，E42，E47
 */
public class WHPrinter extends MyBasePrinter {
    // 15.6 香山秤黑色  称重模块 参数 /dev/ttyS4", 19200,

    public WHPrinter() {
//        this.print = print;
    }

    @Override
    public boolean open() {
        int baudrate = 115200;
        // 15.6 香山秤黑色  打印机参数
        String path = "/dev/ttyS1";
        return super.open(path, baudrate);
    }

    /**
     * 准备二维码打印前的工作准备
     *
     * @return 返回指令
     */
    @Override
    protected void readyPrinterQR() {
        write(new byte[]{27, 49, 0, 27, 51, 0});
    }

    /**
     * 打印完恢复 默认指令
     *
     * @param n 打印完 走纸几行
     * @return
     */
    @Override
    public void finishPrinterQR(byte n) {
        write(new byte[]{27, 49, 3, 27, 51, 3, 27, 100, n});
    }

    /**
     * 点阵打印
     */
    public void printMatrixQR(String contents1, int width, int height, int maxPix) {
        byte[] head = {27, 75, (byte) 128, 1};
        List<byte[]> bytes = getRQMatrixData(contents1, width, height, maxPix, head);
        if (bytes != null) {
            readyPrinterQR();
            write(bytes);
            finishPrinterQR((byte) 8);
        }
    }

    /**
     * 点行打印
     */
    public void printMatrixLineQR(String contents1, int width, int height, int maxPix) {
        byte[] head = {28, 75, 0, (byte) (maxPix / 8)};
        List<byte[]> bytes = getRQMatrixData(contents1, width, height, maxPix, head);
        if (bytes != null) {
            readyPrinterQR();
            write(bytes);
            finishPrinterQR((byte) 8);
        }
    }

    /**
     * 位圖打印
     */
    @Override
    public void printBitmapQR(String contents1, int width, int height, int maxPix) {
        byte[] head = {27, 42, 33, (byte) 128, 1};
        byte[] data = getRQBitmapData(contents1, width, height, maxPix, head);
        if (data != null) {
//            flush();
            readyPrinterQR();
            write(data);
            finishPrinterQR((byte) 4);
        }
    }

//    /**
//     * 位圖打印
//     */
//    @Override
//    public void printBitmapQR(String contents1, int width, int height, int maxPix) {
//        byte[] head = {27, 42, 33, (byte) 128, 1};
//        List<byte[]> data = getRQBitmapDataCopy(contents1, width, height, maxPix, head);
//        if (data != null) {
//            readyPrinterQR();
//            flush();
//            write(data);
//            finishPrinterQR((byte) 4);
//        }
//    }

    /**
     * 位图打印方式 8*8
     */
    public void printBitmap88QR(String contents1, int width, int height, int maxPix) {
        byte[] head = {27, 42, 1, (byte) 128, 1};
        byte[] data = getRQBitmapData(contents1, width, height, maxPix, head);
        if (data != null) {
            flush();
            readyPrinterQR();
            write(data);
            finishPrinterQR((byte) 4);
        }
    }

    /* 普通 功能方法 ****************************************************/

    /**
     * 跳行  n 空白
     *
     * @param n 跳 空白的行数
     */
    public void PrintJumpLine(byte n) throws IOException {
        byte[] by = {27, 100, n};
        write(by);
    }

    /**
     * 同一的    打印机打印以商通打印机为模板 修改而得
     */
    @Override
    public void printOrder(ExecutorService executorService, final OrderInfo orderInfo) {
        if (orderInfo == null) {
            return;
        }
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                List<OrderBean> orderBeans = orderInfo.getOrderItem();
                if (orderBeans == null) {
                    orderBeans = new ArrayList<>(orderInfo.getOrderBeans());
                }
                StringBuilder sb = new StringBuilder();
                sb.append("------------交易明细------------\n");

                sb.append("市场名称：").append(orderInfo.getMarketName()).append("\n");
                sb.append("交易日期：").append(orderInfo.getTime()).append("\n");
                sb.append("交易单号：").append(orderInfo.getBillcode()).append("\n");

                if (1 == orderInfo.getSettlemethod()) {
                    sb.append("结算方式：微信支付\n");
                }
                if (2 == orderInfo.getSettlemethod()) {
                    sb.append("结算方式：支付宝支付\n");
                }
                if (3 == orderInfo.getSettlemethod()) {
                    sb.append("结算方式：现金支付\n");
                }

                sb.append("卖方名称：").append(orderInfo.getSeller()).append("\n");
                sb.append("摊位号：").append(orderInfo.getStallNo()).append("\n");
                sb.append("商品名\b" + "单价/元\b" + "重量/kg\b" + "金额/元" + "\n");

                for (int i = 0; i < orderBeans.size(); i++) {
                    OrderBean goods = orderBeans.get(i);
                    goods.setOrderInfo(orderInfo);
                    sb.append(goods.getName()).append("\t").append(goods.getPrice()).append("\t").append(goods.getWeight()).append("\t").append(goods.getMoney()).append("\n");
                }

                sb.append("--------------------------------\n");
                sb.append("合计(元)：").append(orderInfo.getTotalamount()).append("\n");
                sb.append("司磅员：").append(orderInfo.getSeller()).append("\t").append("秤号：").append(orderInfo.getTerid() + "\n");
                sb.append(BASE_COMPANY_NAME);
                sb.append("\n\n");

                setLineSpacing((byte) 12);
                printString(sb.toString());

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String qrString = "http://data.axebao.com/smartsz/trace/?no=" + orderInfo.getBillcode();
                printltString("扫一扫获取追溯信息：");
                printBitmapQR(qrString, 48, 48, 384);
            }
        });
    }

}
