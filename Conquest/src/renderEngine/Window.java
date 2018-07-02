package renderEngine;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;
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
	public GLFWCursorPosCallback cursorCallback;
	public GLFWMouseButtonCallback mouseButtonCallback;
	public GLFWScrollCallback scrollCallback;
	
	private int width = 1920;
	private int height = 1080;

	private String title = "Conquest" ;
	
	private boolean resized = false;
	public boolean[] keys = new boolean[35565];
	private boolean cursorChange;
	private double mouseX, mouseY; //mouse cursor positions
	private double deltaMX, deltaMY; //how much has the cursor moved?
	private boolean mouseLeftPressed, mouseRightPressed;
	private double scrollAmount;
	private boolean isScrolled;

	/**
	 * This method creates the window with it's associated OpenGL context.
	 */
	public void init() {
		if(glfwInit() == false) {
			throw new IllegalStateException("Unable to initialize GLFW!");
		}
		System.out.println("[Window.init]: Creating window... ");
		
		mouseX = 0;
		mouseY = 0;
		
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
		
		//xpos = current x position of the cursor; ypos = current y position of the cursor --> relative to the upper-left corner
		glfwSetCursorPosCallback(windowHandle, cursorCallback = GLFWCursorPosCallback.create((window, xpos, ypos) -> {
			deltaMX = xpos - mouseX;
			deltaMY = ypos - mouseY;
			cursorChange = true;
			mouseX = xpos;
			mouseY = ypos;
		}));
		
		glfwSetMouseButtonCallback(windowHandle, mouseButtonCallback = GLFWMouseButtonCallback.create((window, button, action, mods) -> {
			if (button == GLFW_MOUSE_BUTTON_1 && action == GLFW_PRESS) {
				mouseLeftPressed = true;
			} else if (button == GLFW_MOUSE_BUTTON_1 && action == GLFW_RELEASE) {
				mouseLeftPressed = false;
			}
		}));
		
		/*
		 * This method gets called every time the mouse wheel is scrolled.
		 * yoffset represents the vertical ("normal") scroll amount
		 */
		glfwSetScrollCallback(windowHandle, scrollCallback = GLFWScrollCallback.create((window, xoffset, yoffset) -> {
			scrollAmount = yoffset;
			isScrolled = true;
		}));
		
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(windowHandle, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2 );
		
		// DEPR: glfwSetKeyCallback(windowHandle, new Input());
		
		glfwMakeContextCurrent(windowHandle);
		glfwShowWindow(windowHandle);
		
		GL.createCapabilities();
		
		glEnable(GL_DEPTH_TEST);
		
		System.out.println("[Window.init] OpenGL version: " + glGetString(GL_VERSION));
	}
	
	//set the value for whether the cursor has moved
	public void setCursorChange(boolean value) {
		cursorChange = value;
	}
	
	//has the cursor moved?
	public boolean getCursorChange() {
		return cursorChange;
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
	
	public double getDeltaMX() {
		return deltaMX;
	}
	
	public double getDeltaMY() {
		return deltaMY;
	}
	
	public boolean isMouseLeftPressed() {
		return mouseLeftPressed;
	}
	
	public boolean isScrolled() {
		return isScrolled;
	}
	
	public void setScrolled(boolean value) {
		isScrolled = value;
	}
	
	public double getScrollAmount() {
		return scrollAmount;
	}
}