/**
 * Config Loader - This class loads and reads configuration files (INI format).
 */

package data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The configuration file descriptor.
	 */
	private String file;
	
	/**
	 * The INI properties reader.
	 */
	protected Properties config;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	/**
	 * Loads a configuration file.
	 * 
	 * @param file the configuration file.
	 * @throws FileNotFoundException if the file cannot be found.
	 * @throws IOException if a problem occurs while reading the file.
	 */
	public ConfigLoader(String file) throws FileNotFoundException, IOException {
		this.file = file;
		this.config = new Properties();
		
		config.load(new FileInputStream(file));
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	/**
	 * Returns the value of the desired attribute as a string.
	 * 
	 * @param name the attribute name.
	 * @throws IOException if the attribute doesn't exist.
	 */
	public final String getValue(String name) throws IOException {
		String value = config.getProperty(name);
		
		if (value != null) {
			return value;
		} else {
			throw new IOException("Missing value \"" + name + "\" "
				+ "in file " + file);
		}
	}
	
	/**
	 * Returns the value of the desired attribute as an integer.
	 * 
	 * @param name the attribute name.
	 * @throws IOException if the attribute doesn't exist.
	 * @throws NumberFormatException if the value cannot be cast into an
	 *             integer.
	 */
	public final int getIntValue(String name) throws IOException,
		NumberFormatException {
		return Integer.parseInt(getValue(name));
	}
	
	/**
	 * Returns the value of the desired attribute as a double.
	 * 
	 * @param name the attribute name.
	 * @throws IOException if the attribute doesn't exist.
	 * @throws NumberFormatException if the value cannot be cast into a double.
	 */
	public final double getDoubleValue(String name) throws IOException,
		NumberFormatException {
		return Double.parseDouble(getValue(name));
	}
	
	/**
	 * Returns the value of the desired attribute as a string.
	 * 
	 * @param name the attribute name.
	 * @param defaultValue the default value if the attribute doesn't exist.
	 */
	public final String getValue(String name, String defaultValue)
		throws IOException {
		if (config.containsKey(name)) {
			return getValue(name);
		} else {
			return defaultValue;
		}
	}
	
	/**
	 * Returns the value of the desired attribute as an integer.
	 * 
	 * @param name the attribute name.
	 * @param defaultValue the default value if the attribute doesn't exist.
	 * @throws NumberFormatException if the value cannot be cast into an
	 *             integer.
	 */
	public final int getIntValue(String name, int defaultValue)
		throws IOException, NumberFormatException {
		if (config.containsKey(name)) {
			return Integer.parseInt(getValue(name));
		} else {
			return defaultValue;
		}
	}
	
	/**
	 * Returns the value of the desired attribute as a double.
	 * 
	 * @param name the attribute name.
	 * @param defaultValue the default value if the attribute doesn't exist.
	 * @throws NumberFormatException if the value cannot be cast into a double.
	 */
	public final double getDoubleValue(String name, double defaultValue)
		throws IOException, NumberFormatException {
		if (config.containsKey(name)) {
			return Double.parseDouble(getValue(name));
		} else {
			return defaultValue;
		}
	}
}
