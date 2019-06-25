package com.lamprose.RMON.Client.Thread;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Main extends Thread {
    String host,port;
    InputStream inputStream=null;
    byte[] in;
    OutputStream outputStream;
    private Socket server;
    public Main(String Host, String Port){
        host=Host;
        port=Port;
        in=new byte[1024];
        init();
    }

    private void init(){
        try{
            server=new Socket(host,Integer.parseInt(port));
            server.setKeepAlive(true);
            inputStream=server.getInputStream();
            outputStream=server.getOutputStream();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void sendMsg(String msg){
        try{
            if (server.isConnected()){
                outputStream.write(msg.getBytes("UTF-8"));
                inputStream.read(in);
                System.out.println(new String(in,"UTF-8"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
