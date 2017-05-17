package we.software.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.awt.*;

import java.awt.event.*;

import javax.swing.*;

import javax.swing.border.*;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.DefaultCaret;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

public class ChatGui extends JPanel implements Runnable{

	public JTextField chatInput;
	public JTextPane chatHistory;
	public Thread chatThread;
	public boolean chatRunning = false;
	private KeyInput kp;

	public ChatGui() {

        setBounds(1, 641, 623, 125);
        //setOpaque(false);
        setLayout(new GridBagLayout());
        this.setBackground(Color.BLACK);

        chatInput = new JTextField();
        Font f1 = new Font(Font.SANS_SERIF, Font.PLAIN, 15);
        chatInput.setFont(f1);
        chatInput.setForeground(Color.white);
        chatInput.setOpaque(false);

        //Font f2 = new Font("Dialog", Font.ITALIC, 15);
        chatHistory = new JTextPane();
        //chatHistory.setFont(f2);
        chatHistory.setAutoscrolls(true);
        chatHistory.setEditable(false);
        chatHistory.setOpaque(false);

        kp = new KeyInput();


        DefaultCaret caret = (DefaultCaret)chatHistory.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        JScrollPane js = new JScrollPane(chatHistory);
        js.getViewport().setOpaque(false);
        js.setOpaque(false);
        js.setVisible(true);
        js.setAutoscrolls(true);


        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.ipady = 1;
        c.ipady = 70;
        add(js, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridx = 0;
        c.gridy = 1;
        add(chatInput, c);
        addKeyListener(kp);
	}

	/*public void init(){

	    setBounds(1, 641, 623, 125);
        setOpaque(false);
        setLayout(new GridBagLayout());

        chatInput = new JTextField();
        Font f1 = new Font("Dialog", Font.PLAIN, 15);
        chatInput.setFont(f1);
        chatInput.setForeground(Color.white);
        chatInput.setOpaque(false);

        Font f2 = new Font("Dialog", Font.ITALIC, 15);
        chatHistory = new JTextPane();
        chatHistory.setFont(f2);
        chatHistory.setAutoscrolls(true);
        chatHistory.setEditable(false);
        chatHistory.setOpaque(false);

        kp = new KeyInput();


        DefaultCaret caret = (DefaultCaret)chatHistory.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        JScrollPane js = new JScrollPane(chatHistory);
        js.getViewport().setOpaque(false);
        js.setOpaque(false);
        js.setVisible(true);
        js.setAutoscrolls(true);


        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.ipady = 1;
        c.ipady = 70;
        add(js, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridx = 0;
        c.gridy = 1;
        add(chatInput, c);
        addKeyListener(kp);

    }*/

    public void appendToPane(String msg, int choice) {
        StyledDocument doc = chatHistory.getStyledDocument();
        Style style = chatHistory.addStyle("I'm a Style", null);
        switch(choice){
        case 0:{
        	StyleConstants.setBold(style, false);
            StyleConstants.setUnderline(style, false);
            StyleConstants.setFontSize(style, 15);
            StyleConstants.setFontFamily(style, Font.DIALOG);
            StyleConstants.setItalic(style, true);
            StyleConstants.setForeground(style, Color.WHITE);
            break;
        }
        case 1:{
        	StyleConstants.setBold(style, false);
            StyleConstants.setUnderline(style, false);
            StyleConstants.setFontSize(style, 15);
            StyleConstants.setFontFamily(style, Font.DIALOG);
            StyleConstants.setItalic(style, true);
            StyleConstants.setForeground(style, Color.orange);
            break;
        }
        case 2:{
        	StyleConstants.setBold(style, false);
            StyleConstants.setUnderline(style, false);
            StyleConstants.setFontSize(style, 15);
            StyleConstants.setFontFamily(style, Font.DIALOG);
            StyleConstants.setItalic(style, true);
            StyleConstants.setForeground(style, Color.CYAN);
            break;
        }
        case 3:{
        	StyleConstants.setBold(style, false);
            StyleConstants.setUnderline(style, true);
            StyleConstants.setFontSize(style, 15);
            StyleConstants.setFontFamily(style, Font.DIALOG);
            StyleConstants.setItalic(style, true);
            StyleConstants.setForeground(style, Color.PINK);
            break;
        }
        case 4:{
        	StyleConstants.setBold(style, true);
            StyleConstants.setUnderline(style, true);
            StyleConstants.setFontSize(style, 15);
            StyleConstants.setFontFamily(style, Font.DIALOG);
            StyleConstants.setItalic(style, true);
            StyleConstants.setForeground(style, Color.RED);
            break;
        }
        }
        
        
        try { doc.insertString(doc.getLength(), msg,style); }
        catch (BadLocationException e){
            System.out.println(e.getStackTrace());
        }

    }

    @Override
    public void run() {

        //init();

        System.out.println("Chat started");
        while(chatRunning){
            if(kp.send){
               // appendToPane("You: "+ chatInput.getText() + "\n", 0);
                //chatInput.setText("");
            }
        }

        System.out.println("Chat stopped");
        stop();

    }


    public void start(){

        if(chatRunning) return;

        chatThread = new Thread(this);
        chatRunning = true;
        chatThread.start();
    }

    public void stop(){

        if(!chatRunning) return;

        chatRunning = false;

        try{
            chatThread.join();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}


