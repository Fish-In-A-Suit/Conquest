package utils;

import math.Matrix4f;

public class MatrixUtils {
	public static Matrix4f changeOrder(Matrix4f mat, Enum order) {
		if (order.equals(orders.ROW)) {
			return changeToColumnOrder(mat);
		} else
			return changeToRowOrder(mat);
	}
	
	private static Matrix4f changeToColumnOrder(Matrix4f mat) {
		Matrix4f result = new Matrix4f();
		//diagonal - same
		result.m00 = mat.m00;
		result.m11 = mat.m11;
		result.m22 = mat.m22;
		result.m33 = mat.m33;
		
		//lower triangle - copy upper triangle of mat
		result.m01 = mat.m10;
		result.m02 = mat.m20;
		result.m03 = mat.m30;
		result.m12 = mat.m21;
		result.m13 = mat.m31;
		result.m23 = mat.m32;
		
		//upper triangle - copy lower triangle of mat
		result.m10 = mat.m01;
		result.m20 = mat.m02;
		result.m21 = mat.m12;
		result.m30 = mat.m03;
		result.m31 = mat.m13;
		result.m32 = mat.m23;
		
		return result;
	}
	
	private static Matrix4f changeToRowOrder(Matrix4f mat) {
		Matrix4f result = new Matrix4f();
		//diagonal - same
		result.m00 = mat.m00;
		result.m11 = mat.m11;
		result.m22 = mat.m22;
		result.m33 = mat.m33;
		
		//lower triangle - copy upper triangle of mat
		result.m10 = mat.m01;
		result.m20 = mat.m02;
		result.m30 = mat.m03;
		result.m21 = mat.m12;
		result.m31 = mat.m13;
		result.m32 = mat.m23;
		
		//upper triangle - copy lower triangle of mat
		result.m01 = mat.m10;
		result.m02 = mat.m20;
		result.m03 = mat.m30;
		result.m12 = mat.m21;
		result.m13 = mat.m31;
		result.m23 = mat.m32;
		
		return result;
	}
	
	enum orders {
		ROW, COLUMN;
	}

}
