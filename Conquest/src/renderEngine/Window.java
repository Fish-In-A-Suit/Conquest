package renderEngine;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

/**
 * This class provides functionality for creating a glfw window and it's associated context
 * @author Aljoša
 *
 */
public class Window {
	public long windowHandle;
	public GLFWKeyCallback keycallback;
	
	private int width = 1920;
	private int height = 1080;

	private String title = "Conquest" ;
	
	private boolean resized = false;
	public boolean[] keys = new boolean[35565];

	/**
	 * This method creates the window with it's associated OpenGL context. It also:
	 *   - sets up a size callback
	 *   - sets up a key callback
	 *   - positions the window to the centre of the screen
	 */
	public void init() {
		if(glfwInit() == false) {
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
		
		glfwSetFramebufferSizeCallback(windowHandle, (window, width, height) -> {
		    this.width = width;
			this.height = height;
			resized = true;
			System.out.println("Window has been resized! New width and height are: " + width + " | " + height);
		});
		
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
		
		System.out.println("[Window] OpenGL version: " + glGetString(GL_VERSION));
	}
	
	public void setResized(boolean resized) {
		this.resized = resized;
	}
	
	public boolean isResized() {
		return resized;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}