package com.kanistra.converter;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.*;

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
    public static final int SESSION_PANEL_HEIGHT = 40;
    public static final int SESSION_DETAILS_PANEL_HEIGHT = 370;
    public static final int ALL_CAMERAS_PANEL_HEIGHT = 120;

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
    public static final String TIME_TITLE = "Remaining Time";
    public static final String SET_ALL_TITLE = "Set this params to all cameras in session";
    public static final String FRAMES_TITLE = "Frames";
    public static final String FRAME_PROGRESS_TITLE = "Progress";
    public static final String START_ALL_CAMERAS_TITLE = "Start convert all cameras";
    public static final String STOP_ALL_CAMERAS_TITLE = "Stop convert all cameras";
    public static final String THREADS_AMOUNT_TITLE = "Threads amount";

    public Interface(SessionsLib sessionsLib) {
        mSessionsLib = sessionsLib;
        mainFrame = new JFrame(WINDOW_TITLE);
        setLayout(new BorderLayout());
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        mainFrame.add(this, BorderLayout.CENTER);

        new DropTarget(mainFrame, new DropTargetListener() {
            @Override
            public void dragEnter(DropTargetDragEvent dtde) {

            }

            @Override
            public void dragOver(DropTargetDragEvent dtde) {

            }

            @Override
            public void dropActionChanged(DropTargetDragEvent dtde) {

            }

            @Override
            public void dragExit(DropTargetEvent dte) {

            }

            @Override
            public void drop(DropTargetDropEvent dtde) {
                dtde.acceptDrop(DnDConstants.ACTION_LINK);
                Transferable transferable = dtde.getTransferable();
                DataFlavor[] flavors = transferable.getTransferDataFlavors();
                for (DataFlavor flavor : flavors) {
                    try {
                        if (flavor.isFlavorJavaFileListType()) {
                            String data = transferable.getTransferData(flavor).toString();
                            data = data.substring(1, data.length() - 1);
                            String[] files = data.split(", ");
                            for (String file : files) {
                                System.out.println(file);
                                mSessionsLib.addCamera(new Camera(file));
                                loadInterface();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                dtde.dropComplete(true);
            }
        });

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
        if (mCurrentSession == null) {
            JPanel panel = new JPanel();
            panel.setPreferredSize(new Dimension(WINDOW_WIDTH - SESSIONS_PANEL_WIDTH - 20, 400));
            camerasScrollPane.add(panel);
        }
        camerasScrollPane.setHorizontalScrollBar(null);
        add(camerasScrollPane, BorderLayout.LINE_END);

        revalidate();
    }
}
