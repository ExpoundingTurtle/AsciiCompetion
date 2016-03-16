package Screens;

import java.awt.event.KeyEvent;
import asciiPanel.AsciiPanel;

public class LoseScreen implements Screen{
	
	public void displayOutput(AsciiPanel terminal){
		terminal.write("Your health has reached 0.", 28, 1);
		terminal.write("GAME OVER", 35, 11);
		terminal.writeCenter("-- press [enter] to restart --", 22);
	}
	
	public Screen respondToUserInput(KeyEvent key) {
		return key.getKeyCode() == KeyEvent.VK_ENTER ? new PlayScreen() : this;
	}
}
