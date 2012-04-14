
package ui.gui;

import info.monitorenter.gui.chart.Chart2D;
import info.monitorenter.gui.chart.ITrace2D;
import info.monitorenter.gui.chart.traces.Trace2DSimple;
import info.monitorenter.gui.chart.views.ChartPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Collection;
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
	
	private JPanel scoreArea;
	private Chart2D chart;
	private AiScorePanel[] scorePanels;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	public ScorePanel() {
		super(new BorderLayout());
		
		this.chart = new Chart2D();
		
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(0, 160));
		chartPanel.setMinimumSize(new Dimension(0, 80));
		
		add(chartPanel, BorderLayout.CENTER);
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	public void initIas(Collection<Ai> ais) {
		int i = 0;
		
		this.scoreArea = new JPanel(new GridLayout(ais.size(), 1));
		this.scorePanels = new AiScorePanel[ais.size()];
		
		add(scoreArea, BorderLayout.SOUTH);
		
		for (Ai ai : ais) {
			ITrace2D trace = new Trace2DSimple("");
			AiScorePanel scorePanel = new AiScorePanel(ai, trace);
			
			chart.addTrace(trace);
			scoreArea.add(scorePanel);
			scorePanels[i++] = scorePanel;
		}
		
		updateScore(0);
	}
	
	public void updateScore(long nbFrames) {
		for (AiScorePanel scorePanel : scorePanels) {
			scorePanel.updateScore(nbFrames);
		}
	}
}
