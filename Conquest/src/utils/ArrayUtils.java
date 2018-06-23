package utils;

import java.util.ArrayList;
import java.util.List;

public class ArrayUtils {
	public static void displayFloatArray(float[] array) {
		for(int i = 0; i < array.length; i++) {
			System.out.print(array[i] + " ");
		}
		System.out.println();
	}
	
	public static String getFloatArray(float[] array) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for(int i = 0; i < array.length; i++) {
			sb.append(array[i]).append(" ");
		}
		sb.append("]");
		return sb.toString();
	}
	
	public static String getIntArray(int[] array) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for(int i = 0; i < array.length; i++) {
			sb.append(array[i]).append(" ");
		}
		sb.append("]");
		return sb.toString();
	}
	
	public static List<String> convertToStringList(String[] stringArray) {
		List<String> result = new ArrayList<>();
		
		for(int i = 0; i < stringArray.length; i++) {
			result.add(stringArray[i]);
		}
		return result;
	}
	
	public static void displayPopulation(float[] array) {
		int i = 0;
		for(float element : array) {
			i++;
		}
		System.out.println("The size of " + array.toString() + ": " + array.length + "; amount of populated indexes: " + i);
	}

}
