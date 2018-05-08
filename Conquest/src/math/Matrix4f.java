package math;

public class Matrix4f {
	float m00, m01, m02, m03;
	float m10, m11, m12, m13;
	float m20, m21, m22, m23;
	float m30, m31, m32, m33;
	
	//for debugging purposes
	float d_scale;
	float d_left;
	float d_right;
	float d_top;
	float d_bottom;
	
	/**
	 * This creates an identity matrix
	 */
	public Matrix4f() {
		m00 = 1.0f; m01 = 0.0f; m02 = 0.0f; m03 = 0.0f;
		m10 = 0.0f;	m11 = 1.0f; m12 = 0.0f; m13 = 0.0f;
		m20 = 0.0f; m21 = 0.0f; m22 = 1.0f; m23 = 0.0f;
		m30 = 0.0f; m31 = 0.0f; m32 = 0.0f; m33 = 1.0f;
	}
	
	/**
	 * Transforms a Matrix4f to array. This method is especially useful when a matrix needs to be stored in a FloatBuffer
	 * (for instance when calling the glUniform4fv method)
	 * @return
	 */
	public float[] mat4fToArray()  {
		float[] matrixArray = {
				m00, m01, m02, m03,
				m10, m11, m12, m13,
				m20, m21, m22, m23,
				m30, m31, m32, m33
		};
		
		return matrixArray;
	}
	
	/**
	 * Multiplies a matrix on which this method is called by a specified vector. This method can be used for
	 * various types of matrices (ex. translation, rotation, scale). It returns an extended and transformed
	 * representation of the input Vector3f object
	 * @param vec3f The vector to multiply with a matrix object
	 * @return Vector4f
	 */
	public Vector4f multiplyByVector(Vector3f vec3f) {
		Vector4f extendedVec = Vector4f.createVec4f(vec3f);
		
		extendedVec.x = m00*extendedVec.x + m01*extendedVec.y + m02*extendedVec.z + m03*extendedVec.w;
		extendedVec.y = m10*extendedVec.x + m11*extendedVec.y + m12*extendedVec.z + m13*extendedVec.w;
		extendedVec.z = m20*extendedVec.x + m21*extendedVec.y + m22*extendedVec.z + m23*extendedVec.w;
		extendedVec.w = m30*extendedVec.x + m31*extendedVec.y + m32*extendedVec.z + m33*extendedVec.w;
		
		return extendedVec;
	}
	
	public Matrix4f multiplyByMatrix(Matrix4f mat) {
		this.m00 += mat.m00; this.m01 += mat.m10; this.m02 += mat.m20; this.m03 += mat.m30;
		this.m10 += mat.m01; this.m11 += mat.m11; this.m12 += mat.m21; this.m13 += mat.m31;
		this.m20 += mat.m02; this.m21 += mat.m12; this.m22 += mat.m22; this.m23 += mat.m32;
		this.m30 += mat.m03; this.m31 += mat.m13; this.m32 += mat.m23; this.m33 += mat.m33;
		return this;
	}
	
	/**
	 * Returns a String representing the matrix on which this method is called
	 * @return String
	 */
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append(m00).append(' ').append(m01).append(' ').append(m02).append(' ').append(m03).append('\n');
		result.append(m10).append(' ').append(m11).append(' ').append(m12).append(' ').append(m13).append('\n');
		result.append(m20).append(' ').append(m21).append(' ').append(m22).append(' ').append(m23).append('\n');
		result.append(m30).append(' ').append(m31).append(' ').append(m32).append(' ').append(m33).append('\n');
		return result.toString();
	}

	/**
	 * Creates a perspective projection matrix out of an existing matrix
	 * @param fovy The vertical field of view. Normal values: 60, 90, 120
	 * @param aspectRatio 
	 * @param zNear The near clipping plane
	 * @param zFar The far clipping plane
	 */
	public Matrix4f perspective(float fovy, float aspectRatio, float zNear, float zFar) {
		float scale = (float) (Math.tan(fovy * 0.5) * zNear);
		d_scale = scale;
		float top = scale;
		d_top = top;
		float right = top * aspectRatio;
		d_right = right;
		float bottom = -top;
		d_bottom = bottom;
		float left = bottom * aspectRatio;
		d_left = left;
		
		this.m00 = 2*zNear / (right - left);
		this.m03 = (right + left) / (right - left);
		this.m11 = 2*zNear / (top - bottom);
		this.m12 = (top + bottom) / (top - bottom);
		this.m22 = -(zFar + zNear) / (zFar - zNear);
		this.m23 = -2*zFar*zNear / (zFar - zNear);
		this.m32 = -1;
		
		return this;
	}
	
	public void displayPerspectiveMatProperties() {
		System.out.println("scale = " + d_scale + " | top = " + d_top + " | bottom = " + d_bottom +
				           " | left = " + d_left + " | right = " + d_right);
	}
	
	public Matrix4f translate(float x, float y, float z) {
		this.m03 = x;
		this.m13 = y;
		this.m32 = z;
		return this;
	}
	
	public Matrix4f translate(Vector3f vec) {
		this.m03 = vec.x;
		this.m13 = vec.y;
		this.m23 = vec.z;
		return this;
	}
}
