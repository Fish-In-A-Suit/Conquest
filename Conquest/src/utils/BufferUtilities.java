package utils;

import org.lwjgl.BufferUtils;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * @author Aljoša
 * 
 * This class provides buffer-related methods, so that whenever you need to create a bufffer of choice, you just
 * import this class and call a corresponding method - therefore creating a buffer with only 1 LOC instead of 3.
 *
 */
public class BufferUtilities {
	
	/**
	 * @param array The float array of data to be stored in a FloatBuffer
	 * @return result - a FloatBuffer populated with data and ready to be read from
	 * 
	 * This method takes in a float array of data, creates a FloatBuffer, populates it with the contents of the
	 * specified array of data and prepares that FloatBuffer to be read from (it flips it)
	 */
	public static FloatBuffer storeDataInFloatBuffer(float[] array) {
		FloatBuffer result = BufferUtils.createFloatBuffer(array.length);
		//System.out.println("The state of the floatbuffer prior to populating it with data: " + result.toString());
		result.put(array);
		//System.out.println("The state of the floatbuffer after populating it with data: " + result.toString());
		result.flip();
		//System.out.println("The state of the floatbuffer after flipping it: " + result.toString());
		return result;
	}
	
	/**
	 * @param array The int array of data to be stored in a FloatBuffer
	 * @return result - an IntBuffer populated with data and ready to be read from
	 * 
	 * This method takes in an int array of data, creates an IntBuffer, populates it with the contents of the
	 * specified array of data and prepares that IntBuffer to be read from (it flips it)
	 */
	public static IntBuffer storeDataInIntBuffer(int[] array) {
		IntBuffer result = BufferUtils.createIntBuffer(array.length);
		result.put(array).flip();
		return result;	
	}
}
