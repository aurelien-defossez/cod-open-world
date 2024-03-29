/**
 * Process Reader - This class reads and print everything that goes on the
 * standard process output on the current process output.
 */

package debug;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.log4j.Logger;

public class ProcessReader extends Thread {
	// -------------------------------------------------------------------------
	// Class attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The log4j logger.
	 */
	private Logger logger = Logger.getLogger(ProcessReader.class);
	
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The process standard output.
	 */
	private BufferedReader processStdOut;
	
	/**
	 * True while reading to the remote process.
	 */
	private boolean reading;
	
	// -------------------------------------------------------------------------
	// Builder
	// -------------------------------------------------------------------------
	
	/**
	 * Initializes the process reader.
	 * 
	 * @param process the process to listen to.
	 * @param name the thread name for this process reader.
	 */
	public ProcessReader(Process process, String name) {
		super(name);
		reading = true;
		
		processStdOut =
			new BufferedReader(new InputStreamReader(process.getInputStream()));
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	/**
	 * Listens to the process standard output and prints this on the current
	 * standard output.
	 */
	@Override
	public void run() {
		if (logger.isDebugEnabled())
			logger.debug("ProcessReader started");
		
		try {
			while (reading) {
				String line = processStdOut.readLine();
				
				if (line != null) {
					System.out.println(line);
				}
			}
		} catch (IOException e) {
			// Do nothing
		}
		
		if (logger.isDebugEnabled())
			logger.debug("ProcessReader ended");
	}
	
	/**
	 * Stops the process reader.
	 */
	public void stopReading() {
		reading = false;
	}
}
