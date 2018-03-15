package renderEngine;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import input.Input;

public class Window {
	public long windowHandle;
	private int width = 1920;
	private int height = 1080;
	private String title = "Conquest" ;
	
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
		
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(windowHandle, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2 );
		
		glfwSetKeyCallback(windowHandle, new Input());
		
		glfwMakeContextCurrent(windowHandle);
		glfwShowWindow(windowHandle);
		
		GL.createCapabilities();
		
		/**
		 * @param red - the float value to which to clear the R channel of the color buffer
		 * @param green - the float value to which to clear the G channel of the color buffer
		 * @param blue - the float value to which to clear the B channel of the color buffer
		 * @param alpha - the float value to which to clear the A channel of the color buffer
		 */
		glClearColor(0.0f, 0.0f, 1.0f, 1.0f);
		glEnable(GL_DEPTH_TEST);
		System.out.println("OpenGL version: " + glGetString(GL_VERSION));
	}
}