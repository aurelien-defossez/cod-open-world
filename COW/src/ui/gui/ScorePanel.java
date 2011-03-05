
package ui.gui;

import java.awt.BorderLayout;
import java.util.Collection;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import com.Ai;

public class ScorePanel extends JPanel {
	// -------------------------------------------------------------------------
	// Constants
	// -------------------------------------------------------------------------
	
	private static final long serialVersionUID = 1L;
	
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	private Collection<Ai> ais;
	private JTextArea scoreArea;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	public ScorePanel() {
		super(new BorderLayout());
		scoreArea = new JTextArea();
		add(scoreArea, BorderLayout.CENTER);
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	public void initIas(Collection<Ai> ais) {
		this.ais = ais;
		updateScore();
	}
	
	public void updateScore() {
		scoreArea.setText("");
		
		for (Ai ai : ais) {
			scoreArea.append(" " + ai.getName() + " (" + ai.getPlayerName()
					+ ") : " + ai.getScore() + " points\n");
		}
	}
}
