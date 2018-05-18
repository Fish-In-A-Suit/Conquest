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
	
	public GameEntity(float[] vPositions, int[] indices, float[] colours) {
		System.out.println("[GameEntity.GameEntity]: Initializing mesh instance field based on parameters... ");
		mesh = new Mesh(vPositions, indices, colours);
		
		System.out.println("[GameEntity.GameEntity]: Initializing position, scale and rotation instance fields... ");
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
		rotation.x += x * 0.05f;
		rotation.y += y * 0.05f;
		rotation.z += z * 0.05f;
	}
	
	public void increaseScale(float scale) {
		this.scale += scale * 0.01f;
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
	
	public void increaseAllFun(float tx, float ty, float tz, float scale, float rx, float rz, float ry) {
		//position.x += tx * 0.05f;
	    //position.y += ty * 0.05f;
		//position.z += tz * 0.05f;
		
		if (this.scale < 2.0f) {
			this.scale += scale * 0.02f;
		} else {
			this.scale = -this.scale;
		}
		
		rotation.x += rx * 0.05f;
		rotation.y += ry * 0.05f;
		rotation.z += rz * 0.05f;
	}
}