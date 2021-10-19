package com.kanistra.converter;

import javax.swing.*;
import java.io.File;

public class OpenMenuButton extends JMenuItem {
    public OpenMenuButton(SessionsLib sessionsLib, Interface i) {
        setText("Open...");
        JFileChooser fc = new JFileChooser();
        addActionListener(e -> {
            System.out.println("open");
            int returnVal = fc.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                System.out.println(file.getAbsolutePath());
                sessionsLib.addCamera(new Camera(file.getAbsolutePath()));
                i.loadInterface();
            } else {
                System.out.println("cancel");
            }
        });
    }
}
