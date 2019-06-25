package com.lamprose.RMON.Server.Thread;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SecondaryThread {
    //数据输出流
    InputStream inputStream=null;
    byte[] buffer;
    OutputStream outputStream;
    Socket socket;

    public SecondaryThread() {
        buffer=new byte[1024];
        try{
            // 1. 绑定服务端口号，暴露一个服务，该服务的地址是：本机IP：9999
            ServerSocket serverSocket = new ServerSocket(9998);
            // 2. 监听是否有客户端访问
            // 如果没有客户端访问，则会阻塞，直到有客户端进行访问，才会向下执行
            System.out.println("次要:尝试与客户端建立连接");
            socket = serverSocket.accept();
            System.out.println("次要:与客户端成功建立连接");
            // 3. 获取输出流，向客户端发送消息
            inputStream = socket.getInputStream();
            outputStream=socket.getOutputStream();
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

    public void upload(String path){
        try{
            path=path.trim();
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

    public void receiveCommand(String command){
        try {
            Runtime r=Runtime.getRuntime();
            command=command.trim();
            System.out.println(command);
            Process p=r.exec(command);
            BufferedReader in  = new BufferedReader(new InputStreamReader(p.getInputStream(),"GBK"));
            String line=null;
            while ((line = in.readLine()) != null) {
                outputStream.write((line+"+").getBytes("UTF-8"));
                outputStream.flush();
            }
            in.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
