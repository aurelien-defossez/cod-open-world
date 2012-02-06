/**
 * Replay Writer - This class saves the current game in a generic replay file.
 */

package sim.replay;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import main.CowException;
import org.apache.log4j.Logger;
import view.View;
import com.ApiCall;
import com.GameListener;
import com.Variant;
import com.ai.Ai;
import com.remote.CompressedDataOutputStream;

public class ReplayWriter implements GameListener {
	// -------------------------------------------------------------------------
	// Class attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The log4j logger.
	 */
	private Logger logger = Logger.getLogger(ReplayWriter.class);
	
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The data writer for the replay file.
	 */
	private CompressedDataOutputStream out;
	
	/**
	 * The AIs for this game.
	 */
	private Collection<Ai> ais;
	
	/**
	 * True if the file is opened.
	 */
	private boolean opened;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	/**
	 * Constructs the replay writer, creates the file and the data writer.
	 * 
	 * @param gameName the game name.
	 * @param replayName the replay file name.
	 * @throws CowException if a problem occurs while creating the file or its
	 *             writer.
	 */
	public ReplayWriter(String gameName, String replayName) {
		try {
			// Create file
			File fd = new File("games/" + gameName + "/replays/" + replayName);
			
			// Open data writer
			out = new CompressedDataOutputStream(new FileOutputStream(fd));
			opened = true;
		} catch (FileNotFoundException e) {
			throw new CowException("Cannot create replay file (\"" + replayName
				+ "\").", e);
		}
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	/**
	 * Initializes the replay file, by writing the AI information.
	 * 
	 * @param ais the AIs.
	 */
	@Override
	public void initGame(Collection<Ai> ais) {
		this.ais = ais;
		
		if (opened) {
			try {
				// Write AIs information
				out.writeUnsignedVarint(ais.size());
				
				for (Ai ai : ais) {
					out.writeUnsignedVarint(ai.getId());
					out.writeUTF(ai.getPlayerName());
					out.writeUTF(ai.getName());
					out.writeUnsignedVarint(ai.getColor().getRGB());
				}
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
				stopWriting();
			}
		} else {
			throw new CowException("File not opened yet");
		}
	}
	
	/**
	 * Stops the replay writer from writing.
	 */
	@Override
	public void endGame() {
		stopWriting();
	}
	
	/**
	 * Writes the new colors in the file.
	 */
	@Override
	public void updateColors() {
		if (opened) {
			try {
				// Write colors
				out.writeUnsignedVarint(View.UPDATE_COLORS);
				
				for (Ai ai : ais) {
					out.writeUnsignedVarint(ai.getColor().getRGB());
				}
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
				stopWriting();
			}
		}
	}
	
	/**
	 * Writes the scores in the file.
	 */
	@Override
	public void updateScore(long nbFrames) {
		if (opened) {
			try {
				// Write scores
				out.writeUnsignedVarint(View.UPDATE_SCORE);
				
				for (Ai ai : ais) {
					out.writeVarint(ai.getScore());
				}
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
				stopWriting();
			}
		}
	}
	
	/**
	 * Writes the frame in the file.
	 */
	@Override
	public void setFrame() {
		if (opened) {
			try {
				// Write frame
				out.writeUnsignedVarint(View.SET_FRAME);
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
				stopWriting();
			}
		}
	}
	
	/**
	 * Serializes the view API call in the file.
	 * 
	 * @param call the view API call.
	 */
	@Override
	public void callViewFunction(ApiCall call) {
		if (opened) {
			try {
				// Write function id
				out.writeUnsignedVarint(call.getFunctionId());
				
				// Write parameters
				for (Variant parameter : call.getParameters()) {
					out.writeVariantValue(parameter);
				}
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
				stopWriting();
			}
		}
	}
	
	// -------------------------------------------------------------------------
	// Private methods
	// -------------------------------------------------------------------------
	
	/**
	 * Stops the replay writer from writing.
	 */
	private void stopWriting() {
		logger.info("Replay writer stopped.");
		
		try {
			opened = false;
			out.close();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
}
