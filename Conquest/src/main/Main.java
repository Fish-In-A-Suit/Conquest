package main;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.GL;

import input.Input;
import renderEngine.Window;

public class Main implements Runnable {
	private boolean running = false;
	private Thread renderingThread;
	private Window window = new Window();
	
	private void start() {
		running = true;
		renderingThread = new Thread(this, "renderingThread");
		renderingThread.start();
		
	}
	
	public void run() {
		window.init();
		
		while(running) {
			update();
			render();
			
			if(glfwWindowShouldClose(window.windowHandle)) {
				running = false;
			}
		}
		
	}
	
	public void update() {
		glfwPollEvents();
		
		if (Input.keys[GLFW_KEY_SPACE]) {
			System.out.println("SPACEBAR was pressed");
		}
	}
	
	public void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glfwSwapBuffers(window.windowHandle);
	}
	
	public static void main(String[] args) {
		new Main().start();
	}
	

}
