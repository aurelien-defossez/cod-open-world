/**
 * Replay Writer - This class saves the current game in a generic replay file.
 */

package sim.replay;

import java.io.DataOutputStream;
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
	private DataOutputStream out;
	
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
			out = new DataOutputStream(new FileOutputStream(fd));
			opened = true;
			
			// Write game name
			out.writeUTF(gameName);
		} catch (FileNotFoundException e) {
			throw new CowException("Cannot create replay file (\"" + replayName
				+ "\").", e);
		} catch (IOException e) {
			throw new CowException("Cannot create replay writer.", e);
		}
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	@Override
	/**
	 * Initializes the replay file, by writing the AI information.
	 * 
	 * @param ais the AIs.
	 */
	public void initGame(Collection<Ai> ais) {
		this.ais = ais;
		
		if (opened) {
			// Insert game name
			try {
				// Write AIs information
				out.writeShort(ais.size());
				
				for (Ai ai : ais) {
					out.writeShort(ai.getId());
					out.writeUTF(ai.getPlayerName());
					out.writeUTF(ai.getName());
				}
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
				stopWriting();
			}
		}
	}
	
	@Override
	/**
	 * Stops the replay writer from writing.
	 */
	public void endGame() {
		stopWriting();
	}
	
	@Override
	/**
	 * Writes the scores in the file.
	 */
	public void updateScore() {
		if (opened) {
			try {
				// Write scores
				out.writeShort(View.UPDATE_SCORE);
				
				for (Ai ai : ais) {
					out.writeLong(ai.getScore());
				}
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
				stopWriting();
			}
		}
	}
	
	@Override
	/**
	 * Writes the frame in the file.
	 */
	public void setFrame() {
		if (opened) {
			try {
				// Write frame
				out.writeShort(View.SET_FRAME);
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
				stopWriting();
			}
		}
	}
	
	@Override
	/**
	 * Serializes the view API call in the file.
	 * 
	 * @param call the view API call.
	 */
	public void callViewFunction(ApiCall call) {
		if (opened) {
			try {
				// Write function id
				out.writeShort(call.getFunctionId());
				
				// Write parameters
				for (Variant parameter : call.getParameters()) {
					switch (parameter.getType()) {
					case BOOL:
						out.writeBoolean((Boolean) parameter.getValue());
						break;
					
					case INT:
						out.writeInt((Integer) parameter.getValue());
						break;
					
					case DOUBLE:
						out.writeDouble((Double) parameter.getValue());
						break;
					
					case STRING:
						out.writeUTF((String) parameter.getValue());
						break;
					
					case BOOL_MATRIX1:
						boolean[] booleanArray =
							(boolean[]) parameter.getValue();
						out.writeInt(booleanArray.length);
						
						for (boolean value : booleanArray) {
							out.writeBoolean(value);
						}
						break;
					
					case INT_MATRIX1:
						int[] intArray = (int[]) parameter.getValue();
						out.writeInt(intArray.length);
						
						for (Integer value : intArray) {
							out.writeInt(value);
						}
						break;
					
					case DOUBLE_MATRIX1:
						double[] doubleArray = (double[]) parameter.getValue();
						out.writeInt(doubleArray.length);
						
						for (Double value : doubleArray) {
							out.writeDouble(value);
						}
						break;
					
					case STRING_MATRIX1:
						String[] stringArray = (String[]) parameter.getValue();
						out.writeInt(stringArray.length);
						
						for (String value : stringArray) {
							out.writeUTF(value);
						}
						break;
					}
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
