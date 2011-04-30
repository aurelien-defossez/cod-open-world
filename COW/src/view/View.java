/**
 * View - This class represents generic view for the game.
 */

package view;

import java.awt.Component;
import java.util.Collection;
import main.CowException;
import org.apache.log4j.Logger;
import com.ApiCall;
import com.GameListener;
import com.ai.Ai;

public abstract class View implements GameListener, KeyboardListener {
	// -------------------------------------------------------------------------
	// Constants
	// -------------------------------------------------------------------------
	
	public static final int SET_FRAME = 1;
	public static final int UPDATE_SCORE = 2;
	
	public static final int PRINT_TEXT = 10;
	
	public static final int DISPLAY_GRID = 20;
	
	public static final int CREATE_ENTITY = 50;
	public static final int DELETE_ENTITY = 51;
	public static final int MOVE_ENTITY = 52;
	public static final int ROTATE_ENTITY = 53;
	
	// -------------------------------------------------------------------------
	// Enumeration
	// -------------------------------------------------------------------------
	
	public enum ViewType {
		None, Console, Text, V2D, V3D
	};
	
	// -------------------------------------------------------------------------
	// Class attributes
	// -------------------------------------------------------------------------
	
	private Logger logger = Logger.getLogger(View.class);
	
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	protected KeyboardController keyboardController;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	public View(KeyboardController keyboardController) {
		this.keyboardController = keyboardController;
		keyboardController.addListener(this);
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	@Override
	public void initGame(Collection<Ai> ais) {
		// Do nothing
	}
	
	@Override
	public void endGame() {
		// Do nothing
	}
	
	@Override
	public void setFrame() {
		// Do nothing
	}
	
	@Override
	public void updateScore() {
		// Do nothing
	}
	
	@Override
	public final void callViewFunction(ApiCall call) {
		try {
			switch (call.getFunctionId()) {
			case PRINT_TEXT:
				printText((String) call.getParameter(0).getValue());
				break;
			
			case DISPLAY_GRID:
				displayGrid((Integer) call.getParameter(0).getValue(),
					(Integer) call.getParameter(1).getValue(), (Integer) call
						.getParameter(2).getValue(), (Integer) call
						.getParameter(3).getValue(), (Integer) call
						.getParameter(4).getValue(), (Integer) call
						.getParameter(5).getValue(), (Integer) call
						.getParameter(6).getValue());
				break;
			
			case CREATE_ENTITY:
				createEntity((Integer) call.getParameter(0).getValue(),
					(Integer) call.getParameter(1).getValue());
				break;
			
			case DELETE_ENTITY:
				deleteEntity((Integer) call.getParameter(0).getValue());
				break;
			
			case MOVE_ENTITY:
				moveEntity((Integer) call.getParameter(0).getValue(),
					(Integer) call.getParameter(1).getValue(), (Integer) call
						.getParameter(2).getValue());
				break;
			
			case ROTATE_ENTITY:
				rotateEntity((Integer) call.getParameter(0).getValue(),
					(Integer) call.getParameter(1).getValue());
				break;
			}
		} catch (CowException e) {
			logger.error(e.getMessage());
		}
	}
	
	// -------------------------------------------------------------------------
	// Optional abstract methods
	// -------------------------------------------------------------------------
	
	protected void printText(String text) {
	}
	
	protected void displayGrid(int x0, int y0, int x1, int y1, int xSpacing,
		int ySpacing, int color) {
	}
	
	protected void createEntity(int definitionId, int id) {
	}
	
	protected void deleteEntity(int id) {
	}
	
	protected void moveEntity(int id, int dx, int dy) {
	}
	
	protected void rotateEntity(int id, int angle) {
	}
	
	// -------------------------------------------------------------------------
	// Abstract methods
	// -------------------------------------------------------------------------
	
	public abstract Component getComponent();
	
	public abstract boolean isReady();
	
	public abstract void keyReleased(int key);
}
