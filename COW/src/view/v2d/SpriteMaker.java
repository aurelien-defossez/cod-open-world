
package view.v2d;

import org.apache.log4j.Logger;
import com.jme.bounding.BoundingBox;
import com.jme.image.Texture;
import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jme.scene.shape.Quad;
import com.jme.scene.state.TextureState;

public class SpriteMaker {
	// -------------------------------------------------------------------------
	// Class attributes
	// -------------------------------------------------------------------------
	
	private static Logger logger = Logger.getLogger(SpriteMaker.class);
	
	// -------------------------------------------------------------------------
	// Enumeration
	// -------------------------------------------------------------------------
	
	public enum entityCenter {
		BOTTOM_LEFT, BOTTOM_MIDDLE, BOTTOM_RIGHT, TOP_LEFT, TOP_MIDDLE,
		TOP_RIGHT, LEFT_MIDDLE, RIGHT_MIDDLE, CENTER
	};
	
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	private Node node;
	private String name;
	private float width;
	private float height;
	private int depth;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	public SpriteMaker(SpriteLoader spriteLoader, SceneRenderer renderer,
		String name, String spritePath, float width, float height, int depth,
		entityCenter center) {
		this.name = name;
		this.width = width;
		this.height = height;
		this.depth = depth;
		
		if (logger.isDebugEnabled())
			logger.debug("Load entity(" + name + ", " + spritePath + ", "
				+ width + ", " + height + ", " + depth + ", " + center + ")");
		
		// Load texture
		Texture texture = spriteLoader.loadSprite(spritePath);
		TextureState textureState = renderer.getRenderer().createTextureState();
		textureState.setTexture(texture);
		
		// Create node
		node = new Node("Maker (" + name + ")");
		
		// Set bounding box
		node.setModelBound(new BoundingBox());
		
		// Apply texture & transparency
		node.setRenderState(textureState);
		node.setRenderState(spriteLoader.getTransparencyState());
		
		// Move entities according to their center
		switch (center) {
		case BOTTOM_LEFT:
			node.setLocalTranslation(new Vector3f(width / 2, height / 2, 0));
			break;
		case BOTTOM_MIDDLE:
			node.setLocalTranslation(new Vector3f(0, height / 2, 0));
			break;
		case BOTTOM_RIGHT:
			node.setLocalTranslation(new Vector3f(-width / 2, height / 2, 0));
			break;
		case TOP_LEFT:
			node.setLocalTranslation(new Vector3f(width / 2, -height / 2, 0));
			break;
		case TOP_MIDDLE:
			node.setLocalTranslation(new Vector3f(0, -height / 2, 0));
			break;
		case TOP_RIGHT:
			node.setLocalTranslation(new Vector3f(-width / 2, -height / 2, 0));
			break;
		case LEFT_MIDDLE:
			node.setLocalTranslation(new Vector3f(width / 2, 0, 0));
			break;
		case RIGHT_MIDDLE:
			node.setLocalTranslation(new Vector3f(-width / 2, 0, 0));
			break;
		case CENTER:
			// Do nothing, quads are already center-aligned by default
			break;
		}
		
		// Attach node to renderer
		renderer.attachChild(node);
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	public Entity createEntity(int id) {
		// Create sprite
		Quad sprite =
			new Quad("Entity #" + id + " (" + name + ")", width, height);
		
		// Set Z order
		sprite.setZOrder(depth);
		
		// Add to node
		node.attachChild(sprite);
		
		// Update texture
		sprite.updateRenderState();
		
		return new Entity(sprite, id);
	}
	
	public void removeEntity(Quad sprite) {
		node.detachChild(sprite);
	}
}
