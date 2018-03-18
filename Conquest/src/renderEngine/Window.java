package renderEngine;

import static org.lwjgl.glfw.GLFW.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

//import input.Input;

public class Window {
	public static long windowHandle;
	private int width = 1920;
	private int height = 1080;
	private String title = "Conquest" ;
	
	public static boolean[] keys = new boolean[35565];
	
	public void init() {
		if(!glfwInit()) {
			throw new IllegalStateException("Unable to initialize GLFW!");
		}
		
		glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
		
		windowHandle = glfwCreateWindow(width, height, title, NULL, NULL);
		
		if(windowHandle == NULL) {
			throw new RuntimeException("Failed to create the GLFW window!");
		}
		
		glfwSetKeyCallback(windowHandle, (window, key, scancode, action, mods) -> {
			if (action != GLFW_RELEASE) {
				keys[key] = true;
			} else {
				keys[key] = false;
			}
		});
		
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(windowHandle, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2 );
		
		// DEPR: glfwSetKeyCallback(windowHandle, new Input());
		
		glfwMakeContextCurrent(windowHandle);
		glfwShowWindow(windowHandle);
		
		GL.createCapabilities();
		
		/**
		 * @param red - the float value to which to clear the R channel of the color buffer
		 * @param green - the float value to which to clear the G channel of the color buffer
		 * @param blue - the float value to which to clear the B channel of the color buffer
		 * @param alpha - the float value to which to clear the A channel of the color buffer
		 */
		glEnable(GL_DEPTH_TEST);
		System.out.println("OpenGL version: " + glGetString(GL_VERSION));
	}
}