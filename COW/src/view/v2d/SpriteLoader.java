
package view.v2d;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import org.apache.log4j.Logger;
import com.jme.image.Texture;
import com.jme.scene.state.BlendState;
import com.jme.scene.state.RenderState;
import com.jme.util.TextureManager;

public class SpriteLoader {
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	private static final String SPRITE_FOLDER_NAME = "sprites";
	private static final String NOT_FOUND_IMAGE_PATH = "resources/images/";
	private static final String NOT_FOUND_IMAGE_NAME = "notfound.png";
	
	// -------------------------------------------------------------------------
	// Class attributes
	// -------------------------------------------------------------------------
	
	private static Logger logger = Logger.getLogger(SpriteLoader.class);
	
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	private URLClassLoader spriteLoader;
	private SceneRenderer renderer;
	private BlendState transparencyState;
	private Texture notFoundTexture;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------`
	
	public SpriteLoader(SceneRenderer renderer, String gameName) {
		this.renderer = renderer;
		
		// Create class loader
		try {
			File spriteDir =
				new File("games/" + gameName + "/entities/"
					+ SPRITE_FOLDER_NAME + "/");
			URL[] urls = { spriteDir.toURI().toURL() };
			spriteLoader = new URLClassLoader(urls);
		} catch (MalformedURLException e) {
			// Should not happen
			throw new RuntimeException(e.getMessage());
		}
		
		// Load not found texture
		try {
			File imagesDir = new File(NOT_FOUND_IMAGE_PATH);
			URL[] urls = { imagesDir.toURI().toURL() };
			notFoundTexture =
				loadSprite(NOT_FOUND_IMAGE_NAME, new URLClassLoader(urls));
		} catch (MalformedURLException e) {
			// Should not happen
			throw new RuntimeException(e.getMessage());
		}
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	public Texture loadSprite(String name, URLClassLoader classLoader) {
		URL textureUrl = classLoader.getResource(name);
		
		if (textureUrl != null) {
			return TextureManager.loadTexture(textureUrl,
				Texture.MinificationFilter.BilinearNearestMipMap,
				Texture.MagnificationFilter.Bilinear);
		} else {
			logger.error("Resource '" + name + "' not found in "
				+ classLoader.getURLs()[0]);
			
			return notFoundTexture;
		}
	}
	
	public Texture loadSprite(String name) {
		return loadSprite(name, spriteLoader);
	}
	
	public RenderState getTransparencyState() {
		if (transparencyState == null) {
			// Create transparency state
			transparencyState = renderer.getRenderer().createBlendState();
			transparencyState.setBlendEnabled(true);
			transparencyState
				.setSourceFunctionAlpha(BlendState.SourceFunction.SourceAlpha);
			transparencyState
				.setDestinationFunctionAlpha(BlendState.DestinationFunction.OneMinusSourceAlpha);
			transparencyState.setBlendEquation(BlendState.BlendEquation.Add);
			transparencyState.setTestEnabled(false);
			transparencyState.setEnabled(true);
		}
		
		return transparencyState;
	}
}
