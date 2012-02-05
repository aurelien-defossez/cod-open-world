
package ui.gui;

import info.monitorenter.gui.chart.Chart2D;
import info.monitorenter.gui.chart.ITrace2D;
import info.monitorenter.gui.chart.traces.Trace2DSimple;
import info.monitorenter.gui.chart.views.ChartPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import com.ai.Ai;

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
	private Chart2D chart;
	private Map<Ai, ITrace2D> traces;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	public ScorePanel() {
		super(new BorderLayout());
		
		this.scoreArea = new JTextArea();
		this.chart = new Chart2D();
		this.traces = new HashMap<Ai, ITrace2D>();
		
		chart.enablePointHighlighting(true);
		
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(0, 200));
		
		add(scoreArea, BorderLayout.SOUTH);
		add(chartPanel, BorderLayout.CENTER);
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	public void initIas(Collection<Ai> ais) {
		this.ais = ais;
		
		for (Ai ai : ais) {
			ITrace2D trace = new Trace2DSimple("");
			trace.setColor(ai.getColor());
			
			chart.addTrace(trace);
			traces.put(ai, trace);
		}
		
		updateScore(0);
	}
	
	public void updateScore(long nbFrames) {
		scoreArea.setText("");
		
		for (Ai ai : ais) {
			scoreArea.append(" " + ai.getName() + " (" + ai.getPlayerName()
				+ ") : " + ai.getScore() + " points\n");
			
			traces.get(ai).addPoint(nbFrames, ai.getScore());
		}
	}
}
