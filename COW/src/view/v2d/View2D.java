
package view.v2d;

import java.awt.Component;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Collection;
import com.ai.Ai;
import com.jme.renderer.ColorRGBA;
import com.jme.system.DisplaySystem;
import com.jme.system.lwjgl.LWJGLSystemProvider;
import com.jmex.awt.lwjgl.LWJGLAWTCanvasConstructor;
import com.jmex.awt.lwjgl.LWJGLCanvas;
import view.KeyboardController;
import view.MouseController;
import view.View;

public class View2D extends View {
	// -------------------------------------------------------------------------
	// Constants
	// -------------------------------------------------------------------------
	
	private final static int FRAME_RATE = 60;
	
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	private LWJGLCanvas canvas;
	private SceneGraph scene;
	private SceneRenderer renderer;
	private String gameName;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	public View2D(String gameName, KeyboardController keyBoardController,
			MouseController mouseController) {
		super(keyBoardController);
		this.gameName = gameName;
		
		// Create display
		DisplaySystem display =
				DisplaySystem
						.getDisplaySystem(LWJGLSystemProvider.LWJGL_SYSTEM_IDENTIFIER);
		display.registerCanvasConstructor("AWT",
				LWJGLAWTCanvasConstructor.class);
		
		// Create canvas (automatic resolution adjustment)
		canvas = (LWJGLCanvas) display.createCanvas(1, 1);
		canvas.setTargetRate(FRAME_RATE);
		
		// Bind keyboard and mouse controllers
		canvas.addKeyListener(keyBoardController);
		canvas.addMouseListener(mouseController);
		canvas.addMouseWheelListener(mouseController);
		mouseController.setCanvas(canvas);
		
		// Create and bind renderer
		renderer = new SceneRenderer(keyboardController, mouseController);
		canvas.setImplementor(renderer);
		scene = new SceneGraph(renderer, gameName);
		
		// Bind resize listener
		canvas.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent ce) {
				doResize();
			}
		});
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	@Override
	public void initGame(Collection<Ai> ais) {
		super.initGame(ais);
		
		// Load entities definitions
		scene.loadDefinitions(gameName);
	}
	
	@Override
	public Component getComponent() {
		return canvas;
	}
	
	@Override
	public boolean isReady() {
		return renderer.isReady();
	}
	
	@Override
	public void keyReleased(int key) {
		// Do nothing
	}
	
	// -------------------------------------------------------------------------
	// Protected methods
	// -------------------------------------------------------------------------
	
	protected void doResize() {
		renderer.resizeCanvas(canvas.getWidth(), canvas.getHeight());
	}
	
	protected void displayGrid(double x0, double y0, double x1, double y1,
			double xSpacing, double ySpacing, int color) {
		renderer.displayGrid((float) x0, (float) y0, (float) x1, (float) y1,
				(float) xSpacing, (float) ySpacing, ColorRGBA.darkGray);
	}
	
	protected void createEntity(int definitionId, int id) {
		scene.createEntity(definitionId, id);
	}
	
	protected void deleteEntity(int id) {
		scene.getEntity(id).delete();
	}
	
	protected void moveEntity(int id, double dx, double dy) {
		// TODO: Interpolation
		scene.getEntity(id).move(dx, dy, false);
	}
	
	protected void rotateEntity(int id, double angle) {
		// TODO: Interpolation
		scene.getEntity(id).rotate((float) angle, false);
	}
}
