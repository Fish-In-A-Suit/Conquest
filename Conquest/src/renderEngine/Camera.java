package renderEngine;

import math.Vector3f;

/**
 * This class represents a camera object. It's informatin (ie position and rotation) are used
 * by Renderer.render(...) to acquire a viewing matrix.
 * @author Aljoša
 *
 */
public class Camera {
	private Vector3f position;
	private Vector3f rotation;

	public Camera() {
		position = new Vector3f(0, 0, 0);
		rotation = new Vector3f(0, 0, 0);
	}
	
	public void move(float x, float y, float z) {
		position.x += x * 0.05;
		position.y += y * 0.05;
		position.z += z * 0.05;
	}
	
	public Vector3f getPosition() {
		return position;
	}
	
	public Vector3f getRotation() {
		return rotation;
	}
	
	public void increasePosition(Vector3f position) {
		this.position.add(position);
	}
	
	public void increasePosition(float x, float y, float z) {
		this.position.x += x * 0.05f;
		this.position.y += y * 0.05f;
		this.position.z += z * 0.05f;
	}
	
	public void increaseRotation(double x, double y) {
		this.rotation.x = (float) (y * -0.005d);
		this.rotation.y = (float) (-x * 0.005d);
	}
	
	public void resetRotation() {
		rotation.x = 0;
		rotation.y = 0;
		rotation.z = 0;
	}
}
