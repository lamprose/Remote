package com.lamprose.RMON.Client.Frame;

import sun.awt.windows.ThemeReader;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class Screen extends JPanel {
    String host,post;
    Dimension ds;
    boolean isRun;
    DataInputStream dis;
    JLabel backImage;
    private Socket server;
    public Screen(String Host,String Post){
        host=Host;
        post=Post;
        isRun=false;
        setLayout(new BorderLayout());
        init();
        setSize(ds);
        setVisible(true);
    }

    public void init(){
        try{
            server = new Socket(host, Integer.parseInt(post));
            server.setKeepAlive(true);
            dis = new DataInputStream(server.getInputStream());

            //读取分辨率
            double height = dis.readDouble();
            double width = dis.readDouble();
            ds = new Dimension((int)width, (int)height);

            backImage = new JLabel();
            //滚动条
            add(backImage);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void startThread(){
        isRun=true;
        new Thread(new Runnable() {
            public void run() {
                while (isRun){
                    try {
                        int len = dis.readInt();
                        byte[] data = new byte[len];
                        dis.readFully(data);
                        ImageIcon imageIcon = new ImageIcon(compress(data,0.5));
                        backImage.setIcon(imageIcon);
                        //重新绘制面板。
                        //revalidate();
                        Thread.sleep(0);
                    }catch (Exception e) {
                        e.printStackTrace();
                        isRun = false;
                    }
                }
            }
        }).start();
    }
    public void stopThread(){
        isRun=false;
    }

    public Dimension getSize(){
        return ds;
    }

    /**
     * 按照 宽高 比例压缩
     *
     * @param srcImgData 待压缩图片输入流
     * @param scale 压缩刻度
     * @return 压缩后图片数据
     * @throws IOException 压缩图片过程中出错
     */
    public static Image compress(byte[] srcImgData, double scale) throws IOException {
        BufferedImage bi = ImageIO.read(new ByteArrayInputStream(srcImgData));
        int width = (int) (bi.getWidth() * scale); // 源图宽度
        int height = (int) (bi.getHeight() * scale); // 源图高度

        Image image = bi.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics g = tag.getGraphics();
        g.setColor(Color.RED);
        g.drawImage(image, 0, 0, null); // 绘制处理后的图
        g.dispose();
        return tag;
    }
}
