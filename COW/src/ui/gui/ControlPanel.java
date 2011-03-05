
package ui.gui;

import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import main.CowSimulator;
import sim.Scheduler;
import ui.gui.ControlAction.ButtonType;
import view.KeyboardListener;

public class ControlPanel extends JPanel implements KeyboardListener {
	// -------------------------------------------------------------------------
	// Constants
	// -------------------------------------------------------------------------
	
	private static final long serialVersionUID = 1L;
	public static final int PRECISION = 100;
	
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	private Scheduler scheduler;
	private JButton pause;
	private JButton nextStep;
	private JButton stop;
	private JSlider timeSlider;
	private boolean enabled;
	private boolean paused;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	public ControlPanel(Scheduler scheduler) {
		this.scheduler = scheduler;
		this.enabled = false;
		this.paused = true;
		
		// Create pause button
		pause = new JButton(new ImageIcon("images/play-pause.png"));
		pause.addActionListener(new ControlAction(this, ButtonType.Pause));
		
		// Create next step button
		nextStep = new JButton(new ImageIcon("images/next.png"));
		nextStep.addActionListener(new ControlAction(this, ButtonType.NextStep));
		
		// Create stop button
		stop = new JButton(new ImageIcon("images/stop.png"));
		stop.addActionListener(new ControlAction(this, ButtonType.Stop));
		
		// Create time slider
		timeSlider =
				new JSlider((int) Math.floor(PRECISION
						* Math.log(CowSimulator.MIN_SPEED)),
						(int) Math.ceil(PRECISION
								* Math.log(CowSimulator.UNLIMITED_SPEED)),
						(int) Math.round(PRECISION
								* Math.log(scheduler.getSpeed())));
		timeSlider.addChangeListener(new TimeController(timeSlider, scheduler));
		
		// Create control panel
		add(pause);
		add(nextStep);
		add(stop);
		add(timeSlider);
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		
		pause.setEnabled(enabled);
		nextStep.setEnabled(enabled);
		stop.setEnabled(enabled);
		timeSlider.setEnabled(enabled);
	}
	
	public synchronized void playPause() {
		if (paused) {
			paused = false;
			scheduler.play();
		} else {
			paused = true;
			scheduler.pause();
		}
	}
	
	public synchronized void nextStep() {
		scheduler.nextStep();
	}
	
	public synchronized void stopGame() {
		scheduler.stopGame();
	}
	
	@Override
	public void keyReleased(int key) {
		if (enabled) {
			// Play / Pause
			if (key == KeyEvent.VK_SPACE) {
				playPause();
			}
			// Next step
			else if (key == KeyEvent.VK_ENTER) {
				nextStep();
			}
			// Stop
			else if (key == KeyEvent.VK_ESCAPE) {
				stopGame();
			}
		}
	}
}
