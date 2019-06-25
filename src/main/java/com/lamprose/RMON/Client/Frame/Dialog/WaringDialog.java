package com.lamprose.RMON.Client.Frame.Dialog;

import com.lamprose.RMON.Client.Frame.Client;

import javax.swing.*;

public class WaringDialog extends JDialog {
    Client main;
    String warninString;
    public WaringDialog(Client Owner,boolean modal,String Warning){
        super(Owner,"警告",modal);
        main=Owner;
        warninString=Warning;
        init();
        pack();
        setLocation(Owner.getX()+100,Owner.getY()+100);
        setVisible(true);
    }

    private void init(){
        add(new JLabel(warninString));
    }
}
