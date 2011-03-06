/**
 * Entity Loader - This class loads an entity for the generic view.
 */

package data;

import java.io.FileNotFoundException;
import java.io.IOException;
import view.v2d.SpriteMaker.entityCenter;

public class EntityLoader extends ConfigLoader {
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	/**
	 * Loads the entity configuration file.
	 * 
	 * @param file the configuration file.
	 * @throws FileNotFoundException if the file cannot be found.
	 * @throws IOException if a problem occurs while reading the file.
	 */
	public EntityLoader(String file) throws FileNotFoundException, IOException {
		super(file);
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	/**
	 * Returns the value of the desired attribute as an "entity center" type.
	 * 
	 * @param name the attribute name.
	 * @param defaultValue the default
	 * @throws IOException if the attribute doesn't exist.
	 */
	public entityCenter getEntityCenterValue(String name,
			entityCenter defaultValue) throws IOException {
		String value = getValue(name).toLowerCase();
		
		if (value.equals("bottom-left")) {
			return entityCenter.BOTTOM_LEFT;
		} else if (value.equals("bottom-middle")) {
			return entityCenter.BOTTOM_MIDDLE;
		} else if (value.equals("bottom-right")) {
			return entityCenter.BOTTOM_RIGHT;
		} else if (value.equals("top-left")) {
			return entityCenter.TOP_LEFT;
		} else if (value.equals("top-middle")) {
			return entityCenter.TOP_MIDDLE;
		} else if (value.equals("top-right")) {
			return entityCenter.TOP_RIGHT;
		} else if (value.equals("left-middle")) {
			return entityCenter.LEFT_MIDDLE;
		} else if (value.equals("right-middle")) {
			return entityCenter.RIGHT_MIDDLE;
		} else if (value.equals("center")) {
			return entityCenter.CENTER;
		} else {
			return defaultValue;
		}
	}
}
