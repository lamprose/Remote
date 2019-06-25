package com.lamprose.RMON.Server.Thread;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ScreenThread extends Thread {
    //数据输出流
    DataOutputStream dataOut;

    public ScreenThread(DataOutputStream dataOut) {
        this.dataOut = dataOut;
    }

    public void run() {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension dm =tk.getScreenSize();

        //获取屏幕分辨率，发送
        try {
            dataOut.writeDouble(dm.getHeight());
            dataOut.writeDouble(dm.getWidth());
            //刷新内存
            dataOut.flush();

            //定义一个矩形
            Rectangle rc = new Rectangle(dm);

            //定义一个机器人
            Robot robot = new Robot();

            while(true) {
                BufferedImage bufferedImage = robot.createScreenCapture(rc);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                //图像压缩处理
                JPEGImageEncoder jEncoder = JPEGCodec.createJPEGEncoder(baos);
                jEncoder.encode(bufferedImage);

                byte[] data = baos.toByteArray();
                //不停输出
                dataOut.writeInt(data.length);
                dataOut.write(data);
                dataOut.flush();
                Thread.sleep(0);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
