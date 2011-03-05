
package ui.gui;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import sim.Scheduler;

public class TimeController implements ChangeListener {
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	private JSlider slider;
	private Scheduler scheduler;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	public TimeController(JSlider slider, Scheduler scheduler) {
		this.slider = slider;
		this.scheduler = scheduler;
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	@Override
	public void stateChanged(ChangeEvent event) {
		scheduler.setSpeed(Math.exp((double) slider.getValue()
				/ ControlPanel.PRECISION));
	}
}
