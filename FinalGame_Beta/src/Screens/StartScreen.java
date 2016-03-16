package Screens;

import java.awt.event.KeyEvent;
import asciiPanel.AsciiPanel;
import Main.ApplicationMain;

public class StartScreen implements Screen {

	private String version;
	//public void currentVersion(String version){ this.version = version; }

	public StartScreen(String version){
		this.version = version;
	}
	
	public void displayOutput(AsciiPanel terminal) {
		terminal.write("Build Version " + version, 1, 1);
		terminal.writeCenter("-- press [enter] to start --", 22);
	
	}
	
	public Screen respondToUserInput(KeyEvent key) {
		return key.getKeyCode() == KeyEvent.VK_ENTER ? new PlayScreen() : this;
	}
}
