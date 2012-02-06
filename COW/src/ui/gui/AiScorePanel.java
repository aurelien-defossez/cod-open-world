
package ui.gui;

import info.monitorenter.gui.chart.ITrace2D;
import java.awt.FlowLayout;
import java.io.IOException;
import java.util.NavigableMap;
import java.util.TreeMap;
import javax.swing.JLabel;
import javax.swing.JPanel;
import com.ai.Ai;

public class AiScorePanel extends JPanel {
	// -------------------------------------------------------------------------
	// Constants
	// -------------------------------------------------------------------------
	
	private static final long serialVersionUID = 1L;
	private static final int MAX_DISPLAYED_POINTS = 1000;
	private static final int SAMPLE_RATE = 2;
	
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	private Ai ai;
	private JLabel scoreLabel;
	private ITrace2D trace;
	private NavigableMap<Long, Integer> points;
	private int displayedPointsRatio;
	private int framesPerDisplayedPoints;
	private long nextDisplayedPointFrame;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	public AiScorePanel(Ai ai, ITrace2D trace) {
		super(new FlowLayout(FlowLayout.LEFT));
		
		this.ai = ai;
		this.trace = trace;
		this.scoreLabel = new JLabel();
		this.points = new TreeMap<Long, Integer>();
		this.displayedPointsRatio = 1;
		this.framesPerDisplayedPoints = 0;
		this.nextDisplayedPointFrame = 0;
		
		// Initialize trace
		points.put(0L, 0);
		
		// Create colored box
		JLabel colorLabel = new JLabel("      ");
		colorLabel.setBackground(ai.getColor());
		colorLabel.setOpaque(true);
		
		// Customize trace
		trace.setColor(ai.getColor());
		
		// Create name label
		JLabel nameLabel = new JLabel(ai.getName() + " (" + ai.getPlayerName() + ")");
		
		// Add components
		add(colorLabel);
		add(nameLabel);
		add(scoreLabel);
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	public void updateScore(long nbFrames) {
		int score = ai.getScore();
		
		points.put(nbFrames, score);
		scoreLabel.setText(score + " points");
		
		if (nbFrames >= nextDisplayedPointFrame) {
			trace.addPoint(nbFrames, score);
			nextDisplayedPointFrame = nbFrames + framesPerDisplayedPoints;
		} else {
			System.out.println("skip");
		}
		
		// Too many points displayed, sample trace
		if (trace.getSize() > MAX_DISPLAYED_POINTS) {
			displayedPointsRatio *= SAMPLE_RATE;
			framesPerDisplayedPoints = (int) (nbFrames / trace.getSize() * SAMPLE_RATE);
			
			trace.removeAllPoints();
			
			for (long i = 0; i <= nbFrames; i += framesPerDisplayedPoints) {
				trace.addPoint(i, points.floorEntry(i).getValue());
			}
		}
	}
}
