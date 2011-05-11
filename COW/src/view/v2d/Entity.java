
package view.v2d;

import com.jme.math.FastMath;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.scene.shape.Quad;

public class Entity {
	// -------------------------------------------------------------------------
	// Constants
	// -------------------------------------------------------------------------
	
	private static final Vector3f zRotation = new Vector3f(0, 0, 1);
	private static final float PI_180 = FastMath.PI / 180;
	
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	private Quad sprite;
	private Vector3f position;
	private Vector3f destination;
	private Quaternion rotation;
	private int rotationAngle;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	public Entity(Quad sprite, int id) {
		this.sprite = sprite;
		this.position = new Vector3f(0, 0, 0);
		this.destination = new Vector3f(0, 0, 0);
		this.rotation = new Quaternion();
		this.rotationAngle = 0;
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	public Vector3f getPosition() {
		return position;
	}
	
	public float getRotationAngle() {
		return rotationAngle;
	}
	
	public void move(int dx, int dy, boolean interpolate) {
		destination.addLocal(dx, dy, 0);
		
		if (!interpolate) {
			position.addLocal(dx, dy, 0);
		}
		
		sprite.setLocalTranslation(position);
	}
	
	public void delete() {
		sprite.removeFromParent();
	}
	
	public void rotate(int angle, boolean interpolate) {
		rotationAngle += angle;
		rotation.fromAngleAxis(rotationAngle * PI_180, zRotation);
		sprite.setLocalRotation(rotation);
	}
}
