package com.cx;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
public class FileFormat {
	
	private int charNumber ;
	private String inputFileFullName ;
	private String inputFileName ;
	private String inputFilePath ;
	private String inputFileNameWithoutEXT ;
	private String fileExtension ;
	private String outputFloderPath ;
	private final String FLAGNAME = "LineFormat";
	
	public FileFormat(String inputFileFullName,int charNumber){
		this.charNumber = charNumber ;
		this.inputFileFullName = inputFileFullName ;
		this.inputFilePath = getFilePath(inputFileFullName);
		this.inputFileName = getInputFileName(inputFileFullName);
		this.fileExtension = getFileExtension(this.inputFileName);
		this.inputFileNameWithoutEXT = getInputFileNameWithoutEXT(this.inputFileName);
		this.outputFloderPath = getNewFloderPath();
	}
	

	private String getInputFileName(String inputFileFullName){
		return inputFileFullName.substring(inputFileFullName.lastIndexOf(File.separator)+1);
	}
	
	private String getFilePath(String inputFileFullName){
		return inputFileFullName.substring(0,inputFileFullName.lastIndexOf(File.separator)+1);
	}
	
	
	private String getInputFileNameWithoutEXT(String inputFileName){
		return inputFileName.substring(0,inputFileName.lastIndexOf("."));
	}
	
	private String getFileExtension(String inputFileName){
		return inputFileName.substring(inputFileName.lastIndexOf("."));
	}
	
	
	public String getNewFloderPath(){
		StringBuffer folderName = new StringBuffer(this.inputFilePath);
		folderName.append(this.inputFileNameWithoutEXT);
		folderName.append(this.FLAGNAME);
		folderName.append(getCurrentTime());
		return folderName.toString() ;
	}
	
	public String getCurrentTime(){
		Date now = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		return dateFormat.format( now ) ;
	}
	
	public File getFolder(){
		File floder = new File(this.outputFloderPath);
		if(!floder.exists()){
			floder.mkdir();
			Message.show("Create Floder : " + this.outputFloderPath, true);
		}
		return floder;
	}
	
	public String getOutputFileName(int fileCount){
		StringBuffer outputFileName = new StringBuffer(this.inputFileNameWithoutEXT);
		outputFileName.append(this.FLAGNAME);
		if(fileCount != 1){
			outputFileName.append(fileCount);
		}
		return outputFileName.append(this.fileExtension).toString() ;
	}
	
	public File getOutputFile(String outputFileName){
		File floder = getFolder() ;
		File outputFile = null;
		if(floder.exists()){
			outputFile = new File(floder.getPath()+File.separator+outputFileName);
			if(!outputFile.exists()){
				try {
					outputFile.createNewFile();
					Message.show("Create File : " + outputFileName , true);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return outputFile ;
	}
	
	public void readFile(){
		try {
			BufferedReader br = new BufferedReader(new FileReader(this.inputFileFullName));
			File outputFile = getOutputFile(getOutputFileName(1));
			if(outputFile != null && outputFile.exists()){
				BufferedWriter out=new BufferedWriter(new FileWriter(outputFile));
				String line = "";
				Message.show("Start Format Line by Line ... " , true);
				while ((line = br.readLine())!=null){
					out.write(formatLine(line,this.charNumber));
				}
				br.close();
				out.close();
				Message.show("Format End " , true);
				Message.show("Please open new format file :  "  , true);
				Message.show(outputFile.getPath(), true);
				Message.showBR();
			}
			
			} catch (Exception e) {
				e.printStackTrace();
			}
	
	}
	
	private String formatLine(String line , int charNumber){
		
		String beforFormatStr = line.trim() ;
		StringBuffer afterFormatStr = new StringBuffer("");
		while(beforFormatStr.length() > charNumber){
			String str1 =  beforFormatStr.substring(0, charNumber);
			int index = getIndex(str1,charNumber);
			String str2 = str1.substring(0, index);
			beforFormatStr = beforFormatStr.substring(index);
			afterFormatStr.append(str2);
			afterFormatStr.append("\r\n");
		}
		afterFormatStr.append(beforFormatStr);
		afterFormatStr.append("\r\n");
		return afterFormatStr.toString() ;
	}
	
	private int getIndex(String str,int charNumber){
		int index = str.lastIndexOf(",");
		if(index == -1){
			index = str.lastIndexOf("(");
		}
		if(index == -1){
			index = str.lastIndexOf(")");
		}
		if(index == -1){
			index = str.lastIndexOf(" ");
		}
		if(index == -1){
			index = charNumber;
		}
		return index;
	}
}
