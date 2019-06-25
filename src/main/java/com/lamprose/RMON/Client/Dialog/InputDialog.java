package com.lamprose.RMON.Client.Dialog;

import com.lamprose.RMON.Client.Frame.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InputDialog extends JDialog {
    Client main;
    String input;
    public InputDialog(Client Owner,boolean modal,String Title){
        super(Owner,Title,modal);
        main=Owner;
        init();
        setLayout(new FlowLayout());
        pack();
        setLocation(Owner.getX()+100,Owner.getY()+100);
        setVisible(true);
    }

    private void init(){
        final JTextField jTextField=new JTextField(20);
        add(jTextField);
        JButton jButton=new JButton("确定");
        add(jButton);
        jButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                input=jTextField.getText();
                dispose();
            }
        });
    }

    public String returnInput(){
        return input;
    }
}
