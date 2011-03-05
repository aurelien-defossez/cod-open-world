
package view.v2d;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import view.ButtonListener;
import view.KeyboardController;
import view.MouseController;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.renderer.Renderer;
import com.jme.scene.Geometry;
import com.jme.scene.Line;
import com.jme.scene.Spatial;
import com.jme.system.canvas.SimpleCanvasImpl;

public class SceneRenderer extends SimpleCanvasImpl implements ButtonListener {
	// -------------------------------------------------------------------------
	// Constants
	// -------------------------------------------------------------------------
	
	private final int CAMERA_SPEED = 400;
	private final Vector3f CAMERA_INITIAL_POSITION = new Vector3f(0, 0, 0);
	private final float CAMERA_INITIAL_SCALE = 1;
	
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	private KeyboardController keyboardController;
	private MouseController mouseController;
	private Vector3f cameraPosition;
	private Vector3f beforeDragCameraPosition;
	private Point beforeDragMousePosition;
	private float cameraScale;
	private boolean ready;
	private int lastWidth;
	private int lastHeight;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	public SceneRenderer(KeyboardController keyboardController,
			MouseController mouseController) {
		super(1, 1);
		
		this.keyboardController = keyboardController;
		this.mouseController = mouseController;
		this.ready = false;
		
		mouseController.addListener(this);
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	@Override
	public void simpleSetup() {
		// Set orthogonal mode
		rootNode.setRenderQueueMode(Renderer.QUEUE_ORTHO);
		
		// Initialize camera values
		cameraPosition = CAMERA_INITIAL_POSITION;
		cameraScale = CAMERA_INITIAL_SCALE;
		beforeDragCameraPosition = new Vector3f();
		
		// Move camera accordingly
		rootNode.setLocalTranslation(cameraPosition);
		rootNode.setLocalScale(cameraScale);
		
		// Initialize scene dimensions
		lastWidth = renderer.getWidth();
		lastHeight = renderer.getHeight();
		
		// The renderer is ready
		ready = true;
	}
	
	@Override
	public void simpleUpdate() {
		// Keyboard: Move camera up
		if (keyboardController.isPressed(KeyEvent.VK_UP)) {
			moveView(0, -CAMERA_SPEED * getTimePerFrame());
		}
		// Keyboard: Move camera down
		else if (keyboardController.isPressed(KeyEvent.VK_DOWN)) {
			moveView(0, CAMERA_SPEED * getTimePerFrame());
		}
		
		// Keyboard: Move camera left
		if (keyboardController.isPressed(KeyEvent.VK_LEFT)) {
			moveView(CAMERA_SPEED * getTimePerFrame(), 0);
		}
		// Keyboard: Move camera right
		else if (keyboardController.isPressed(KeyEvent.VK_RIGHT)) {
			moveView(-CAMERA_SPEED * getTimePerFrame(), 0);
		}
		
		// Keyboard: Zoom out
		if (keyboardController.isPressed(KeyEvent.VK_MINUS)) {
			zoomView(1 - CAMERA_SPEED * getTimePerFrame() / 250);
		}
		// Keyboard: Zoom in
		else if (keyboardController.isPressed(KeyEvent.VK_EQUALS)) {
			zoomView(1 + CAMERA_SPEED * getTimePerFrame() / 250);
		}
		
		// Mouse: move camera
		if (mouseController.isPressed(MouseEvent.BUTTON1)) {
			Point currentMousePosition = mouseController.getAbsolutePosition();
			cameraPosition.set(beforeDragCameraPosition);
			moveView(currentMousePosition.x - beforeDragMousePosition.x,
					beforeDragMousePosition.y - currentMousePosition.y);
		}
	}
	
	public boolean isReady() {
		return ready;
	}
	
	public void attachChild(Spatial node) {
		rootNode.attachChild(node);
	}
	
	public void displayGrid(float x0, float y0, float x1, float y1,
			float xSpacing, float ySpacing, ColorRGBA color) {
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
		Geometry grid =
				new Line("regularLine", lines.toArray(new Vector3f[] {}), null,
						null, null);
		grid.setDefaultColor(color);
		grid.setZOrder(5);
		grid.updateRenderState();
		
		// Attach grid
		attachChild(grid);
	}
	
	@Override
	public void buttonPressed(int button) {
		// Start dragging
		if (button == MouseEvent.BUTTON1) {
			beforeDragMousePosition = mouseController.getAbsolutePosition();
			beforeDragCameraPosition.set(cameraPosition);
		}
	}
	
	@Override
	public void buttonReleased(int button) {
		// Do nothing
	}
	
	@Override
	public void scroll(int value) {
		if (value < 0) {
			zoomView(1 + CAMERA_SPEED * getTimePerFrame() / 50);
		} else {
			zoomView(1 - CAMERA_SPEED * getTimePerFrame() / 50);
		}
	}
	
	@Override
	public void resizeCanvas(int width, int height) {
		// Resize canvas
		super.resizeCanvas(width, height);
		
		// Move view
		if (ready) {
			moveView((width - lastWidth) / 2, (height - lastHeight) / 2);
		}
		
		// Save scene dimensions
		lastWidth = width;
		lastHeight = height;
	}
	
	// -------------------------------------------------------------------------
	// Private methods
	// -------------------------------------------------------------------------
	
	private void moveView(float dx, float dy) {
		// Translate root node
		cameraPosition.addLocal(dx, dy, 0);
		
		rootNode.setLocalTranslation(cameraPosition);
	}
	
	private void zoomView(double dz) {
		// Change scale value
		cameraScale *= dz;
		
		// Point to zoom on
		Point mousePosition = mouseController.getLocalPosition();
		Vector3f centre;
		// Zoom on the cursor
		if (mousePosition != null) {
			centre =
					new Vector3f(mousePosition.x, renderer.getHeight()
							- mousePosition.y, 0);
		}
		// Zoom on the centre of the scene
		else {
			centre =
					new Vector3f(renderer.getWidth() / 2,
							renderer.getHeight() / 2, 0);
		}
		
		// Move camera accordingly
		cameraPosition.set(
				(float) (dz * cameraPosition.x + centre.x * (1 - dz)),
				(float) (dz * cameraPosition.y + centre.y * (1 - dz)), 0);
		
		// Move and scale root node
		rootNode.setLocalScale((float) cameraScale);
		rootNode.setLocalTranslation(cameraPosition);
	}
}
