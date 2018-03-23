package main;

import static org.lwjgl.glfw.GLFW.*;
import renderEngine.Renderer;
//import input.Input;

import renderEngine.Window;

public class Main implements Runnable {
	private boolean running = false;
	private Thread renderingThread;

	private Window window = new Window();
	private Renderer renderer = new Renderer();
	
	private void start() {
		running = true;
		renderingThread = new Thread(this, "renderingThread");
		renderingThread.start();
		
	}
	
	public void run() {
		window.init();
		
		long lastTime = System.nanoTime();
		double delta = 0.0;
		double ns = 1000000000.0 / 60.0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			if (delta >= 1.0) {
				update();
				updates ++;
				delta--;
			}
			renderer.render();
			frames++;
			
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println(updates + " ups, " + frames + " fps");
				updates = 0;
				frames = 0;
			}
			
			if(glfwWindowShouldClose(window.windowHandle)) {
				glfwTerminate();
				running = false;
				
				glfwDestroyWindow(window.windowHandle);
				window.keycallback.free();
			}
		}
		
	}
	
	public void update() {
		glfwSwapBuffers(window.windowHandle);
		glfwPollEvents();
		
		if (window.keys[GLFW_KEY_SPACE] == true) {
			System.out.println("SPACEBAR was pressed");
		}
	}

	
	public static void main(String[] args) {
		new Main().start();
	}
	

}

