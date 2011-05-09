
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

	@Override
	protected void displayGrid(int x0, int y0, int x1, int y1, int xSpacing,
		int ySpacing, int color, boolean temporary) {
		// Do nothing
	}

	@Override
	protected void drawLine(int x0, int y0, int x1, int y1, int color,
		boolean temporary) {
		// Do nothing
	}

	@Override
	protected void drawCircle(int x, int y, int radius, int samples, int color,
		boolean temporary) {
		// Do nothing
	}

	@Override
	protected void createEntity(int definitionId, int id) {
		// Do nothing
	}

	@Override
	protected void deleteEntity(int id) {
		// Do nothing
	}

	@Override
	protected void moveEntity(int id, int dx, int dy) {
		// Do nothing
	}

	@Override
	protected void rotateEntity(int id, int angle) {
		// Do nothing
	}

	@Override
	protected void deleteTemporaryShapes() {
		// Do nothing
	}
}
