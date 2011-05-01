
package view.v2d;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import main.CowException;
import data.ConfigLoader;
import data.EntityLoader;
import view.v2d.SpriteMaker.entityCenter;

public class SceneGraph {
	// -------------------------------------------------------------------------
	// Constants
	// -------------------------------------------------------------------------
	
	private static final String CONFIG_FILE = "entities.ini";
	
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	private Map<Integer, SpriteMaker> makers;
	private Map<Integer, Entity> entities;
	private ConfigLoader entitiesLoader;
	private SpriteLoader spriteLoader;
	private SceneRenderer renderer;
	private String gameName;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	public SceneGraph(SceneRenderer renderer, String gameName) {
		this.renderer = renderer;
		this.gameName = gameName;
		this.makers = new HashMap<Integer, SpriteMaker>();
		this.entities = new HashMap<Integer, Entity>();
		this.spriteLoader = new SpriteLoader(renderer, gameName);
		
		try {
			this.entitiesLoader =
				new ConfigLoader("games/" + gameName + "/entities/"
					+ CONFIG_FILE);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	public void loadDefinitions(String gameName) throws CowException {
		// TODO Remove
	}
	
	public void createEntity(int definitionId, int id) {
		// Get sprite maker
		SpriteMaker spriteMaker = makers.get(definitionId);
		
		// Create sprite maker
		if (spriteMaker == null) {
			String entityName;
			
			// Load entity name from its id
			try {
				entityName =
					entitiesLoader.getValue(String.valueOf(definitionId));
			} catch (IOException e) {
				throw new CowException("Can't load entity #" + definitionId
					+ ": " + e.getMessage());
			}
			
			// Load entity loader from the entity name
			try {
				EntityLoader entityLoader =
					new EntityLoader("games/" + gameName + "/entities/"
						+ entityName + ".ini");
				
				// Get entity type
				String type = entityLoader.getValue("type").toLowerCase();
				
				// Sprite
				if (type.equals("sprite")) {
					spriteMaker =
						new SpriteMaker(spriteLoader, renderer, entityName,
							entityLoader.getValue("sprite"),
							(float) entityLoader.getDoubleValue("width", 0),
							(float) entityLoader.getDoubleValue("height", 0),
							entityLoader.getIntValue("depth"), entityLoader
								.getEntityCenterValue("center",
									entityCenter.CENTER));
					makers.put(definitionId, spriteMaker);
				}
				// Unknown type
				else {
					throw new CowException("Unknown entity type '" + type
						+ "' for entity #" + id + " (" + entityName + ")");
				}
			} catch (IOException e) {
				throw new CowException("Can't load entity '" + entityName
					+ "': " + e.getMessage());
			}
		}
		
		// Create entity
		entities.put(id, spriteMaker.createEntity(id));
	}
	
	public Entity getEntity(int id) {
		return entities.get(id);
	}
}
