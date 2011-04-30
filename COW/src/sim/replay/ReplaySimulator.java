/**
 * Replay Simulator - This class replays a match already played from a replay
 * file.
 */

package sim.replay;

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
import com.Variant.VariantType;
import com.ai.Ai;
import com.remote.CompressedDataInputStream;

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
	private CompressedDataInputStream in;
	
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
		super(scheduler, gameName, new String[0]);
		
		// Open file
		File fd = new File("games/" + gameName + "/replays/" + replayName);
		in = new CompressedDataInputStream(new FileInputStream(fd));
		
		// Load AIs
		int nbAis = in.readUnsignedVarint();
		for (int i = 0; i < nbAis; i++) {
			short aiId = (short) in.readUnsignedVarint();
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
				int function = in.readUnsignedVarint();
				ApiCall call;
				
				switch (function) {
				case View.SET_FRAME:
					setFrame();
					break;
				
				case View.UPDATE_SCORE:
					for (Ai ai : getAis()) {
						ai.setScore(in.readVarint());
					}
					updateScore();
					break;
				
				case View.PRINT_TEXT:
					call = new ApiCall((short) function, 1);
					call.add(new Variant(in.readUTF()));
					callViewApi(call);
					break;
				
				case View.DISPLAY_GRID:
					call = new ApiCall((short) function, 7);
					call.add(in.readVariantValue(VariantType.INT));
					call.add(in.readVariantValue(VariantType.INT));
					call.add(in.readVariantValue(VariantType.INT));
					call.add(in.readVariantValue(VariantType.INT));
					call.add(in.readVariantValue(VariantType.INT));
					call.add(in.readVariantValue(VariantType.INT));
					call.add(in.readVariantValue(VariantType.INT));
					callViewApi(call);
					break;
				
				case View.CREATE_ENTITY:
					call = new ApiCall((short) function, 2);
					call.add(in.readVariantValue(VariantType.INT));
					call.add(in.readVariantValue(VariantType.INT));
					callViewApi(call);
					break;
				
				case View.DELETE_ENTITY:
					call = new ApiCall((short) function, 1);
					call.add(in.readVariantValue(VariantType.INT));
					callViewApi(call);
					break;
				
				case View.MOVE_ENTITY:
					call = new ApiCall((short) function, 3);
					call.add(in.readVariantValue(VariantType.INT));
					call.add(in.readVariantValue(VariantType.INT));
					call.add(in.readVariantValue(VariantType.INT));
					callViewApi(call);
					break;
				
				case View.ROTATE_ENTITY:
					call = new ApiCall((short) function, 2);
					call.add(in.readVariantValue(VariantType.INT));
					call.add(in.readVariantValue(VariantType.INT));
					callViewApi(call);
					break;
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
	public Variant callGameFunction(ApiCall call, Ai ai) {
		// Do nothing
		return null;
	}
}
