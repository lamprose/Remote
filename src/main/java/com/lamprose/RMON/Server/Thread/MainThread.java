package com.lamprose.RMON.Server.Thread;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MainThread extends Thread {
    //数据输出流
    InputStream inputStream=null;
    byte[] in;
    OutputStream outputStream;
    Socket socket;
    String command;
    SecondaryThread secondaryThread;

    public MainThread() {
        in=new byte[1024];
        try{
            // 1. 绑定服务端口号，暴露一个服务，该服务的地址是：本机IP：9999
            ServerSocket serverSocket = new ServerSocket(9999);
            // 2. 监听是否有客户端访问
            // 如果没有客户端访问，则会阻塞，直到有客户端进行访问，才会向下执行
            System.out.println("主:尝试与客户端建立连接");
            socket = serverSocket.accept();
            System.out.println("主:与客户端成功建立连接");
            // 3. 获取输出流，向客户端发送消息
            inputStream = socket.getInputStream();
            outputStream=socket.getOutputStream();
            secondaryThread=new SecondaryThread();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /*
    *从客户端接收消息
     */
    @Override
    public void run() {
        try{
            while (socket.isConnected()){
                inputStream.read(in);
                if(in.length!=0){
                    command=new String(in,"UTF-8");
                    System.out.println(command);
                    if(command.charAt(0)=='1'){
                        secondaryThread.download(command.substring(1));
                    }else if(command.charAt(0)=='2'){
                        secondaryThread.upload(command.substring(1));
                    }else if(command.charAt(0)=='3'){
                        secondaryThread.receiveCommand(command.substring(1));
                    }
                }
                outputStream.write("成功".getBytes("UTF-8"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
