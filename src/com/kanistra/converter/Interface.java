package com.kanistra.converter;

import javax.swing.*;
import java.awt.*;

public class Interface extends JPanel {

    private final JFrame mainFrame;
    private SessionsLibPanel mSessionsLibPanel;
    private JPanel mSessionDetailsPanel;
    private JMenu mFileMenu;
    private JMenuBar mMenuBar;

    private SessionsLib mSessionsLib;
    private Session mCurrentSession;

    public static final int WINDOW_WIDTH = 1080;
    public static final int WINDOW_HEIGHT = 720;
    public static final String WINDOW_TITLE = "Kanistra Video Converter";
    public static final int SESSIONS_PANEL_WIDTH = 300;
    public static final int SESSION_PANEL_HEIGHT = 50;
    public static final int SESSION_DETAILS_PANEL_HEIGHT = 250;

    public static final Color SELECT_COLOR = new Color(47, 101, 202);
    public static final Color NOT_SELECT_COLOR = Color.lightGray;

    public static final String INPUT_PATH_TITLE = "Input path";
    public static final String OUTPUT_PATH_TITLE = "Output path";
    public static final String START_BUTTON_TITLE = "Start";
    public static final String STOP_BUTTON_TITLE = "Stop";
    public static final String STEP_TITLE = "Step";
    public static final String TOLERANCE_TITLE = "Tolerance";
    public static final String JPG_TITLE = "Save as .jpg";
    public static final String TOTAL_FRAMES_TITLE = "Total frames";
    public static final String OUTPUT_AMOUNT_FRAMES_TITLE = "Output amount frames";
    public static final String TOTAL_SESSIONS_TITLE = "Total sessions";
    public static final String TOTAL_CAMERAS_TITLE = "Total cameras";
    public static final String SPEED_TITLE = "Speed";
    public static final String TIME_TITLE = "Time";

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
        mSessionsLibPanel.addSelectSessionListener(new SelectSessionListener() {
            @Override
            public void onSessionSelected(Session session) {
                mCurrentSession = session;
                loadSessionDetailsPanel();
            }

            @Override
            public void onAllSectionsSelected() {
                mCurrentSession = null;
                loadSessionDetailsPanel();
            }
        });
        JScrollPane sessionsLibScrollPane = new JScrollPane(mSessionsLibPanel);
        sessionsLibScrollPane.setHorizontalScrollBar(null);
        add(sessionsLibScrollPane, BorderLayout.LINE_START);

        mSessionDetailsPanel = new SessionDetailsPanel(mCurrentSession);
        JScrollPane camerasScrollPane = new JScrollPane(mSessionDetailsPanel);
        camerasScrollPane.setHorizontalScrollBar(null);
        add(camerasScrollPane, BorderLayout.LINE_END);

        revalidate();
    }

    public void loadSessionDetailsPanel() {
        remove(1);
        if (mCurrentSession == null) {
            mSessionDetailsPanel = new AllSessionDetailsPanel(mSessionsLib);
        } else {
            mSessionDetailsPanel = new SessionDetailsPanel(mCurrentSession);
        }
        JScrollPane camerasScrollPane = new JScrollPane(mSessionDetailsPanel);
        camerasScrollPane.setHorizontalScrollBar(null);
        add(camerasScrollPane, BorderLayout.LINE_END);

        revalidate();
    }
}
