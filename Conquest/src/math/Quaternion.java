package math;

/**
 * A quaternion represents a rotation in 3D. It is very easy to interpolate between two quaternion rotations
 * (in comparison to Euler rotations), and they can be converted to/extracted from matrices easily. When there is a
 * need to represent a rotation, it's best to use a quaternion, but when there's a need to apply the rotation, you first need
 * to convert it back to a matrix.
 * @author Aljoša
 *
 */
public class Quaternion {
	private float x, y, z, w;
	
	/**
	 * Creates a quaternion and normalizes it
	 * @param x
	 * @param y
	 * @param z
	 * @param w
	 */
	public Quaternion(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
		normalize();
	}
	
	/**
	 * Normalizes the quaternion
	 */
	public void normalize() {
		float mag = (float) Math.sqrt(w*w + x*x + y*y + z*z);
		x /= mag;
		y /= mag;
		z /= mag;
		w /= mag;
	}
	
	/**
	 * Converts the quaternion to a 4x4 matrix representing a rotation. 
	 * 
	 * More detailed explanation here:
	 * http://www.euclideanspace.com/maths/geometry/rotations/conversions/quaternionToMatrix/
	 * 
	 * @return The rotation matrix which represents the exact same rotation as this quaternion
	 */
	//need to check if matrix order is correct!
	public Matrix4f toRotationMatrix() {
		Matrix4f mat = new Matrix4f();
		float xy = x*y;
		float xz= x * z;
		float xw = x * w;
		float yz = y * z;
		float yw = y * w;
		float zw = z * w;
		float xSquared = x * x;
		float ySquared = y * y;
		float zSquared = z * z;
		
		mat.m00 = 1 - 2*(ySquared+zSquared);
		mat.m01 = 2 * (xy - zw);
		mat.m02 = 2 * (xz + yw);
		mat.m03 = 0;
		mat.m10 = 2 * (xy + zw);
		mat.m11 = 1 - 2 * (xSquared + zSquared);
		mat.m12 = 2 * (yz - xw);
		mat.m13 = 0;
		mat.m20 = 2 * (xz - yw);
		mat.m21 = 2 * (yz + xw);
		mat.m22 = 1 - 2 * (xSquared + ySquared);
		mat.m23 = 0;
		mat.m30 = 0;
		mat.m31 = 0;
		mat.m32 = 0;
		mat.m33 = 1;
		return mat;
	}
	
	public static Quaternion fromMatrix(Matrix4f mat) {
		float w, x, y, z;
		float diagonal = mat.m00 + mat.m11 + mat.m22;
		
		if (diagonal > 0) {
			float w4 = (float)(Math.sqrt(diagonal + 1.0f)*2f);
			w = w4/4f;
			x = (mat.m21 - mat.m12) / w4;
			y = (mat.m02 - mat.m20) / w4;
			z = (mat.m10 - mat.m01) / w4;
		} else if ((mat.m00 > mat.m11) && (mat.m00 > mat.m22)) {
			float x4 = (float)(Math.sqrt(1f + mat.m00 - mat.m11 - mat.m22) * 2f);
			w = (mat.m21 - mat.m12) / x4;
			x = x4/4f;
			y = (mat.m01 + mat.m10) / x4;
			z = (mat.m02 + mat.m20) / x4;
		} else if(mat.m11 > mat.m22) {
			float y4 = (float)(Math.sqrt(1f + mat.m11 - mat.m00 - mat.m22) * 2f);
			w = (mat.m02 - mat.m20) / y4;
			x = (mat.m01 + mat.m10) / y4;
			y = y4/4f;
			z = (mat.m12 + mat.m21) /y4;
		} else {
			float z4 = (float)(Math.sqrt(1f + mat.m11 - mat.m00 - mat.m22) * 2f);
			w = (mat.m10 - mat.m01) / z4;
			x = (mat.m02 + mat.m20) / z4;
			y = (mat.m12 + mat.m21) / z4;
			z = z4 / 4f;
		}
		return new Quaternion(x, y, z, w);
	}
	
	/**
	 * Interpolates between two quaternion rotations and returns the resulting
	 * quaternion rotation. The interpolation method here is "nlerp", or
	 * "normalized-lerp". Another mnethod that could be used is "slerp", and you
	 * can see a comparison of the methods here:
	 * https://keithmaggio.wordpress.com/2011/02/15/math-magician-lerp-slerp-and-nlerp/
	 * 
	 * and here:
	 * http://number-none.com/product/Understanding%20Slerp,%20Then%20Not%20Using%20It/
	 * 
	 * @param a
	 * @param b
	 * @param blend
	 *            - a value between 0 and 1 indicating how far to interpolate
	 *            between the two quaternions.
	 * @return The resulting interpolated rotation in quaternion format.
	 */
	public static Quaternion interpolate(Quaternion a, Quaternion b, float blend) {
		Quaternion result = new Quaternion(0, 0, 0, 1);
		float dot = a.w * b.w + a.x * b.x + a.y * b.y + a.z * b.z;
		float blendI = 1f - blend;
		if (dot < 0) {
			result.w = blendI * a.w + blend * -b.w;
			result.x = blendI * a.x + blend * -b.x;
			result.y = blendI * a.y + blend * -b.y;
			result.z = blendI * a.z + blend * -b.z;
		} else {
			result.w = blendI * a.w + blend * b.w;
			result.x = blendI * a.x + blend * b.x;
			result.y = blendI * a.y + blend * b.y;
			result.z = blendI * a.z + blend * b.z;
		}
		result.normalize();
		return result;
	}

}
