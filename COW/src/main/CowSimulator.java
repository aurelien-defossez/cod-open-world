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
import sim.GameSimulator;
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
	public static final double MAX_SPEED = 1000;
	
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
	public static final String UNLIMITED_LETTER = "u";
	
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
		// Parameters
		String option = "";
		Vector<String> ais = new Vector<String>();
		Vector<String> replays = new Vector<String>();
		String[] parameters = new String[0];
		String gameName = null;
		String loadReplayName = null;
		String resultFile = null;
		double gameSpeed = DEFAULT_SPEED;
		boolean displayHelp = false;
		boolean autoStart = false;
		boolean testMode = false;
		boolean useView = true;
		boolean quiet = false;
		
		// Stop JME flooding (log4j)
		System.setProperty("java.util.logging.config.file", "");
		
		// Parse arguments
		try {
			for (int i = 0; i < args.length;) {
				option = args[i++].toLowerCase();
				
				// -a, --ai: Add an AI
				if (option.equals("-a") || option.equals("--ai")) {
					String aiName = args[i++];
					ais.add(aiName);
				}
				
				// -f, --resultFile
				else if (option.equals("-f") || option.equals("--resultFile")) {
					resultFile = args[i++];
				}

				// -g, --game: Determine game
				else if (option.equals("-g") || option.equals("--game")) {
					if (gameName == null) {
						gameName = args[i++];
					} else {
						throw new CowException(
							"The game can't be specified twice");
					}
				}

				// -h, --help: Display help
				else if (option.equals("-h") || option.equals("--help")) {
					displayHelp = true;
				}

				// -n, --noview: Don't show a view and play the game blindly
				else if (option.equals("-n") || option.equals("--noview")) {
					useView = false;
					autoStart = true;
				}

				// -p, --load: Load and play a replay
				else if (option.equals("-p") || option.equals("--load")) {
					loadReplayName = args[i++];
				}

				// -q, --quiet: Plays quietly and don't log anything
				else if (option.equals("-q") || option.equals("--quiet")) {
					quiet = true;
				}

				// -r, --save: Save replay
				else if (option.equals("-r") || option.equals("--save")) {
					String replayName = args[i++];
					replays.add(replayName);
				}

				// -s, --speed: Set speed
				else if (option.equals("-s") || option.equals("--speed")) {
					String speed = args[i++].toLowerCase();
					
					// Check unlimited speed
					if (speed.equals(UNLIMITED_NAME)
						|| speed.equals(UNLIMITED_LETTER)) {
						gameSpeed = UNLIMITED_SPEED;
					}
					// Get integer speed
					else {
						try {
							gameSpeed = Double.parseDouble(speed);
							gameSpeed =
								Math.max(MIN_SPEED, Math.min(gameSpeed,
									MAX_SPEED));
						} catch (NumberFormatException e) {
							throw new CowException("Speed must be an float "
								+ ", \"" + UNLIMITED_NAME + "\" " + "or \""
								+ UNLIMITED_LETTER + "\".");
						}
					}
				}

				// -t, --test: Test mode
				else if (option.equals("-t") || option.equals("--test")) {
					testMode = true;
				}

				// -x, --auto: Auto-start
				else if (option.equals("-x") || option.equals("--auto")) {
					autoStart = true;
				}

				// -z, --parameters: Set game parameters
				else if (option.equals("-z") || option.equals("--parameters")) {
					parameters = new String[args.length - i];
					
					for (int j = 0; j < parameters.length; j++) {
						parameters[j] = args[i++];
					}
				}

				// Unknown option
				else {
					throw new CowException("Unknown option: " + option + ".");
				}
			}
		}
		// Specific exception
		catch (CowException e) {
			System.out.println(e.getMessage());
			return;
		}
		// Not enough arguments
		catch (IndexOutOfBoundsException e) {
			System.out.println("Argument expected after " + option);
		}
		
		// Initialize logger
		if (quiet) {
			PropertyConfigurator.configure("log4j-quiet-config.txt");
		} else {
			PropertyConfigurator.configure("log4j-config.txt");
		}
		
		// Display help
		if (displayHelp) {
			try {
				// Open file
				String line;
				BufferedReader reader =
					new BufferedReader(new FileReader(new File(HELP_FILE)));
				
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
			try {
				// Game not specified
				if (gameName == null) {
					throw new CowException("The game must be specified.");
				}
				
				// Create scheduler
				Scheduler scheduler = new Scheduler(gameSpeed);
				GameSimulator simulator;
				
				// Set game
				if (loadReplayName == null) {
					simulator =
						scheduler.loadGame(gameName, parameters, testMode, resultFile);
				} else {
					simulator = scheduler.loadReplay(gameName, loadReplayName);
				}
				
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
				Gui gui = null;
				if (useView) {
					gui = new Gui(scheduler, ViewType.V2D);
					simulator.addGameListener(gui);
					simulator.addGameListener(gui.getView());
				}
				
				// Wait for view to be ready
				if (useView) {
					while (!gui.getView().isReady()) {
						Thread.sleep(1);
					}
				}
				
				// Initialize game
				simulator.initGame();
				
				// Auto start
				if (autoStart) {
					// Auto-start
					scheduler.play();
				}
			}
			// Cow exception
			catch (CowException e) {
				logger.fatal(e.getMessage(), e);
			}
			// Unexpected exception
			catch (Exception e) {
				logger.fatal(e.getMessage(), e);
				// TODO: handle not catched exception
			}
		}
	}
}
