package Screens;

import java.awt.event.KeyEvent;
import java.util.List;

import asciiPanel.AsciiPanel;
import Creature.Creature;

import java.awt.Color;
import Items.InventorySlot;


public class InventoryScreen implements Screen {
	int topx;
	int topy;
	int mHeight;
	int mWidth;
	int menueState = 0;
	
	 int currentSelected = 0;
	 int offset = 0;
	 boolean active = false;
	
	private Creature player;
	
	public InventoryScreen(int tx, int ty, int width, int height, Creature player){
		
		
		this.topx = tx;
		this.topy = ty;
		this.mHeight = height;
		this.mWidth = width;
		this.player = player;
		
		
	}
	
	public void displayOutput(AsciiPanel terminal) {
		
		fillScreenArea(terminal, topx, topy, mWidth, mHeight);
		drawBorder(terminal, AsciiPanel.brightYellow);
		drawHeader(terminal, "Inventory", topx, topy+1, mWidth, 1, AsciiPanel.brightYellow);
		//drawInvList(terminal, player.inv.inventoryList, topx+1, topy+3, mHeight-2);
		drawInvList(terminal, player.inv.inventoryList, topx+2, topy+3, mHeight);
	
	}

	@Override
	public Screen respondToUserInput(KeyEvent e) {
		switch(e.getKeyCode()){
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_S:
			if(currentSelected+offset<player.inv.inventoryList.size()-1){
			 if(currentSelected< mHeight-4 ){currentSelected++;}else{offset++;}
			 
			}
			System.out.println(currentSelected +" "+offset);
		break;
		case KeyEvent.VK_UP:
		case KeyEvent.VK_W:
			if(currentSelected>0){currentSelected--;}else if(offset>0){offset--;}
		break;
		
		case KeyEvent.VK_E:
			if (player.inv.inventoryList.get(currentSelected+offset).item == player.weapon() || player.inv.inventoryList.get(currentSelected+offset).item == player.armor())
				player.unequip(player.inv.inventoryList.get(currentSelected+offset).item);
			else
				player.equip(player.inv.inventoryList.get(currentSelected+offset).item);
		break;
		case KeyEvent.VK_Q:
			player.drop(player.inv.inventoryList.get(currentSelected+offset));
			if (currentSelected > player.inv.inventoryList.size() - 1){
				currentSelected--;
			}
		break;
		case KeyEvent.VK_I:return null;
		case KeyEvent.VK_ESCAPE: return null;
		}
		return this;
		
	}
	
//-------------------------------Support code starts here-----------------------------------------------------------
	public void drawBorder(AsciiPanel terminal, Color c){
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
		
		
	}
	
	//Draw the header of the inventory menue
	private void drawHeader(AsciiPanel terminal, String title, int topx, int topy, int width, int height, Color color){
		for(int x = topx+1; x<topx+width; x++){
			
				terminal.write((char)196, x, topy+height, color);
				
				
			
		}
		terminal.write(title, topx+width/2-title.length()/2, topy);
	}
	
	
	private void drawInvList(AsciiPanel terminal, List<InventorySlot> inv, int topx, int topy, int height){
		
		
		for(int i = 0; i<inv.size()-offset; i++){
			if(i<height-topy){
			String line = inv.get(i+offset).item.getDisplayName();
			if(inv.get(i + offset).item.stackSize() > 1){
				terminal.write(" x"+inv.get(i+offset).quantity, topx+inv.get(i+offset).item.getDisplayName().length(), topy+i);
			}
			if(inv.get(i+offset).item == player.weapon() || inv.get(i+offset).item == player.armor()){
				line += " " + (char)15;
			}
			terminal.write(line, topx, topy+i);
			if(i == currentSelected){
				
				terminal.write((char)16, topx-1, topy+i, AsciiPanel.brightYellow);
				
			}
			}
		}
		
	}
}
