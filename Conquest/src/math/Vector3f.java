package math;

public class Vector3f {
	public float x;
	public float y;
	public float z;
	
	public Vector3f() {
	}
	
	public Vector3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3f negate() {
		float newX = -this.x;
		float newY = -this.y;
		float newZ = -this.z;
		
		return new Vector3f(newX, newY, newZ);
	}
	
	public Vector3f add(Vector3f vec) {
		x += vec.x;
		y += vec.y;
		z += vec.z;
		return this;
	}
	
	public String toString() {
		return "Vector3f: " + x + " " + y + " " + z;
	}
	
	/**
	 * Displays a vector of any data values cast to integers - it can be read in a hum-friendly style
	 * @return
	 */
	public String displayFriendly() {
		return "x: " + (int)x + " y: " + (int)y + " z: " + (int)z;
	}
	
	public float length() {
		return (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y,  2) + Math.pow(z, 2));
	}

}
