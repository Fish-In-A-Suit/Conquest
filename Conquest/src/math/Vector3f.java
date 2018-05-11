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

}
