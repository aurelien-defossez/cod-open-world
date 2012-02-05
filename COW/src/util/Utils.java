/**
 * Utils - This class is a collection of utils methods.
 */

package util;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import main.BashException;

public class Utils {
	// -------------------------------------------------------------------------
	// Class public methods
	// -------------------------------------------------------------------------
	
	/**
	 * Executes the bash command.
	 * 
	 * @param command the bash command to execute.
	 * @returns the command result.
	 * @throws BashException if an error occur while executing the bash command.
	 */
	public static String executeCommand(String command) throws BashException {
		StringBuffer sb = new StringBuffer();
		
		try {
			Process process = Runtime.getRuntime().exec(command);
			process.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
				process.getInputStream()));
			
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			
			return sb.toString();
		} catch (IOException e) {
			throw new BashException("Unable to execute command '" + command + "' " +
					"(" + e.getMessage() + ")");
		} catch (InterruptedException e) {
			throw new BashException("Unable to execute command '" + command + "' " +
					"(" + e.getMessage() + ")");
		}
	}
	
	/**
	 * Randomizes a color.
	 * 
	 * @param min the minimal value for a component, between 0 and 1.
	 * @param max the maximal value for a component, between 0 and 1.
	 * @return a random color in the given interval.
	 */
	public static Color randomColor(double min, double max) {
		double r = min + Math.random() * (max - min);
		double g = min + Math.random() * (max - min);
		double b = min + Math.random() * (max - min);
		
		return new Color((float) r, (float) g, (float) b);
	}
}
