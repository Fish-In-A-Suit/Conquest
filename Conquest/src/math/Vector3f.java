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

}
