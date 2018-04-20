package main;

import static org.lwjgl.glfw.GLFW.*;

import renderEngine.Mesh;
import renderEngine.Renderer;
//import input.Input;

import renderEngine.Window;
import utils.Timer;

/**
 * The starting point of the program - where all of the game comes together
 * 
 * @author Aljoša
 *
 */
public class Main implements Runnable {
	private boolean running = false;
	private boolean assertions = true;
	private Thread renderingThread;

	private Window window = new Window();
	private Renderer renderer = new Renderer();
	private Mesh mesh;
	private Timer timer = new Timer();
	
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
	
	float[] colours = {
			0.5f, 0.5f, 0.0f,
			0.4f, 0.3f, 0.2f,
			0.8f, 0.6f, 0.8f,
			0.2f, 0.3f, 0.4f
	};

	private void start() {
		System.out.println("****************** STARTING CONQUEST ******************");
		running = true;
		renderingThread = new Thread(this, "renderingThread");
		renderingThread.start();
		
	}
	
	public void run() {
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
		gameLoop();
	}
	
	/**
	 * This method is responsible for intializing all of the components which are cruicial for the game to start, that is:
	 *  - creating the window
	 *  - set up a shader program
	 *  - setting up the mesh
	 *  
	 * @throws Exception
	 */
	private void init() throws Exception {
		System.out.println("[Main] Creating window... ");
		window.init();
		
		System.out.println("[Main] Setting up renderer... ");
		renderer.init();
		
		System.out.println("[Main] Starting timer... ");
		timer.init();
		
		System.out.println("Creating a new mesh object... ");
		setupMesh(positions, indices, colours);
	}
	
	private void gameLoop() {
		long lastTime = System.nanoTime();
		double delta = 0.0;
		double ns = 1000000000.0 / 60.0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		
		System.out.println("\n" + "[Main] Entering the main game loop!");
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			if (delta >= 1.0) {
				processInput();
				update();
				updates ++;
				delta--;
			}
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
				System.out.println("\n" + "****************** Starting cleanup operations ******************");
				running = false;
				
				System.out.println("[Main] Cleaning up mesh...");
				mesh.cleanUp();
				
				System.out.println("[Main] Freeing window callbacks...");
				window.keycallback.free();
				
				System.out.println("[Main] Destroying window...");
				glfwDestroyWindow(window.windowHandle);
				
				System.out.println("[Main] Terminating the glfw context...");
				glfwTerminate();
			}
		}
	}
	
	private void processInput() {
		glfwPollEvents();
		
		if (window.keys[GLFW_KEY_SPACE] == true) {
			System.out.println("SPACEBAR was pressed");
		}
	}
	
	public void update() {
		glfwSwapBuffers(window.windowHandle);

	}
	
	/**
	 * This method creates a new instance of Mesh given the following parameters
	 * @param vertices - the float array of vertex positions of a model
	 * @param indices - the int array indices, which specify how OpenGL should connect the model's vertices
	 * @param colours - the float array of associated vertex colours
	 */
	public void setupMesh(float[] vertices, int[] indices, float[] colours) {
		mesh = new Mesh(vertices, indices, colours);
	}

	private void runAssertions()  {
		if (mesh == null)  {
			System.out.println("[Main] Mesh hasn't been created!");
		} else  {
			System.out.println("[Main] Mesh exists");
		}
		
		System.out.println("[Main] vaoID of mesh: " + mesh.getVaoID());
		System.out.println("[Main] Width of the window: " + window.getWidth() + ", Height of the window: " + window.getHeight() );
	}
	
	public static void main(String[] args) {
		new Main().start();
	}	
}

