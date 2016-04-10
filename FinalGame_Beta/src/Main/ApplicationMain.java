package Main;

import javax.swing.JFrame;
import asciiPanel.AsciiPanel;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import Screens.Screen;
import Screens.StartScreen;

public class ApplicationMain extends JFrame implements KeyListener {
	private static final long serialVersionUID = 1060623638149583738L;
	
	public AsciiPanel terminal;
	private Screen screen;
	
	private int terminalWidth;
	public int terminalWidth() { return terminalWidth; }
	
	private int terminalHeight;
	public int terminalHeight() { return terminalHeight; }
	
	//The current version of the application
	public String version = "0.0.4";
	
	public ApplicationMain(){
		super();
		terminal = new AsciiPanel();
		add(terminal);
		//pack();
		screen = new StartScreen(version);
		addKeyListener(this);
		repaint();
		this.terminalWidth = terminal.getWidthInCharacters();
		this.terminalHeight = terminal.getHeightInCharacters();
		//setResizable(false);
	}
	
	@Override
	public void repaint(){
		terminal.clear();
		screen.displayOutput(terminal);
		super.repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		screen = screen.respondToUserInput(e);
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) { }

	@Override
	public void keyTyped(KeyEvent e) { }
	
	public static void main(String[] args) {
		ApplicationMain app = new ApplicationMain();
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setVisible(true);
		app.setResizable(false);
		app.setSize(726,413);
	}
}
