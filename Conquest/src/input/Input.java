/*package input;
// DEPR:

import org.lwjgl.glfw.GLFWKeyCallback;
import static org.lwjgl.glfw.GLFW.*;

public class Input extends GLFWKeyCallback {
	public static boolean[] keys = new boolean[35565];
	
	/**
	 * @param window - the window which recieved input events
	 * @param key - the keyboard key which was pressed or released
	 * @param scanCode - the system specific scancode of the key
	 * @param action - one of: PRESS, RELEASE, REPEAT
	 * @param mods - bitfield describing which modifier keys were held down
	 * 
	 * This method is called everytime the glfwPollEvents() picks up an event
	 *
	public void invoke(long window, int key, int scanCode, int action, int mods) {
		if (action != GLFW_RELEASE) {
			keys[key] = true;
		} else {
			keys[key] = false;
		}
	}

}
*/