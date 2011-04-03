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
			while (true) {
				if (processStdOut.ready()) {
					System.out.println(processStdOut.readLine());
				} else {
					Thread.yield();
				}
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		
		if (logger.isDebugEnabled())
			logger.debug("ProcessReader ended");
	}
}
