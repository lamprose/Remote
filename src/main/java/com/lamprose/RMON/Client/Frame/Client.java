package com.lamprose.RMON.Client.Frame;

import com.lamprose.RMON.Client.Frame.Dialog.ConnectDialog;
import com.lamprose.RMON.Client.Frame.Dialog.WaringDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Client extends JFrame {
    private JMenuBar menubar;
    private JMenuItem connectMenu,screenMenu,aboutMenu;
    private Screen screen;
    public String IP="";
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
        screenMenu=new JMenuItem("屏幕");
        aboutMenu = new JMenuItem("关于");
        control.add(connectMenu);
        function.add(screenMenu);
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
                System.out.println(screen.getSize().height);
                screen.startThread();
            }
        });
    }
}