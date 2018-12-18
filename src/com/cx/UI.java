package com.cx;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
 
public class UI {
    
   private JFrame mainFrame;
   private JTextField fileTextField ;
   private JButton fileButton ;
   private JTextField countTextField ;
   private JButton startFormatButton;
   private JTextArea statusTextArea ;
   private JTextField lineTextField ;

   
   private class FileButtonHandler implements ActionListener{
	   public void actionPerformed(ActionEvent e){
		   String filePath = getFilePath() ;
		   fileTextField.setText(filePath);
		   Message.show("Selected file : " + filePath,true);
	   }
   }
   
   private class FormatButtonHandler implements ActionListener{
	   public void actionPerformed(ActionEvent e){
		   String filePath = fileTextField.getText();
		   int count = Integer.valueOf(countTextField.getText());
		   Message.showBR();
		   Message.show("Start Formatting ... ",true);
		   Message.show("Selected file : " + filePath,true);
		   Message.show(count + "  chars most in each line ",true);
		   FileFormat fileFormat = new FileFormat(filePath,count);
		   fileFormat.readFile();
	   }
   }
   

   public UI(){
      prepareGUI();
      setMessageTextArea();
      setListener();
   }
   
   public void setMessageTextArea(){
	   Message.statusTextArea = this.statusTextArea ;
   }
   
   
   private void setListener(){
	   
	   fileButton.addActionListener(new FileButtonHandler());
	   startFormatButton.addActionListener(new FormatButtonHandler());
	   
   }

   private void prepareGUI(){
      mainFrame = new JFrame("Line Format");
      mainFrame.setSize(600,600);
      mainFrame.setLayout(new FlowLayout());
      JPanel controlPanel = new JPanel();
      controlPanel.setLayout(new GridLayout(7, 1));
      JPanel filePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
      JPanel countPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
      JPanel linePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
      JPanel formatPanel = new JPanel(new FlowLayout());
      JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
      
      JLabel headerLabel  = new JLabel("This Tool Is Provided By BIM CD Squad , Please contact cdxiaoch@cn.ibm.com for any questions");    
      JLabel fileLabel = new JLabel("Please select file : ");
      fileTextField = new JTextField("");
      fileTextField.setPreferredSize(new Dimension(300,30));
      fileButton = new JButton("Select File");
      JLabel countLable = new JLabel("How many words at most you want in one line : ");
      countTextField = new JTextField("");
      countTextField.setPreferredSize(new Dimension(50,30));
      countTextField.setDocument(new NumberValidator(4));
      countTextField.setText("65");
      JLabel lineLable = new JLabel("How many lines at most you want in one file : ");
      lineTextField = new JTextField("");
      lineTextField.setPreferredSize(new Dimension(50,30));
//      lineTextField.setText("50");
      lineTextField.setDocument(new NumberValidator(4));
      startFormatButton = new JButton("Start Format");
      startFormatButton.setPreferredSize(new Dimension(200,30));
      JLabel statusLable = new JLabel("Below is status details : ");
      statusTextArea = new JTextArea("Please select file first \r\n");
      statusTextArea.setLineWrap(true);
      statusTextArea.setEditable(false);
      
      JScrollPane scroll = new JScrollPane();
      scroll.setPreferredSize(new Dimension(550,200));
      scroll.setViewportView(statusTextArea);
      
      filePanel.add(fileTextField);
      filePanel.add(fileButton);
      countPanel.add(countLable);
      countPanel.add(countTextField);
      linePanel.add(lineLable);
      linePanel.add(lineTextField);
      formatPanel.add(startFormatButton);
      statusPanel.add(scroll);
      
      controlPanel.add(headerLabel);
      controlPanel.add(fileLabel);
      controlPanel.add(filePanel);
      controlPanel.add(countPanel);
      controlPanel.add(linePanel);
      controlPanel.add(formatPanel);
      controlPanel.add(statusLable);
      mainFrame.add(controlPanel);
      mainFrame.add(statusPanel);
      mainFrame.setVisible(true); 

   }
   
   
   private String getFilePath(){
	   String fileName = "";
	   JFileChooser  fileDialog = new JFileChooser();
	   int returnVal = fileDialog.showOpenDialog(mainFrame);
       if (returnVal == JFileChooser.APPROVE_OPTION) {
          java.io.File file = fileDialog.getSelectedFile();
          fileName =  file.getPath();
       }
       return fileName;
   }
   
}
