package math;

public class Matrix4f {
	public float m00, m01, m02, m03;
	public float m10, m11, m12, m13;
	public float m20, m21, m22, m23;
	public float m30, m31, m32, m33;
	
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
	
	public Matrix4f(Matrix4fColumn mat) {
		//diagonal stays the same
		this.m00 = mat.m00;
		this.m11 = mat.m11;
		this.m22 = mat.m22;
		this.m33 = mat.m33;
		
		//lower triangle - copy from mat upper triangle
		this.m10 = mat.m01;
		this.m20 = mat.m02;
		this.m30 = mat.m03;
		this.m21 = mat.m12;
		this.m31 = mat.m13;
		this.m32 = mat.m23;
		
		//upper triangle - copy from mat lower triangle
		this.m01 = mat.m10;
		this.m02 = mat.m20;
		this.m03 = mat.m30;
		this.m12 = mat.m21;
		this.m13 = mat.m31;
		this.m23 = mat.m32;
		
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
	
	/**
	 * Multiplies the calling matrix by the matrix which is input as the parameter
	 * @param mat
	 * @return
	 */
	public Matrix4f multiplyByMatrix(Matrix4f mat) {
		Matrix4f result = new Matrix4f();
		//set first row
		result.m00 = this.m00*mat.m00 + this.m01*mat.m10 + this.m02*mat.m20 + this.m03*mat.m30;
		result.m01 = this.m00*mat.m01 + this.m01*mat.m11 + this.m02*mat.m21 + this.m03*mat.m31;
		result.m02 = this.m00*mat.m02 + this.m01*mat.m12 + this.m02*mat.m22 + this.m03*mat.m32;
		result.m03 = this.m00*mat.m03 + this.m01*mat.m13 + this.m02*mat.m23 + this.m03*mat.m33;
		//set 2nd row
		result.m10 = this.m10*mat.m00 + this.m11*mat.m10 + this.m12*mat.m20 + this.m13*mat.m30;
		result.m11 = this.m10*mat.m01 + this.m11*mat.m11 + this.m12*mat.m21 + this.m13*mat.m31;
		result.m12 = this.m10*mat.m02 + this.m11*mat.m12 + this.m12*mat.m22 + this.m13*mat.m32;
		result.m13 = this.m10*mat.m03 + this.m11*mat.m13 + this.m12*mat.m23 + this.m13*mat.m33;
		//set 3rd row
		result.m20 = this.m20*mat.m00 + this.m21*mat.m10 + this.m22*mat.m20 + this.m23*mat.m30;
		result.m21 = this.m20*mat.m01 + this.m21*mat.m11 + this.m22*mat.m21 + this.m23*mat.m31;
		result.m22 = this.m20*mat.m02 + this.m21*mat.m12 + this.m22*mat.m22 + this.m23*mat.m32;
		result.m23 = this.m20*mat.m03 + this.m21*mat.m13 + this.m22*mat.m23 + this.m23*mat.m33;
		//set 4th row
		result.m30 = this.m30*mat.m00 + this.m31*mat.m10 + this.m32*mat.m20 + this.m33*mat.m30;
		result.m31 = this.m30*mat.m01 + this.m31*mat.m11 + this.m32*mat.m21 + this.m33*mat.m31;
		result.m32 = this.m30*mat.m02 + this.m31*mat.m12 + this.m32*mat.m22 + this.m33*mat.m32;
		result.m33 = this.m30*mat.m03 + this.m31*mat.m13 + this.m32*mat.m23 + this.m33*mat.m33;
		
		return result;
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
		float right = scale * aspectRatio;
		d_right = right;
		float bottom = -top;
		d_bottom = bottom;
		float left = -scale * aspectRatio;
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
		this.m23 = z;
		return this;
	}
	
	public Matrix4f translate(Vector3f vec) {
		this.m03 = vec.x;
		this.m13 = vec.y;
		this.m23 = vec.z;
		return this;
	}
	
	/**
	 * Rotates a Matrix4f around the x axis by the specified angle
	 * @param angle
	 * @return
	 */
	// this is wrong
	public Matrix4f rotateX(double angle) {
		this.m11 = (float) Math.cos(angle);
		this.m12 = - (float) Math.sin(angle);
		this.m21 = (float) Math.sin(angle);
		this.m22 = (float) Math.cos(angle);
		return this;
	}
	
	/**
	 * Rotates a Matrix4f around the y axis by the specified angle
	 * @param angle
	 * @return
	 */
	//this is wrong
	public Matrix4f rotateY(double angle) {
		this.m00 = (float) Math.cos(angle);
		this.m02 = (float) Math.sin(angle);
		this.m20 = - (float) Math.sin(angle);
		this.m22 = (float) Math.cos(angle);
		return this;
	}
	
	/**
	 * Rotates a Matrix4f around the z axis by the specified angle
	 * @param angle
	 * @return
	 */
	//this is wrong
	public Matrix4f rotateZ(double angle) {
		float cosZ = (float) Math.cos(angle);
		float sinZ = (float) Math.sin(angle);
		
		this.m00 = cosZ;
		this.m01 = - sinZ;
		this.m10 = sinZ;
		this.m11 = cosZ;
		
		/*
		this.m00 = c;
		this.m01 = - s;
		this.m10 = s;
		this.m11 = c;
		*/
		return this;
	}
	
	/**
	 * Rotates a Matrix4f around the x, y and z axes by the specified angles
	 * @param rotx
	 * @param roty
	 * @param rotz
	 * @return
	 */
	//this is wrong
	public Matrix4f rotate(double rotx, double roty, double rotz) {
		//return this.rotateY(roty).multiplyByMatrix(this.rotateX(rotx)).multiplyByMatrix(this.rotateZ(rotz));

		Matrix4f rotateX = new Matrix4f().rotateX(rotx);
		Matrix4f rotateY = new Matrix4f().rotateY(roty);
		Matrix4f rotateZ = new Matrix4f().rotateZ(rotz);
		Matrix4f result = rotateY.multiplyByMatrix(rotateZ).multiplyByMatrix(rotateX);
		return result;
	}
	
	public Matrix4f rotateXZY(float rx, float ry, float rz, Matrix4f dest) {
		float sinX = (float) Math.sin(rx);
		float cosX = (float) Math.cos(rx);
		float sinY = (float) Math.sin(ry);
		float cosY = (float) Math.cos(ry);
		float sinZ = (float) Math.sin(rz);
		float cosZ = (float) Math.cos(rz);
		float mSinX = -sinX;
		float mSinY = -sinY;
		float mSinZ = -sinZ;
		
		//rotate x
		float nm10 = m10 * cosX + m20 * sinX;
        float nm11 = m11 * cosX + m21 * sinX;
        float nm12 = m12 * cosX + m22 * sinX;
        float nm13 = m13 * cosX + m23 * sinX;
        float nm20 = m10 * mSinX + m20 * cosX;
        float nm21 = m11 * mSinX + m21 * cosX;
        float nm22 = m12 * mSinX + m22 * cosX;
        float nm23 = m13 * mSinX + m23 * cosX;
        //rotateY
        float nm00 = m00 * cosY + nm20 * mSinY;
        float nm01 = m01 * cosY + nm21 * mSinY;
        float nm02 = m02 * cosY + nm22 * mSinY;
        float nm03 = m03 * cosY + nm23 * mSinY;
        dest.m20 = m00 * sinY + nm20 * cosY;
        dest.m21 = m01 * sinY + nm21 * cosY;
        dest.m22 = m02 * sinY + nm22 * cosY;
        dest.m23 = m03 * sinY + nm23 * cosY;
        //rotateZ
        dest.m00 = nm00 * cosZ + nm10 * sinZ;
        dest.m01 = nm01 * cosZ + nm11 * sinZ;
        dest.m02 = nm02 * cosZ + nm12 * sinZ;
        dest.m03 = nm03 * cosZ + nm13 * sinZ;
        dest.m10 = nm00 * mSinZ + nm10 * cosZ;
        dest.m11 = nm01 * mSinZ + nm11 * cosZ;
        dest.m12 = nm02 * mSinZ + nm12 * cosZ;
        dest.m13 = nm03 * mSinZ + nm13 * cosZ;
        
        //copy last column from this
        dest.m30 = m30;
        dest.m31 = m31;
        dest.m32 = m32;
        dest.m33 = m33;
        
        return dest;
	}
	
	public Matrix4f rotateXZY(float rx, float ry, float rz) {
		float sinX = (float) Math.sin(rx);
		float cosX = (float) Math.cos(rx);
		float sinY = (float) Math.sin(ry);
		float cosY = (float) Math.cos(ry);
		float sinZ = (float) Math.sin(rz);
		float cosZ = (float) Math.cos(rz);
		float mSinX = -sinX;
		float mSinY = -sinY;
		float mSinZ = -sinZ;
		
		//rotate x
		float nm10 = m10 * cosX + m20 * sinX;
        float nm11 = m11 * cosX + m21 * sinX;
        float nm12 = m12 * cosX + m22 * sinX;
        float nm13 = m13 * cosX + m23 * sinX;
        float nm20 = m10 * mSinX + m20 * cosX;
        float nm21 = m11 * mSinX + m21 * cosX;
        float nm22 = m12 * mSinX + m22 * cosX;
        float nm23 = m13 * mSinX + m23 * cosX;
        //rotateY
        float nm00 = m00 * cosY + nm20 * mSinY;
        float nm01 = m01 * cosY + nm21 * mSinY;
        float nm02 = m02 * cosY + nm22 * mSinY;
        float nm03 = m03 * cosY + nm23 * mSinY;
        this.m20 = m00 * sinY + nm20 * cosY;
        this.m21 = m01 * sinY + nm21 * cosY;
        this.m22 = m02 * sinY + nm22 * cosY;
        this.m23 = m03 * sinY + nm23 * cosY;
        //rotateZ
        this.m00 = nm00 * cosZ + nm10 * sinZ;
        this.m01 = nm01 * cosZ + nm11 * sinZ;
        this.m02 = nm02 * cosZ + nm12 * sinZ;
        this.m03 = nm03 * cosZ + nm13 * sinZ;
        this.m10 = nm00 * mSinZ + nm10 * cosZ;
        this.m11 = nm01 * mSinZ + nm11 * cosZ;
        this.m12 = nm02 * mSinZ + nm12 * cosZ;
        this.m13 = nm03 * mSinZ + nm13 * cosZ;
        
        //copy last column from this
        this.m30 = m30;
        this.m31 = m31;
        this.m32 = m32;
        this.m33 = m33;
        
        return this;
	}
	
	/**
	 * Creates a matrix which scales an object proportionally by the defined scale
	 * @param scale
	 * @return
	 */
	public Matrix4f scale(float scale) {
		this.m00 = scale;
		this.m11 = scale;
		this.m22 = scale;
		return this;
	}
	
	/**
	 * Inverts a matrix
	 * @return
	 */
	public Matrix4f invert() {
		Matrix4fColumn src = new Matrix4fColumn(this);
		
		float a = src.m00 * src.m11 - src.m01 * src.m10;
	    float b = src.m00 * src.m12 - src.m02 * src.m10;
	    float c = src.m00 * src.m13 - src.m03 * src.m10;
	    float d = src.m01 * src.m12 - src.m02 * src.m11;
	    float e = src.m01 * src.m13 - src.m03 * src.m11;
	    float f = src.m02 * src.m13 - src.m03 * src.m12;
	    float g = src.m20 * src.m31 - src.m21 * src.m30;
	    float h = src.m20 * src.m32 - src.m22 * src.m30;
	    float i = src.m20 * src.m33 - src.m23 * src.m30;
	    float j = src.m21 * src.m32 - src.m22 * src.m31;
	    float k = src.m21 * src.m33 - src.m23 * src.m31;
	    float l = src.m22 * src.m33 - src.m23 * src.m32;
	     
	    float det = a*l - b*k + c*j + d*i - e*h + f*g;
	    float nm00, nm01, nm02, nm03, nm10, nm11, nm12, nm13, nm20, nm21, nm22, nm23, nm30, nm31, nm32, nm33;
	    det = 1.0f / det;
	    nm00 = ( src.m11 * l - src.m12 * k + src.m13 * j) * det;
	    nm01 = (-src.m01 * l + src.m02 * k - src.m03 * j) * det;
	    nm02 = ( src.m31 * f - src.m32 * e + src.m33 * d) * det;
	    nm03 = (-src.m21 * f + src.m22 * e - src.m23 * d) * det;
	    nm10 = (-src.m10 * l + src.m12 * i - src.m13 * h) * det;
	    nm11 = ( src.m00 * l - src.m02 * i + src.m03 * h) * det;
	    nm12 = (-src.m30 * f + src.m32 * c - src.m33 * b) * det;
	    nm13 = ( src.m20 * f - src.m22 * c + src.m23 * b) * det;
	    nm20 = ( src.m10 * k - src.m11 * i + src.m13 * g) * det;
	    nm21 = (-src.m00 * k + src.m01 * i - src.m03 * g) * det;
	    nm22 = ( src.m30 * e - src.m31 * c + src.m33 * a) * det;
	    nm23 = (-src.m20 * e + src.m21 * c - src.m23 * a) * det;
	    nm30 = (-src.m10 * j + src.m11 * h - src.m12 * g) * det;
	    nm31 = ( src.m00 * j - src.m01 * h + src.m02 * g) * det;
	    nm32 = (-src.m30 * d + src.m31 * b - src.m32 * a) * det;
	    nm33 = ( src.m20 * d - src.m21 * b + src.m22 * a) * det;
	    
	    //pr - "partial result", since it has to be transformed to row-major 
	    Matrix4fColumn pr = new Matrix4fColumn();
	    pr.m00 = nm00;
	    pr.m01 = nm01;
	    pr.m02 = nm02;
	    pr.m03 = nm03;
	    pr.m10 = nm10;
	    pr.m11 = nm11;
	    pr.m12 = nm12;
	    pr.m13 = nm13;
	    pr.m20 = nm20;
	    pr.m21 = nm21;
	    pr.m22 = nm22;
	    pr.m23 = nm23;
	    pr.m30 = nm30;
	    pr.m31 = nm31;
	    pr.m32 = nm32;
	    pr.m33 = nm33;
	    
	    return new Matrix4f(pr);
		
	}
	
}
