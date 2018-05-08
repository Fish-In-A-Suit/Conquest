package models;

import math.Vector3f;
import models.Mesh;

/**
 * A class which has references for storing model position, rotation, scale and state
 * @author Aljoša
 *
 */
public class GameEntity {
	
	private final Mesh mesh;
	private Vector3f position;
	private float scale;
	private Vector3f rotation;
	
	public GameEntity(Mesh mesh) {
		this.mesh = mesh;
		position = new Vector3f(0, 0, 0);
		scale = 1;
		rotation = new Vector3f(0, 0, 0);
	}
	
	public Vector3f getPosition() {
		return position;
	}
	
	/**
	 * This method moves an entity around in the world
	 * @param x
	 * @param y
	 * @param z
	 */
	public void updatePosition(float x, float y, float z) {
		position.x += x * 0.05f;
	    position.y += y * 0.05f;
		position.z += z * 0.05f;
	}
	
	public void increaseRotation(float x, float y, float z) {
		rotation.x += x;
		rotation.y += y;
		rotation.z += z;
	}
 
	public float getScale() {
		return scale;
	}
	
	public void setScale(float scale) {
		this.scale = scale;
	}
	
	public Vector3f getRotation() {
		return rotation;
	}
	
	public void setRotation(float x, float y, float z) {
		rotation.x = x;
		rotation.y = y;
		rotation.z = z;
	}
	
	public Mesh getMesh() {
		return mesh;
	}
}
