
package view.v2d;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.Circle;
import com.jme.scene.Geometry;
import com.jme.scene.Line;
import com.jme.scene.Spatial;
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
	private Vector<Spatial> temporarySpatials;
	private ConfigLoader entitiesLoader;
	private SpriteLoader spriteLoader;
	private SceneRenderer renderer;
	private String gameName;
	private int shapeCounter;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	public SceneGraph(SceneRenderer renderer, String gameName) {
		this.renderer = renderer;
		this.gameName = gameName;
		this.makers = new HashMap<Integer, SpriteMaker>();
		this.entities = new HashMap<Integer, Entity>();
		this.temporarySpatials = new Vector<Spatial>();
		this.spriteLoader = new SpriteLoader(renderer, gameName);
		this.shapeCounter = 0;
		
		try {
			this.entitiesLoader =
				new ConfigLoader("games/" + gameName + "/entities/"
					+ CONFIG_FILE);
		} catch (FileNotFoundException e) {
			throw new CowException("Can't load game view: config file \"games/"
				+ gameName + "/entities/" + CONFIG_FILE + "\" missing");
		} catch (IOException e) {
			throw new CowException("Can't load game view: A problem occured " +
				"while reading config file.", e);
		}
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	public void displayGrid(int x0, int y0, int x1, int y1, int xSpacing,
		int ySpacing, ColorRGBA color, boolean temporary) {
		ArrayList<Vector3f> lines = new ArrayList<Vector3f>();
		
		// Vertical lines
		for (float x = x0; x <= x1; x += xSpacing) {
			lines.add(new Vector3f(x, y0, 0));
			lines.add(new Vector3f(x, y1, 0));
		}
		
		// Horizontal lines
		for (float y = y0; y <= y1; y += ySpacing) {
			lines.add(new Vector3f(x0, y, 0));
			lines.add(new Vector3f(x1, y, 0));
		}
		
		// Draw lines
		Geometry grid = new Line("Shape #" + (shapeCounter++) + " (Grid)",
				lines.toArray(new Vector3f[] {}), null, null, null);
		grid.setDefaultColor(color);
		grid.setZOrder(1);
		grid.updateRenderState();
		
		// Attach grid
		renderer.attachChild(grid);
		
		// Add to temporaries
		if (temporary) {
			temporarySpatials.add(grid);
		}
	}
	
	public void drawLine(int x0, int y0, int x1, int y1, ColorRGBA color,
		boolean temporary) {
		ArrayList<Vector3f> lines = new ArrayList<Vector3f>();
		
		// Create line
		lines.add(new Vector3f(x0, y0, 0));
		lines.add(new Vector3f(x1, y1, 0));
		
		Geometry line = new Line("Shape #" + (shapeCounter++) + " (Line)",
			lines.toArray(new Vector3f[] {}), null, null, null);
		line.setDefaultColor(color);
		line.setZOrder(1);
		line.updateRenderState();
		
		// Attach line
		renderer.attachChild(line);
		
		// Add to temporaries
		if (temporary) {
			temporarySpatials.add(line);
		}
	}
	
	public void drawCircle(int x, int y, int radius, int samples,
		ColorRGBA color, boolean temporary) {
		Circle circle = new Circle("Shape #" + (shapeCounter++) + " (Circle)",
			samples, radius);
		circle.setDefaultColor(color);
		circle.setZOrder(1);
		circle.updateRenderState();
		circle.translatePoints(x, y, 0);
		
		// Attach line
		renderer.attachChild(circle);
		
		// Add to temporaries
		if (temporary) {
			temporarySpatials.add(circle);
		}
	}
	
	public void deleteTemporaryShapes() {
		// Remove temporaries from the scene
		for (Spatial temporarySpatial : temporarySpatials) {
			temporarySpatial.removeFromParent();
		}
		
		temporarySpatials.clear();
	}
	
	public void createEntity(int definitionId, int id) {
		// Get sprite maker
		SpriteMaker spriteMaker = makers.get(definitionId);
		
		// Create sprite maker
		if (spriteMaker == null) {
			String entityName;
			
			// Load entity name from its id
			try {
				entityName = entitiesLoader.getValue(
					String.valueOf(definitionId));
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
					spriteMaker = new SpriteMaker(spriteLoader, renderer,
						entityName, entityLoader.getValue("sprite"),
						(float) entityLoader.getDoubleValue("width", 0),
						(float) entityLoader.getDoubleValue("height", 0),
						entityLoader.getIntValue("depth"),
						entityLoader.getEntityCenterValue("center",
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
