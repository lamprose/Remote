package com.lamprose.RMON.Server;

import com.lamprose.RMON.Server.Thread.MainThread;
import com.lamprose.RMON.Server.Thread.ScreenThread;

/**
 * Hello world!
 *
 */
public class Main {
    public static void main(String[] args) {
        new MainThread().start();
        new ScreenThread().start();
    }
}