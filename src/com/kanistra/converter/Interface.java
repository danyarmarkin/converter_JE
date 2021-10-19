package com.kanistra.converter;

import javax.swing.*;
import java.awt.*;

public class Interface extends JPanel {

    private JFrame mainFrame;
    private SessionsLibPanel mSessionsLibPanel;
    private JMenu mFileMenu;
    private JMenuBar mMenuBar;

    private SessionsLib mSessionsLib;

    public static final int WINDOW_WIDTH = 1080;
    public static final int WINDOW_HEIGHT = 720;
    public static final String WINDOW_TITLE = "Kanistra Video Converter";
    public static final int SESSIONS_PANEL_WIDTH = 300;
    public static final int SESSION_PANEL_HEIGHT = 40;


    public Interface(SessionsLib sessionsLib) {
        mSessionsLib = sessionsLib;
        mainFrame = new JFrame(WINDOW_TITLE);
        setLayout(new BorderLayout());
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        mainFrame.add(this, BorderLayout.CENTER);

        loadInterface();

        // create menu
        mMenuBar = new JMenuBar();
        mFileMenu = new JMenu("File");
        mFileMenu.getAccessibleContext().setAccessibleDescription("Dealing with Files");
        OpenMenuButton openFileItem = new OpenMenuButton(mSessionsLib, this);
        mFileMenu.add(openFileItem);
        mMenuBar.add(mFileMenu);
        mainFrame.setJMenuBar(mMenuBar);

        mainFrame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
    }

    public void loadInterface() {
        removeAll();
        mSessionsLibPanel = new SessionsLibPanel(mSessionsLib);
        JScrollPane sessionsLibScrollPane = new JScrollPane(mSessionsLibPanel);
        sessionsLibScrollPane.setHorizontalScrollBar(null);
        add(sessionsLibScrollPane, BorderLayout.LINE_START);
        revalidate();
    }
}
