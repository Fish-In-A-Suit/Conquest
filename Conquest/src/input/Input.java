package input;

import org.lwjgl.glfw.GLFWKeyCallback;
import static org.lwjgl.glfw.GLFW.*;

public class Input extends GLFWKeyCallback {
	public static boolean[] keys = new boolean[35565];
	
	/**
	 * @param window
	 * @param key
	 * @param scanCode
	 * @param action
	 * @param mods
	 */
	public void invoke(long window, int key, int scanCode, int action, int mods) {
		if (action != GLFW_RELEASE) {
			keys[key] = true;
		} else {
			keys[key] = false;
		}
	}

}
