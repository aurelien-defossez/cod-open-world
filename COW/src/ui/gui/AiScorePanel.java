
package ui.gui;

import info.monitorenter.gui.chart.ITrace2D;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import com.ai.Ai;

public class AiScorePanel extends JPanel {
	// -------------------------------------------------------------------------
	// Constants
	// -------------------------------------------------------------------------
	
	private static final long serialVersionUID = 1L;
	
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	private Ai ai;
	private JLabel scoreLabel;
	private ITrace2D trace;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	public AiScorePanel(Ai ai, ITrace2D trace) {
		super(new FlowLayout(FlowLayout.LEFT));
		
		this.ai = ai;
		this.trace = trace;
		this.scoreLabel = new JLabel();
		
		JLabel colorLabel = new JLabel("      ");
		colorLabel.setBackground(ai.getColor());
		colorLabel.setOpaque(true);
		
		trace.setColor(ai.getColor());
		
		JLabel nameLabel = new JLabel(ai.getName() + " (" + ai.getPlayerName() + ")");
		
		add(colorLabel);
		add(nameLabel);
		add(scoreLabel);
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	public void updateScore(long nbFrames) {
		scoreLabel.setText(ai.getScore() + " points");
		trace.addPoint(nbFrames, ai.getScore());
	}
}
