package com.andmin77.srtmaker;

import javax.swing.SwingUtilities;


public class EntryPoint {     
    public static void main(String[] args) throws Exception { 
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    new MainForm().displayGUI();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}