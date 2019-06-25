package com.lamprose.RMON.Client.Thread;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class SecondaryThread {
    String host,port;
    InputStream inputStream=null;
    byte[] buffer;
    OutputStream outputStream;
    private Socket server;


    public SecondaryThread(String Host,String Port) {
        host=Host;
        port=Port;
        buffer=new byte[1024];
        try{
            server=new Socket(host,Integer.parseInt(port));
            server.setKeepAlive(true);
            inputStream=server.getInputStream();
            outputStream=server.getOutputStream();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void upload(String path){
        try{
            FileInputStream fileInputStream = new FileInputStream(path);
            int len=0;
            while ((len = fileInputStream.read(buffer)) != -1){
                outputStream.write(buffer, 0, len);
            }
            fileInputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void download(String filename){
        try{
            System.out.println(filename);
            filename=filename.trim();
            File file=new File("D://data");
            if(!file.exists())
                file.mkdirs();
            File out=new File("D://data"+File.separator+filename);
            out.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(out);
            int len=0;
            do{
                len = inputStream.read(buffer);
                fileOutputStream.write(buffer, 0, len);
            }while (len == 1024);

            fileOutputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String[] getCommandResult(){
        String result="",str="";
        try {
            int len;
            do{
                len=inputStream.read(buffer);
                str=new String(buffer);
                result+=str;
            }while (len==1024);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result.split("\\+");
    }

}
