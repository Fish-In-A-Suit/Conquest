package main;

import static org.lwjgl.glfw.GLFW.*;

import models.GameEntity;
import renderEngine.Renderer;
import renderEngine.Window;

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
	private Game game;

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
		
		System.out.println("Creating a new Game object... ");
		game = new Game();
	}
	
	private void gameLoop() {
		long lastTime = System.nanoTime();
		double delta = 0.0;
		double ns = 1 / 60.0;
		System.out.println("ns = " + ns);
		long timer = System.currentTimeMillis();
		System.out.println("timer = " + timer);
		int updates = 0;
		int frames = 0;
		
		System.out.println("\n" + "[Main] Entering the main game loop!");
		while(running) {
			long now = System.nanoTime(); 
			delta += (now - lastTime) / ns; //time that has elapsed since the last cycle of the game loop / ns
			lastTime = now;
			
			if (delta >= 1.0) {
				processInput();
				updateBuffers();
				updates ++;
				delta--;
			}
			renderer.render(window, game.getGameEntities(), game.getCamera());		    
			frames++;
			
			//game.getGameEntities()[0].increaseRotation(1.0f, 0, 1.0f);
			
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				setWindowTitle(updates, frames, game.getGameEntities()[0]);
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
				for (GameEntity entity : game.getGameEntities()) {
					entity.getMesh().cleanUp();
				}
				
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
		
		game.updateLogic(window);
	}
	
	private void updateBuffers() {
		glfwSwapBuffers(window.windowHandle);
	}
	
	private void runAssertions()  {
		if (game.getGameEntities()[0].getMesh() == null)  {
			System.out.println("[Main] Mesh hasn't been created!");
		} else  {
			System.out.println("[Main] Mesh exists");
		}
		
		System.out.println("[Main] vaoID of mesh: " + game.getGameEntities()[0].getMesh().getVaoID());
		System.out.println("[Main] Width of the window: " + window.getWidth() + ", Height of the window: " + window.getHeight() );
	}
	
	private void setWindowTitle(int ups, int fps, GameEntity entity) {
		glfwSetWindowTitle(window.windowHandle,  "Conquest | UPS: " + ups + ", FPS: " + fps + "    |    world position: " + entity.getPosition().displayFriendly());
	}
	
	public static void main(String[] args) {
		new Main().start();
	}	
}
