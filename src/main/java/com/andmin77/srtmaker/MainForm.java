package com.andmin77.srtmaker;

import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.Date;
import java.util.Calendar;
import javax.swing.*;

public class MainForm {
    
    private final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    
    private final int width = 700;
    private final int height = 700;
    private final double consolePanelWeightY = 0.2;
    
    
    private Timer timer;
    private String lastTime = "00:00:00";
    
    public void displayGUI() throws Exception { 
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame frame = new JFrame();
        frame.setLayout(null);        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds((int) (screenSize.getWidth() / 2 - width / 2), (int) (screenSize.getHeight() / 2 - height / 2), width, height);
        
        Container container = frame.getContentPane();
        container.setLayout(new GridBagLayout());   

        JPanel consolePanel = new JPanel();
        consolePanel.setLayout(null);
        consolePanel.setBackground(Color.lightGray);     

        JButton playButton = new JButton("Play");
        playButton.setBounds(10, 10, 100, 50);

        JButton pauseButton = new JButton("Pause");
        pauseButton.setBounds( playButton.getX() + playButton.getWidth() + 10, playButton.getY(), playButton.getWidth(), playButton.getHeight());        
        pauseButton.setEnabled(false);
        
        JTextField timelineText = new JTextField();
        timelineText.setHorizontalAlignment(JTextField.CENTER);
        timelineText.setBounds( pauseButton.getX() + pauseButton.getWidth() + 10, pauseButton.getY(), 200, playButton.getHeight() );
        
        JTextField lineText = new JTextField();
        lineText.setHorizontalAlignment(JTextField.CENTER);
        lineText.setBounds( timelineText.getX() + timelineText.getWidth() + 10, timelineText.getY(), 50, timelineText.getHeight() );
        
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date d = null;
                try {
                    d = sdf.parse( timelineText.getText() );
                } catch (ParseException ex) {
                    
                }
                if ( d == null ) {
                    try {
                        d = sdf.parse( "00:00:00" );
                    } catch (ParseException ex) {
                        lastTime = "00:00:00";
                    }
                }
                int numberOfLine = 0;
                try {
                    numberOfLine = Integer.parseInt( lineText.getText() );
                } catch (Exception ex) {

                }
                lineText.setText( "" + numberOfLine);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(d);
                calendar.add(Calendar.SECOND, 1);
                timelineText.setText( sdf.format(calendar.getTime()) );
            }
        });
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playButton.setEnabled(false);
                timelineText.setEnabled(false);
                lineText.setEnabled(false);
                pauseButton.setEnabled(true);
                timer.start();
            }
        });
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pauseButton.setEnabled(false);
                timelineText.setEnabled(true);
                lineText.setEnabled(true);
                playButton.setEnabled(true);
                timer.stop();
            }
        });
        
        consolePanel.add(playButton);
        consolePanel.add(pauseButton);
        consolePanel.add(timelineText);
        consolePanel.add(lineText);
        container.add(consolePanel, new GridBagConstraints(0, 0, 1, 1, 1, consolePanelWeightY, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
                
        JTextArea editorText = new JTextArea();    
        editorText.setLineWrap(true);
        editorText.setWrapStyleWord(true);
        editorText.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if ( !playButton.isEnabled() ) {
                    String text = editorText.getText();
                    editorText.setCaretPosition(editorText.viewToModel(e.getPoint()));
                    int caretPosition = editorText.getCaretPosition();

                    while ( text.length() > caretPosition && text.charAt(caretPosition) != ' '  ) {
                        caretPosition++;
                    }

                    int endIndex = caretPosition;
                    String selectedText = text.substring(0, endIndex);

                    int numberOfLine = 0;
                    try {
                        numberOfLine = Integer.parseInt( lineText.getText() );
                    } catch (Exception ex) {

                    }
                    String endTime = timelineText.getText() + ",000";

                    System.out.println( numberOfLine );
                    System.out.println( String.format("%s --> %s", lastTime, endTime ) );
                    System.out.println( selectedText );
                    System.out.println( " ");

                    lastTime = endTime;
                    editorText.setText( text.substring(endIndex) );
                    numberOfLine++;
                    lineText.setText( "" + numberOfLine);
                }
            }
            @Override
            public void mousePressed(MouseEvent e) { }
            @Override
            public void mouseReleased(MouseEvent e) { }
            @Override
            public void mouseEntered(MouseEvent e) { }
            @Override
            public void mouseExited(MouseEvent e) { }
        });
        
        JScrollPane scroll = new JScrollPane(editorText);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        container.add(scroll, new GridBagConstraints(0, 1, 1, 1, 1, 1 - consolePanelWeightY, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
   
        frame.setVisible(true);
    }        
}