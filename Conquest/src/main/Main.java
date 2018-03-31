package main;

import static org.lwjgl.glfw.GLFW.*;

import renderEngine.Mesh;
import renderEngine.Renderer;
//import input.Input;

import renderEngine.Window;

public class Main implements Runnable {
	private boolean running = false;
	private boolean assertions = false;
	private Thread renderingThread;

	private Window window = new Window();
	private Renderer renderer = new Renderer();
	private Mesh mesh;
	
	float[] positions = {
            -0.5f, 0.5f, 0f, //VO
            -0.5f, -0.5f, 0f, //V1
            
            0.5f, -0.5f, 0f, //V2
            0.5f, 0.5f, 0f //V3
	};
	
	int[] indices = {
		0, 1, 3, //top left triangle (V0, V1, V3)
		3, 1, 2 //bottom right triangle (V3, V1, V2)
	};

	private void start() {
		running = true;
		renderingThread = new Thread(this, "renderingThread");
		renderingThread.start();
		
	}
	
	public void run() {
		window.init();
		setupMesh(positions, indices);
		
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
			renderer.clear();
		    renderer.render(window, mesh);
			frames++;
					
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println(updates + " ups, " + frames + " fps");
				updates = 0;
				frames = 0;
				if (assertions == true) {
					runAssertions();
				}
			}
					
			if(glfwWindowShouldClose(window.windowHandle)) {
				running = false;
				mesh.cleanUp();
				window.keycallback.free();
				glfwDestroyWindow(window.windowHandle);
				glfwTerminate();
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
	
	public void setupMesh(float[] vertices, int[] indices) {
		mesh = new Mesh(vertices, indices);
	}

	private void runAssertions()  {
		if (mesh == null)  {
			System.out.println("Mesh == null!");
		} else  {
			System.out.println("Mesh exists");
		}
		
		System.out.println("vaoID of mesh: " + mesh.getVaoID());
		System.out.println("Width: " + window.getWidth() + ", Height: " + window.getHeight() );
	}
	
	public static void main(String[] args) {
		new Main().start();
	}	
}

