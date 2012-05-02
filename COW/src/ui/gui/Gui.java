
package ui.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Collection;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import main.CowException;
import sim.Scheduler;
import ui.Ui;
import view.KeyboardController;
import view.MouseController;
import view.View;
import view.View.ViewType;
import view.text.ViewText;
import view.v2d.View2D;
import com.ai.Ai;

public class Gui extends Ui {
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	private JFrame window;
	
	private ControlPanel controlPanel;
	private ScorePanel scorePanel;
	private View view;
	
	private KeyboardController keyboardController;
	private MouseController mouseController;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	public Gui(final Scheduler scheduler, ViewType viewType) {
		super(scheduler);
		
		// Create window
		window = new JFrame("Cod'Open World");
		window.setLayout(new BorderLayout());
		window.setMinimumSize(new Dimension(640, 480));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.addWindowListener(new WindowListener() {
			public void windowOpened(WindowEvent e) {
			}
			
			public void windowIconified(WindowEvent e) {
			}
			
			public void windowDeiconified(WindowEvent e) {
			}
			
			public void windowDeactivated(WindowEvent e) {
			}
			
			public void windowClosed(WindowEvent e) {
			}
			
			public void windowActivated(WindowEvent e) {
			}
			
			public void windowClosing(WindowEvent e) {
				try {
					Gui.this.scheduler.stopGame();
				} catch (NullPointerException ex) {
					// Do nothing
				}
			}
		});
		
		// Bind focus listener
		window.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent arg0) {
				keyboardController.disableAll();
			}
			
			@Override
			public void focusGained(FocusEvent arg0) {
			}
		});
		
		// Create keyboard and mouse controllers
		keyboardController = new KeyboardController();
		mouseController = new MouseController();
		
		// Create view
		switch (viewType) {
		case Text:
			view = new ViewText(keyboardController);
			break;
		
		case V2D:
			view =
				new View2D(scheduler.getOrchestrator().getGameName(),
					keyboardController, mouseController);
			break;
		
		default:
			throw new CowException("View type " + viewType
				+ " not supported by the GUI.");
		}
		
		// Create view panel
		controlPanel = new ControlPanel(scheduler);
		scorePanel = new ScorePanel();
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, view.getComponent(), scorePanel);
		splitPane.setDividerLocation(1.0);
		splitPane.setResizeWeight(1);
		splitPane.setEnabled(false);
		
		keyboardController.addListener(controlPanel);
		
		// Add panels
		window.add(controlPanel, BorderLayout.NORTH);
		window.add(splitPane, BorderLayout.CENTER);
		window.setVisible(true);
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	public View getView() {
		return view;
	}
	
	public void enableControlPanel(boolean enabled) {
		controlPanel.setEnabled(enabled);
	}
	
	@Override
	public void initGame(Collection<Ai> ais) {
		scorePanel.initIas(ais);
	}
	
	@Override
	public void endGame() {
		enableControlPanel(false);
	}
	
	@Override
	public void updateScore(long nbFrames) {
		scorePanel.updateScore(nbFrames);
	}
	
	@Override
	public void updateColors() {
		scorePanel.updateColors();
	}
	
	public void displayError(String title, String message) {
		JOptionPane.showMessageDialog(window, message, title,
			JOptionPane.ERROR_MESSAGE);
	}
}
