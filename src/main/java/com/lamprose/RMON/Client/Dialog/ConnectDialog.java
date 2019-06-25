package com.lamprose.RMON.Client.Dialog;

import com.lamprose.RMON.Client.Frame.Client;
import com.lamprose.RMON.Client.Tool.ScanDeviceTool;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ConnectDialog extends JDialog {
    private Client main;
    private JTable table;
    private  JButton OK,add;
    private DefaultTableModel tableModel;   //表格模型对象
    private ScanDeviceTool scanTool;
    private List<String> ipList;

    public ConnectDialog(Client owner, boolean modal){
        super(owner,"在线设备",modal);
        setLayout(new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS));
        main=owner;
        initFrame();
        initEvent();
        setSize(550,300);
        setLocation(owner.getX()+100,owner.getY()-35);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }
    private void initFrame(){
        Object[][] data={
                {"a","10.199.224.152","2019-06-24 11:35"},
                {"b","192.168.3.7","2019-06-23 11:35"},
                {"c","192.168.3.8","2019-06-23 11:35"},
        };
        String[] colName={"主机名称","主机地址","在线时间"};
        // 以Names和playerInfo为参数，创建一个表格
        tableModel = new DefaultTableModel(data,colName);
        table=new JTable(tableModel);
        // 设置此表视图的首选大小
        table.setPreferredScrollableViewportSize(new Dimension(550, 100));
        // 将表格加入到滚动条组件中
        final JScrollPane scrollPane = new JScrollPane(table);
        // 再将滚动条组件添加到中间容器中
        add(scrollPane);
        JPanel j=new JPanel();
        OK=new JButton("确定");
        add=new JButton("添加");
        j.add(OK);
        j.add(add);
        add(j);
        scanTool=new ScanDeviceTool();
    }
    private void initEvent(){
        OK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                main.IP=table.getValueAt(table.getSelectedRow(),1).toString();
                scanTool.destory();
                dispose();
            }
        });

        new Thread(new Runnable() {
            public void run() {
                ipList=scanTool.scan();
                if(ipList.size()>0){
                    for(String ip:ipList){
                        tableModel.addRow(new String[]{"b",ip,"2019-06-24 11:36"});
                        try{
                            Thread.sleep(100);
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                int countNow=0;
                do{
                    countNow=scanTool.getPercent();
                    setTitle("正在扫描中....("+countNow+"/225)");
                    try{
                        Thread.sleep(1000);
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }while (countNow!=225);
                setTitle("已完成扫描共"+ipList.size()+"设备在线");
            }
        }).start();
    }
}
