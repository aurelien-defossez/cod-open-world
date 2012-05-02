
package ui.gui;

import info.monitorenter.gui.chart.ITrace2D;
import java.awt.FlowLayout;
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
	private JLabel colorLabel;
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
		colorLabel = new JLabel("      ");
		colorLabel.setOpaque(true);
		
		// Create name label
		JLabel nameLabel = new JLabel(ai.getName() + " (" + ai.getPlayerName() + ")");
		
		// Set default colors
		updateColor();
		
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
		}
		
		// Too many points displayed, sample trace
		if (trace.getSize() > MAX_DISPLAYED_POINTS) {
			NavigableMap<Long, Integer> newPoints = new TreeMap<Long, Integer>();
			newPoints.put(0L, 0);
			
			displayedPointsRatio *= SAMPLE_RATE;
			framesPerDisplayedPoints = (int) Math.round(displayedPointsRatio * nbFrames / trace.getSize());
			
			trace.removeAllPoints();
			
			for (long i = 0; i <= nbFrames; i += framesPerDisplayedPoints) {
				Integer point = points.floorEntry(i).getValue();
				
				trace.addPoint(i, point);
				newPoints.put(i, point);
			}
			
			points.clear();
			points = newPoints;
		}
	}
	
	public void updateColor() {
		trace.setColor(ai.getColor());
		colorLabel.setBackground(ai.getColor());
	}
}
