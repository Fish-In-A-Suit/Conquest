package math;

import math.Matrix4f;
import math.Vector3f;

public class Transformations {
	private Matrix4f translationMatrix;
	private Matrix4f rotationMatrix;
	private static Matrix4f projectionMatrix;
	
	public float dAspect;
	
	public Transformations() {
		System.out.println("[Transformation.Transformations()]: Creating translation matrix... ");
		translationMatrix = new Matrix4f();
		projectionMatrix = new Matrix4f();
	}

	/**
	 * A method for creating a translation matrix non-statically
	 * @param x The x value to translate
	 * @param y The y value to translate
	 * @param z The z value to translate
	 * @return Matrix4f
	 */
	public Matrix4f getTranslationMatrix(float x, float y, float z) {
		translationMatrix.m03 = x;
		translationMatrix.m13 = y;
		translationMatrix.m23 = z;
		return translationMatrix;
		
	}
	
	/**
	 * A method for creating a translation matrix non-statically given a vector
	 * @param vec The vec by which to translate
	 * @return Matrix4f
	 */
	public Matrix4f getTranslationMatrix(Vector3f vec) {
		translationMatrix.translate(vec);
		return translationMatrix;
	}
	
	/**
	 * Creates and returns a rotation matrix
	 * @param rotx The angle by which to rotate around the x axis
	 * @param roty The angle by which to rotate around the y axis
	 * @param rotz The angle by which to rotate around the z axis
	 * @return
	 */
	public Matrix4f getRotationMatrix(float rotx, float roty, float rotz) {
		rotationMatrix = new Matrix4f().rotate(rotx, roty, rotz);
		return rotationMatrix;
	}
	
    /**
     * Creates and returns a perspective projection matrix.
     * @param fov The vertical field of view
     * @param width The width of the window
     * @param height The height of the window
     * @param zNear The near clipping plane
     * @param zFar The far clipping plane
     * @return
     */
	public Matrix4f getProjectionMatrix(float fovy, int width, int height, float zNear, float zFar) {
		//System.out.println("[Transformations.getProjectionMatrix]: window width = " + width + " | window height = " + height );
		float aspectRatio = (float) width / height;
		projectionMatrix.perspective(fovy, aspectRatio, zNear, zFar);
		return projectionMatrix;
	}
}
