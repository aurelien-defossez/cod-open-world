/**
 * Replay Simulator - This class replays a match already played from a replay
 * file.
 */

package sim.replay;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.log4j.Logger;
import sim.GameSimulator;
import sim.Scheduler;
import view.View;
import com.ApiCall;
import com.Variant;
import com.ai.Ai;

public class ReplaySimulator extends GameSimulator {
	// -------------------------------------------------------------------------
	// Class attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The log4j logger.
	 */
	private Logger logger = Logger.getLogger(ReplaySimulator.class);
	
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The replay file data reader.
	 */
	private DataInputStream in;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	/**
	 * Constructs the replay simulator.
	 * 
	 * @param gameName the game name.
	 * @param replayName the replay file name.
	 * @throws FileNotFoundException if the game or replay does not exist.
	 * @throws IOException if an error occurs while loading the replay.
	 */
	public ReplaySimulator(Scheduler scheduler, String gameName,
			String replayName) throws FileNotFoundException, IOException {
		super(scheduler, gameName);
		
		// Open file
		File fd = new File("games/" + gameName + "/replays/" + replayName);
		in = new DataInputStream(new FileInputStream(fd));
		
		// Read game name
		in.readUTF();
		
		// Load AIs
		short nbAis = in.readShort();
		for (int i = 0; i < nbAis; i++) {
			short aiId = in.readShort();
			in.readUTF(); // Read player name
			String aiName = in.readUTF();
			
			// Add AI
			addAi(new ReplayAi(this, aiId, aiName));
		}
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	@Override
	/**
	 * Adds an AI (not implemented, does nothing).
	 * 
	 * @param aiId the AI id.
	 * @param aiName the AI name.
	 */
	public void addAi(short aiId, String aiName) {
		// Do nothing
	}
	
	@Override
	/**
	 * {@inheritDoc}
	 */
	public void initGame() {
		super.initGame();
	}
	
	@Override
	/**
	 * Ends the game replay.
	 */
	public void endGame() {
		super.endGame();
		
		try {
			// Close data reader
			in.close();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	@Override
	/**
	 * Plays the replay.
	 */
	public void play() {
		try {
			while (in.available() > 0) {
				short function = in.readShort();
				
				switch (function) {
				case View.SET_FRAME:
					setFrame();
					break;
				
				case View.UPDATE_SCORE:
					for (Ai ai : getAis()) {
						ai.setScore(in.readLong());
					}
					updateScore();
					break;
				
				case View.PRINT_TEXT:
					ApiCall call = new ApiCall(function, 1);
					call.add(new Variant(in.readUTF()));
					callViewApi(call);
				}
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	@Override
	/**
	 * Makes a game API call (not implemented, returns null).
	 * 
	 * @param call the API call.
	 * @param ai the AI making the call.
	 * @return the return value of this call.
	 */
	public Variant callGameApi(ApiCall call, Ai ai) {
		// Do nothing
		return null;
	}
}
