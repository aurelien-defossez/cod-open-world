
package view.v2d;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import com.jme.image.Texture;
import com.jme.scene.state.BlendState;
import com.jme.scene.state.RenderState;
import com.jme.util.TextureManager;

public class SpriteLoader {
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	private ClassLoader spriteLoader;
	private SceneRenderer renderer;
	private BlendState transparencyState;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------`
	
	public SpriteLoader(SceneRenderer renderer, String gameName) {
		this.renderer = renderer;
		
		// Create class loader
		try {
			File spriteDir =
					new File("games/" + gameName + "/entities/sprites/");
			URL[] urls = { spriteDir.toURI().toURL() };
			spriteLoader = new URLClassLoader(urls);
		} catch (MalformedURLException e) {
			// Should not happen
			throw new RuntimeException(e.getMessage());
		}
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	public Texture loadSprite(String name) {
		URL textureUrl = spriteLoader.getResource(name);
		return TextureManager.loadTexture(textureUrl,
				Texture.MinificationFilter.BilinearNearestMipMap,
				Texture.MagnificationFilter.Bilinear);
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
