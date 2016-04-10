package Screens;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.*;
import org.apache.commons.lang3.text.WordUtils;

import asciiPanel.AsciiPanel;

public class PopupHandeler implements Screen{

//stuff for dilogue
	public String text;
	int topX;
	int topY;
	int width;
	int height;

	boolean display;

	int lineOffset;
	
	//stuff for numboxes
	int numX;
	int numY;
	int numValue;
	
	boolean numActive;
	public PopupHandeler(int x, int y, int width, int height){
		//stuff for dilogue
		text = "Beware the Jabberwock, my son! The jaws that bite, the claws that catch! Beware the Jubjub bird, and shun The frumious Bandersnatch! He took his vorpal sword in hand; Long time the manxome foe he sought- So rested he by the Tumtum tree And stood awhile in thought And, as in uffish thought he stood, The Jabberwock, with eyes of flame, Came whiffling through the tulgey wood, And burbled as it came! One, two! One, two! And through and through The vorpal blade went snicker-snack! He left it dead, and with its head He went galumphing back. And hast thou slain the Jabberwock? Come to my arms, my beamish boy! O frabjous day! Callooh! Callay! He chortled in his joy. Twas brillig, and the slithy toves Did gyre and gimble in the wabe: All mimsy were the borogoves, And the mome raths outgrabe.";
		topX = x;
		topY = y;
		this.width = width;
		this.height = height;
		display = true;
		lineOffset = 0;
		numValue = 31;
		numActive = true;
	
	
	
	
	
	}
	
	


	@Override
	public void displayOutput(AsciiPanel terminal) {
		// TODO Auto-generated method stub
		textBoxDisplay(terminal);
		this.numBoxDisplay(terminal, numX, numY);
	}






	@Override
	public Screen respondToUserInput(KeyEvent key) {
		// TODO Auto-generated method stub
		switch(key.getKeyCode()){
			case KeyEvent.VK_ENTER: this.next(); numActive=false; break;
			case KeyEvent.VK_NUMPAD1: this.setDialouge("Test"); break;
			
			//these key imports are for the num box
			case KeyEvent.VK_DOWN: if(numValue-1 >= 0 && numActive){numValue--;} break;
			case KeyEvent.VK_MINUS: if(numValue-1 >= 0 && numActive){numValue--;} break;
			case KeyEvent.VK_UP: if(numValue+1 <=999 && numActive){numValue++;} break;
			case KeyEvent.VK_EQUALS: if(numValue+1 <=999 && numActive){numValue++;} break;
			
		}
			return this;
	}
	
	
	
			
				
public void numBoxDisplay(AsciiPanel terminal, int x, int y){
	
	//forces the value into the proper range
	if(numValue > 999){numValue = 999;}
	else if(numValue<0){numValue= 0;}
	
	//formats the number int the turms of 000
	DecimalFormat formatter = new DecimalFormat("000");
	String aFormatted = formatter.format(numValue);
	 
	
	
		//makes sure that the numbox is active
		if(this.numActive){
			fillScreenArea(terminal, x, y, 5, 2);
			drawBorder(terminal, Color.yellow, x, y, 4, 2);
			terminal.write(aFormatted, x+1, y+1);
		
		}}


private void textBoxDisplay(AsciiPanel terminal){
	
	
	if(display){
		//These two funtions draw the box
		fillScreenArea(terminal,  topX,  topY,  width,  height);
		drawBorder(terminal, Color.yellow,  topX,  topY,  width,  height);
		
		//this snip breaks the text into an array of wraped lines
		String textStr[] = WordUtils.wrap(text, this.width-2).split("\\r?\\n");
		 
		//this if statment detects when the reader is at the end of the statment
		if(textStr.length>lineOffset){
			
			//text display loop
			 for(int l = 0; l+lineOffset<textStr.length && l<height-2;l++){
			//makes sure the line is not null before trying to write it	 
			if(textStr[l+lineOffset] != null){
				
			//writes each line
			terminal.write(textStr[l+lineOffset], topX+1, topY+1+l);
			
			 }}
	}else{//This else links back to the end check if statment
		//sets the text to null
		 text = null;
		 
		 //resets the Line Offset
		 lineOffset = 0;
		 //Writes a message to let the player know that they have reached the end of the diolouge they are reading
		 terminal.write("---Message End---", topX=2, topY+1);
		 
		 //sets the display of this box to false
		 display = false;
	
		}
	
	}}

private void next(){
	
	
	 if(text!=null){
	lineOffset +=height-2;
	 }
	 
 }
public void setDialouge(String s){
	
	this.text = s;
	this.lineOffset = 0;
	this.display = true;
}

//use this to display the text box
public void numBoxReset(int x, int y){
	this.numX = x;
	this.numY = y;
	numValue = 0;
	numActive = true;
}
	
//use this to read from it
public int getNumBoxValue(){return numValue;}


			


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
				
				
			}}}


}






