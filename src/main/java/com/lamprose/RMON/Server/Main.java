package com.lamprose.RMON.Server;

import com.lamprose.RMON.Server.Thread.ScreenThread;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Hello world!
 *
 */
public class Main {
    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(16000);
            System.out.println("正在连接服务器>>>>>>");
            //等待连接
            Socket client = ss.accept();
            //服务器连接成功
            System.out.println("服务器连接成功>>>>>>");
            //获取输出流
            OutputStream os = client.getOutputStream();
            //二进制文件
            DataOutputStream dos = new DataOutputStream(os);
            //使用多线程传输
            ScreenThread st = new ScreenThread(dos);
            st.start();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}