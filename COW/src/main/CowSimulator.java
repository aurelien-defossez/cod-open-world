/**
 * Cow Simulator - This class starts the simulator.
 */

package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import sim.LiveSimulator;
import sim.Scheduler;
import sim.replay.ReplayWriter;
import ui.gui.Gui;
import view.View.ViewType;

public class CowSimulator {
	// -------------------------------------------------------------------------
	// Constants
	// -------------------------------------------------------------------------
	
	/**
	 * The minimal speed, in frames per second.
	 */
	public static final double MIN_SPEED = 1;
	
	/**
	 * The default speed, in frames per second.
	 */
	public static final double DEFAULT_SPEED = 5;
	
	/**
	 * The maximal speed, in frames per second.
	 */
	public static final double MAX_SPEED = 100;
	
	/**
	 * The unlimited speed constant.
	 */
	public static final double UNLIMITED_SPEED = MAX_SPEED + 1;
	
	/**
	 * The unlimited speed complete name, to use with the speed option (-s
	 * unlimited).
	 */
	public static final String UNLIMITED_NAME = "unlimited";
	
	/**
	 * The unlimited speed small name, to use with the speed option (-s u).
	 */
	public static final char UNLIMITED_LETTER = 'u';
	
	/**
	 * Help file name.
	 */
	public static final String HELP_FILE = "help.txt";
	
	// -------------------------------------------------------------------------
	// Class attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The log4j logger.
	 */
	private static Logger logger = Logger.getLogger(CowSimulator.class);
	
	// -------------------------------------------------------------------------
	// Main
	// -------------------------------------------------------------------------
	
	/**
	 * Launches the Cow Simulator.
	 * 
	 * @param args the arguments.
	 */
	public static void main(String[] args) {
		new CowSimulator(args);
	}
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	/**
	 * Launches the Cow Simulator.
	 * 
	 * @param args the arguments.
	 */
	public CowSimulator(String[] args) {
		// Initialize logger
		PropertyConfigurator.configure("log4j-config.txt");
		
		// Parameters
		char arg = ' ';
		Vector<String> ais = new Vector<String>();
		Vector<String> replays = new Vector<String>();
		String gameName = null;
		double gameSpeed = DEFAULT_SPEED;
		ViewType viewType = ViewType.None;
		boolean displayHelp = false;
		boolean autoStart = false;
		boolean testMode = false;
		
		// Parse arguments
		try {
			for (int i = 0; i < args.length;) {
				arg = args[i++].charAt(1);
				
				switch (arg) {
				
				// -a: Add an AI
				case 'a':
					String aiName = args[i++];
					ais.add(aiName);
					
					if (logger.isTraceEnabled())
						logger.trace("Add AI: " + aiName);
					break;
				
				// -g: Determine game
				case 'g':
					if (gameName == null) {
						gameName = args[i++];
						
						if (logger.isTraceEnabled())
							logger.trace("Set game: " + gameName);
					} else {
						throw new CowException(
								"The game can't be specified twice");
					}
					break;
				
				// -h: Display help
				case 'h':
					if (logger.isTraceEnabled())
						logger.trace("Display help");
					
					testMode = true;
					break;
				
				// -r: Save replay
				case 'r':
					String replayName = args[i++];
					replays.add(replayName);
					
					if (logger.isTraceEnabled())
						logger.trace("Save replay: " + replayName);
					break;
				
				// -s: Set speed
				case 's':
					String speed = args[i++].toLowerCase();
					
					// Check unlimited speed
					if (speed.equals(UNLIMITED_NAME)
							|| speed.equals(UNLIMITED_LETTER)) {
						gameSpeed = UNLIMITED_SPEED;
					}
					// Get number speed
					else {
						try {
							gameSpeed = Double.parseDouble(speed);
							gameSpeed =
									Math.max(MIN_SPEED,
											Math.min(gameSpeed, MAX_SPEED));
						} catch (NumberFormatException e) {
							throw new CowException("Speed must be an float "
									+ "or \"" + UNLIMITED_NAME + "\".");
						}
					}
					
					if (logger.isTraceEnabled())
						logger.trace("Set speed: " + gameSpeed);
					break;
				
				// -t: Test mode
				case 't':
					if (logger.isTraceEnabled())
						logger.trace("Test mode");
					
					testMode = true;
					break;
				
				// -v: Set view
				case 'v':
					String strViewType = args[i++].toLowerCase();
					
					// Define view
					if (strViewType.equals("none")) {
						viewType = ViewType.None;
						throw new CowException("Not implemented yet");
					} else if (strViewType.equals("console")) {
						viewType = ViewType.Console;
						throw new CowException("Not implemented yet");
					} else if (strViewType.equals("text")) {
						viewType = ViewType.Text;
					} else if (strViewType.equals("2d")) {
						viewType = ViewType.V2D;
					} else if (strViewType.equals("3d")) {
						viewType = ViewType.V3D;
						throw new CowException("Not implemented yet");
					} else {
						throw new CowException("View type '" + strViewType
								+ "' invalid.");
					}
					
					if (logger.isTraceEnabled())
						logger.trace("Set view: " + viewType);
					break;
				
				// -x: Auto-start
				case 'x':
					autoStart = true;
					
					if (logger.isTraceEnabled())
						logger.trace("Auto-start enabled");
					break;
				
				// Unknown option
				default:
					throw new CowException("Unknown option: " + arg + ".");
				}
			}
			
			// Display help
			if (displayHelp) {
				try {
					// Open file
					String line;
					BufferedReader reader =
							new BufferedReader(new FileReader(new File(
									HELP_FILE)));
					
					// Print file
					while ((line = reader.readLine()) != null) {
						System.out.println(line);
					}
				}
				// Help file not found
				catch (FileNotFoundException e) {
					logger.fatal("Help file not found: can't display help");
				}
				// IO Exception
				catch (IOException e) {
					logger.fatal("An IO error occurred while reading the "
							+ "help file (" + e.getMessage() + ").");
				}
			}
			// Launch game
			else {
				// Game not specified
				if (gameName == null) {
					throw new CowException("The game must be specified.");
				}
				
				// Create scheduler
				Scheduler scheduler = new Scheduler(gameSpeed);
				
				// Set game
				LiveSimulator simulator =
						scheduler.loadGame(gameName, testMode);
				
				// Add AIs
				for (short i = 0; i < ais.size(); i++) {
					simulator.addAi(i, ais.get(i));
				}
				
				// Add replay writers
				for (String replay : replays) {
					simulator
							.addGameListener(new ReplayWriter(gameName, replay));
				}
				
				// Create GUI
				Gui gui = new Gui(scheduler, viewType);
				simulator.addGameListener(gui);
				simulator.addGameListener(gui.getView());
				
				// Auto start
				if (autoStart) {
					// Wait for the view to be ready
					while (!gui.getView().isReady()) {
						Thread.sleep(1);
					}
					
					// Auto-start
					scheduler.play();
				}
			}
		}
		// Not enough arguments
		catch (IndexOutOfBoundsException e) {
			logger.fatal("Argument expected after -" + arg);
		}
		// Cow exception
		catch (CowException e) {
			logger.fatal(e.getMessage());
		}
		// Unexpected exception
		catch (Exception e) {
			logger.fatal(e.getMessage(), e);
		}
	}
}
