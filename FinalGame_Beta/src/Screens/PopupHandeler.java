package Screens;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import asciiPanel.AsciiPanel;

public class PopupHandeler{


	public String text;
	int topX;
	int topY;
	int width;
	int height;
	int scroll;
	boolean display;
	int offset;
	int lineOffset;
	
	public PopupHandeler(int x, int y, int width, int height){
	text = "";
	topX = x;
	topY = y;
	this.width = width;
	this.height = height;
	offset = 0;
	lineOffset = 0;
	}
	
	



	
	public void display(AsciiPanel terminal){
		
	
		if(text != null){
		if(!text.isEmpty()&&  text != ""){
			String[] arr = text.split(" ");
			int lineWidth = width-2;
			
			int[] allLines = new int[height-2];
			
		int numCharacters = ( height-2)*( width-2);
		
		
		fillScreenArea(terminal,  topX,  topY,  width,  height);
		drawBorder(terminal, Color.yellow,  topX,  topY,  width,  height);
		
			if(numCharacters>= text.length()){
				
				if(text.length()<= lineOffset){
					
					text = null; 
					offset=0;
					 
					}
						for(int al = 0; al<height-2;al++){
							allLines[al] = lineWidth;
						}
						
						for(int k = 0; k<arr.length; k++){
							if(allLines[0]>arr[k].length()){
								
								terminal.write(arr[k], topX+1+(lineWidth-allLines[0]), topY+1);
								//System.out.println(lineWidth-allLines[0]);
								allLines[0] -= arr[k].length()+1;
								
								
							}else if(allLines[1]>arr[k].length()){
								allLines[0] = 0;
								terminal.write(arr[k], topX+1+(lineWidth-allLines[1]), topY+2);
								//System.out.println(lineWidth-allLines[1]);
								allLines[1] -= arr[k].length()+1;
							}else if(allLines[2]>arr[k].length()){
								allLines[1] = 0;
								
								terminal.write(arr[k], topX+1+(lineWidth-allLines[2]), topY+3);
								
								allLines[2] -= arr[k].length()+1;
							}else if(allLines[3]>arr[k].length()){
								allLines[2] = 0;
								terminal.write(arr[k], topX+1+(lineWidth-allLines[3]), topY+4);
								//System.out.println(lineWidth-allLines[3]);
								allLines[3] -= arr[k].length()+1;
								//offset += numCharacters+20;
								text = null;
							}
							
						
							
						}
						
						
							}else{
								
								String newS = "";
								for(int al = 0; al<height-2;al++){
									allLines[al] = lineWidth;
								}
								
								if(text.length()>= lineOffset){
								 newS = text.substring(lineOffset);
						
								 
								}else{text = null; offset=0;}
								
								String[] newArr = newS.split(" ");
								
								for(int k = 0; k<newArr.length; k++){
								
									if(allLines[0]>newArr[k].length()){
										
										terminal.write(newArr[k], topX+1+(lineWidth-allLines[0]), topY+1);
										//System.out.println(lineWidth-allLines[0]);
										allLines[0] -= newArr[k].length()+1;
										offset += newArr[k].length()+1;
										
									}else if(allLines[1]>newArr[k].length()){
										allLines[0] = 0;
										terminal.write(newArr[k], topX+1+(lineWidth-allLines[1]), topY+2);
										//System.out.println(lineWidth-allLines[1]);
										allLines[1] -= newArr[k].length()+1;
										offset += newArr[k].length()+1;
									}else if(allLines[2]>newArr[k].length()){
										allLines[1] = 0;
										
										terminal.write(newArr[k], topX+1+(lineWidth-allLines[2]), topY+3);
										
										allLines[2] -= newArr[k].length()+1;
										offset += newArr[k].length()+1;
									}else if(allLines[3]>newArr[k].length()){
										allLines[2] = 0;
										terminal.write(newArr[k], topX+1+(lineWidth-allLines[3]), topY+4);
										//System.out.println(lineWidth-allLines[3]);
										allLines[3] -= newArr[k].length()+1;
										offset += newArr[k].length()+1;
									}else{allLines[3] = 0;  return;}
									
								
									
								}
							}
							}
							}
						}
			
				

	
	
public void next(){
	
	
		 
		lineOffset = offset;
		 
	 }
	

			


	private void drawBorder(AsciiPanel terminal, Color c, int topx, int topy, int mWidth, int mHeight){
		for(int x = 0; x<terminal.getWidthInCharacters(); x++){
					char topAndBottom = (char)196;
					char sides = (char)179;
			
			
					for(int y = 0; y<terminal.getHeightInCharacters(); y++){
						
						
						if(x>=topx && x<=topx+mWidth && y>=topy && y<=topy+mHeight){
							
							//Draw sides
							if(x == topx ||  x==topx+mWidth ){
							terminal.write(sides, x, y, c);
							}
							//Draw top and bottom
							if(y == topy || y ==topy+mHeight){
								terminal.write(topAndBottom, x, y, c);
							}
							
							//Draw corners
							if(x == topx && y == topy){
								terminal.write((char)218, x, y, c);
							}
							if(x == topx+mWidth && y == topy){
								terminal.write((char)191, x, y, c);
							}
							
							if(x==topx && y == topy+mHeight){
								
								terminal.write((char)192, x, y ,c);
							}
							if(x==topx+mWidth && y==topy+mHeight){
								
								terminal.write((char)217, x, y, c);
							}
						
						
						}}}}
	
	private void fillScreenArea(AsciiPanel terminal, int topx, int topy, int width, int height){
		for(int x = topx; x<topx+width; x++){
			for(int y = topy; y<topy+height; y++){
				terminal.write((char)0, x, y);
				
				
			}
			
			
		}


	}}


