package com.lamprose.RMON.Client.Frame;

import com.lamprose.RMON.Client.Dialog.ConnectDialog;
import com.lamprose.RMON.Client.Dialog.InputDialog;
import com.lamprose.RMON.Client.Dialog.WaringDialog;
import com.lamprose.RMON.Client.Thread.Main;
import com.lamprose.RMON.Client.Thread.SecondaryThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Client extends JFrame {
    private JMenuBar menubar;
    private JMenuItem connectMenu,commandMenu,screenMenu,aboutMenu,upload,download;
    private Screen screen;
    public String IP="";
    Main m;
    SecondaryThread secondaryThread;
    //初始化窗口配置
    public Client() {
        setLayout(new CardLayout());
        initFrame();
        initEvent();
        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(400, 80);
        //setResizable(false);
        setVisible(true);
    }
    //初始化控件
    private void initFrame() {
        menubar = new JMenuBar();
        JMenu control=new JMenu("控制");
        JMenu function=new JMenu("功能");
        connectMenu = new JMenuItem("连接"); // JMnud的实例就是一个菜单
        commandMenu=new JMenuItem("命令行");
        JMenu file=new JMenu("文件");
        upload=new JMenuItem("上传");
        download=new JMenuItem("下载");
        screenMenu=new JMenuItem("屏幕");
        aboutMenu = new JMenuItem("关于");
        control.add(connectMenu);
        function.add(screenMenu);
        function.add(commandMenu);
        file.add(upload);
        file.add(download);
        function.add(file);
        menubar.add(control); // 菜单条中加入菜单
        menubar.add(function);
        menubar.add(aboutMenu);
        this.setJMenuBar(menubar);
    }
    //添加控件事件
    private void initEvent(){

        connectMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ConnectDialog(Client.this,true);
                m=new Main(IP,"9999");
                secondaryThread=new SecondaryThread(IP,"9998");
                System.out.println(IP);
            }
        });

        screenMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(IP==""){
                    new WaringDialog(Client.this,true,"远端服务器未连接,请连接后重试");
                    return;
                }
                screen=new Screen(IP,"16000");
                add(screen);
                setSize(600,600*screen.getSize().height/screen.getSize().width);
                screen.startThread();
            }
        });

        upload.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser=new JFileChooser();
                fileChooser.showOpenDialog(Client.this);
                System.out.println(fileChooser.getSelectedFile().getPath());
                secondaryThread.upload(fileChooser.getSelectedFile().getPath());
                m.sendMsg("1"+fileChooser.getSelectedFile().getName());
            }
        });

        download.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String path=new InputDialog(Client.this,true,"请输入路径").returnInput();
                m.sendMsg("2"+path);
                int lastIndex=path.lastIndexOf('\\');
                secondaryThread.download(path.substring(lastIndex));
            }
        });

        commandMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String command=new InputDialog(Client.this,true,"请输入命令").returnInput();
                System.out.println(command);
                m.sendMsg("3"+command);
                for (String kf:secondaryThread.getCommandResult()){
                    System.out.println(kf);
                }
            }
        });

    }
}