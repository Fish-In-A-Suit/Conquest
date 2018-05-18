package math;

public class Vector4f {
	public float x, y, z, w;
	
	public Vector4f() {
		
	}

	/**
	 * Extends a Vector3f to Vector4f
	 * @param vec3f The Vector3f to be extended to Vector4f
	 */
	public Vector4f(Vector3f vec3f) {
		x = vec3f.x;
		y= vec3f.y;
		z = vec3f.z;
		w = 1;
	}
	
	/**
	 * Creates a new Vector4f given floats x, y and z
	 * @param x
	 * @param y
	 * @param z
	 */
	public Vector4f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this. z = z;
		w = 1;
	}
	
	public String toString() {
		return "Vector4f: " + x + " " + y + " " + z + " " + w;
	}
	
	/**
	 * Extends a Vector3f to Vector4f
	 * @param vec3f
	 * @return
	 */
	public static Vector4f createVec4f(Vector3f vec3f) {
		return new Vector4f(vec3f);
	}

}
