
package view.text;

import java.awt.Component;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import view.KeyboardController;
import view.View;

public class ViewText extends View {
	// -------------------------------------------------------------------------
	// Constants
	// -------------------------------------------------------------------------
	
	private static final long serialVersionUID = 1L;
	
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	private JTextArea textArea;
	private JScrollPane textPane;
	private JScrollBar scrollBar;
	private boolean autoScroll;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	public ViewText(KeyboardController keyboardController) {
		super(keyboardController);
		
		autoScroll = true;
		
		// Create text area
		textArea = new JTextArea();
		textArea.setEditable(false);
		textPane = new JScrollPane(textArea);
		scrollBar = textPane.getVerticalScrollBar();
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	@Override
	public Component getComponent() {
		return textPane;
	}
	
	@Override
	public boolean isReady() {
		return true;
	}
	
	@Override
	public void keyReleased(int key) {
		// Do nothing
	}
	
	// -------------------------------------------------------------------------
	// Protected methods
	// -------------------------------------------------------------------------
	
	@Override
	protected void printText(String text) {
		textArea.append(text);
		
		if (autoScroll) {
			scrollBar.setValue(scrollBar.getMaximum());
		}
	}
}
