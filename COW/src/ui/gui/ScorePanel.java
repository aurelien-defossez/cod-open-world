
package ui.gui;

import info.monitorenter.gui.chart.Chart2D;
import info.monitorenter.gui.chart.ITrace2D;
import info.monitorenter.gui.chart.traces.Trace2DSimple;
import info.monitorenter.gui.chart.views.ChartPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;
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
	private JPanel scoreArea;
	private Chart2D chart;
	private Map<Ai, ITrace2D> traces;
	private Map<Ai, ScoreLabel> labels;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	public ScorePanel() {
		super(new BorderLayout());
		
		this.chart = new Chart2D();
		this.traces = new HashMap<Ai, ITrace2D>();
		this.labels = new HashMap<Ai, ScoreLabel>();
		
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(0, 200));
		
		add(chartPanel, BorderLayout.CENTER);
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	public void initIas(Collection<Ai> ais) {
		this.ais = ais;
		this.scoreArea = new JPanel(new GridLayout(ais.size(), 1));
		
		add(scoreArea, BorderLayout.SOUTH);
		
		for (Ai ai : ais) {
			ITrace2D trace = new Trace2DSimple("");
			trace.setColor(ai.getColor());
			
			chart.addTrace(trace);
			traces.put(ai, trace);
			
			ScoreLabel scoreLabel = new ScoreLabel(ai);
			scoreArea.add(scoreLabel);
			labels.put(ai, scoreLabel);
		}
		
		updateScore(0);
	}
	
	public void updateScore(long nbFrames) {
		for (Ai ai : ais) {
			labels.get(ai).updateScore();
			traces.get(ai).addPoint(nbFrames, ai.getScore());
		}
	}
}
