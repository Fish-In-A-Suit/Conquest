package models;

import math.Vector3f;

/**
 * This class represents a light entity
 * @author Aljoša
 *
 */
public class Light {
	private Vector3f position;
	private Vector3f colour;
	
	public Light(Vector3f position, Vector3f colour) {
		this.position = position;
		this.colour = colour;
	}
	
	public Vector3f getPosition() {
		return position;
	}
	
	public void setPosition(float x, float y, float z) {
		position = new Vector3f(x, y, z);
	}
	
	public Vector3f getColour() {
		return colour;
	}
	
	public void setColour(float x, float y, float z) {
		colour = new Vector3f(x, y, z);
	}
}
