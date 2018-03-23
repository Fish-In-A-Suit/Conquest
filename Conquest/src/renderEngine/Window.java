package renderEngine;

import static org.lwjgl.glfw.GLFW.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

//import input.Input;

public class Window {
	public long windowHandle;
	public GLFWKeyCallback keycallback;
	private int width = 1920;
	private int height = 1080;
	private String title = "Conquest" ;
	
	public boolean[] keys = new boolean[35565];
	
	public void init() {
		if(!glfwInit()) {
			throw new IllegalStateException("Unable to initialize GLFW!");
		}
		
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
		
		windowHandle = glfwCreateWindow(width, height, title, NULL, NULL);
		
		if(windowHandle == NULL) {
			throw new RuntimeException("Failed to create the GLFW window!");
		}
		
		glfwSetKeyCallback(windowHandle, keycallback = GLFWKeyCallback.create((window, key, scancode, action, mods) -> {
			if (action != GLFW_RELEASE) {
				keys[key] = true;
			} else {
				keys[key] = false;
			}
		}));
		
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(windowHandle, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2 );
		
		// DEPR: glfwSetKeyCallback(windowHandle, new Input());
		
		glfwMakeContextCurrent(windowHandle);
		glfwShowWindow(windowHandle);
		
		GL.createCapabilities();

		glEnable(GL_DEPTH_TEST);
		System.out.println("OpenGL version: " + glGetString(GL_VERSION));
	}
}