
package ui.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlAction implements ActionListener {
	// -------------------------------------------------------------------------
	// Enumeration
	// -------------------------------------------------------------------------
	
	public enum ButtonType {
		Pause, Stop, NextStep
	};
	
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	private ControlPanel controlPanel;
	private ButtonType type;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	public ControlAction(ControlPanel controlPanel, ButtonType type) {
		this.controlPanel = controlPanel;
		this.type = type;
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (type) {
		case Pause:
			controlPanel.playPause();
			break;
		case Stop:
			controlPanel.stopGame();
			break;
		case NextStep:
			controlPanel.nextStep();
			break;
		}
	}
}
