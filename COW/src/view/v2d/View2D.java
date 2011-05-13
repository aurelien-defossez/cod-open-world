
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
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	public View2D(String gameName, KeyboardController keyBoardController,
		MouseController mouseController) {
		super(keyBoardController);
		
		// Create display
		DisplaySystem display =
			DisplaySystem
				.getDisplaySystem(LWJGLSystemProvider.LWJGL_SYSTEM_IDENTIFIER);
		display.registerCanvasConstructor("AWT",
			LWJGLAWTCanvasConstructor.class);
		
		// Create canvas (1;1 is for automatic resolution adjustment)
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
	
	@Override
	protected void printText(String text) {
		// TODO: Print text
	}
	
	@Override
	protected void displayGrid(int x0, int y0, int x1, int y1, int xSpacing,
		int ySpacing, int color, boolean temporary) {
		scene.displayGrid(x0, y0, x1, y1, xSpacing, ySpacing,
			getColor(color), temporary);
	}
	
	@Override
	protected void drawLine(int x0, int y0, int x1, int y1, int color,
		boolean temporary) {
		scene.drawLine(x0, y0, x1, y1, getColor(color), temporary);
	}
	
	@Override
	protected void drawCircle(int x, int y, int radius, int samples, int color,
		boolean temporary) {
		scene.drawCircle(x, y, radius, samples, getColor(color), temporary);
	}
	
	@Override
	protected void deleteTemporaryShapes() {
		scene.deleteTemporaryShapes();
	}
	
	@Override
	protected void createEntity(int definitionId, int id) {
		scene.createEntity(definitionId, id);
	}
	
	@Override
	protected void deleteEntity(int id) {
		scene.getEntity(id).delete();
	}
	
	@Override
	protected void moveEntity(int id, int dx, int dy) {
		scene.getEntity(id).move(dx, dy, false);
	}
	
	@Override
	protected void rotateEntity(int id, int angle) {
		scene.getEntity(id).rotate(angle, false);
	}
	
	// -------------------------------------------------------------------------
	// Private methods
	// -------------------------------------------------------------------------
	
	ColorRGBA getColor(int rawColor) {
		ColorRGBA color = new ColorRGBA();
		color.fromIntARGB(rawColor);
		return color;
	}
}
