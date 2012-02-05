
package ui.gui;

import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import com.ai.Ai;

public class ScoreLabel extends JPanel {
	// -------------------------------------------------------------------------
	// Constants
	// -------------------------------------------------------------------------
	
	private static final long serialVersionUID = 1L;
	
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	private Ai ai;
	private JLabel scoreLabel;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	public ScoreLabel(Ai ai) {
		super(new FlowLayout(FlowLayout.LEFT));
		
		this.ai = ai;
		this.scoreLabel = new JLabel();
		
		JLabel colorLabel = new JLabel("      ");
		colorLabel.setBackground(ai.getColor());
		colorLabel.setOpaque(true);
		
		JLabel nameLabel = new JLabel(ai.getName() + " (" + ai.getPlayerName() + ")");
		
		add(colorLabel);
		add(nameLabel);
		add(scoreLabel);
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	public void updateScore() {
		scoreLabel.setText(ai.getScore() + " points");
	}
}
