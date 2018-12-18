package com.cx;

import javax.swing.JTextArea;

public class Message {
	
	public static JTextArea statusTextArea ;
	
	public static void show(String str , boolean isAppend){
		
		if(isAppend){
			statusTextArea.append(str + "\r\n");
		}else{
			statusTextArea.setText(str + "\r\n");
		}
		
	}
	
	public static void showBR(){
		statusTextArea.append("----------------------------------------------------------\r\n");
	}
	

}
