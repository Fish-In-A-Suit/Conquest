package main;

import static org.lwjgl.glfw.GLFW.*;
import loaders.OBJLoader;
import models.GameEntity;
import models.Mesh;
import renderEngine.Camera;
import renderEngine.Window;

/**
 * Responsible for providing methods to handle all of the game logic and entities
 * @author Aljoša
 *
 */
public class Game {
	private GameEntity[] entities;
	private Mesh mesh;
	private Camera camera;
	
	/**
	 * Creates a new mesh instance and uses positions, indices and colours of class Main.
	 * Creates a new GameEntity object (entity) and passes mesh as parameter
	 * Creates a new array of GameEntities and stores entity in that array.
	 */
	public Game() {
		GameEntity cubeEntity = null;
		GameEntity pineTree = null;
		try {
			System.out.println("[Game.Game]: Creating cube... ");
			cubeEntity = OBJLoader.loadObjModel("cube", "cubeTexture1");
			pineTree = OBJLoader.loadObjModel("pineTree", "pineTree");
		} catch (Exception e) {
			System.err.println("[Game.Game] An error has occurred while trying to load a game entity");
			e.printStackTrace();
		}
		System.out.println("[Game.Game] Adding cube to the entities array");
		entities = new GameEntity[] {pineTree};
		
		System.out.println("[Game.Game]: Creating a new camera object... ");
		camera = new Camera();
	}

	/**
	 * Collects window input events (listens for keyboard and mouse events)
	 * @param window
	 */
	public void updateLogic(Window window) {
		//this isn't ok, since it can only handle english keyboard... if I press z, GLW_KEY_Y gets called
		if (window.keys[GLFW_KEY_UP]) {
            entities[0].updatePosition(0, -1.0f, 0f);
		} else if (window.keys[GLFW_KEY_DOWN]){
			entities[0].updatePosition(0, 1.0f, 0f);
		} else if (window.keys[GLFW_KEY_LEFT]) {
            entities[0].updatePosition(-1.0f, 0, 0);
		} else if (window.keys[GLFW_KEY_RIGHT]) {
			entities[0].updatePosition(1.0f, 0, 0);
		} else if (window.keys[GLFW_KEY_SPACE]) {
			entities[0].updatePosition(0, 1.0f, 0);
		} else if (window.keys[GLFW_KEY_LEFT_SHIFT]) {
			entities[0].updatePosition(0, -1.0f, 0);
		} else if (window.keys[GLFW_KEY_X]) {
			entities[0].increaseRotation(2.0f, 0f, 0f);
		} else if (window.keys[GLFW_KEY_Y]) {
			entities[0].increaseRotation(0f, 2.0f, 0f);
		} else if (window.keys[GLFW_KEY_Z]) {
			entities[0].increaseRotation(0f, 0f, 2.0f);
		} else if (window.keys[GLFW_KEY_U]) {
			entities[0].increaseRotation(1.0f, 1.0f, 1.0f);
		} else if (window.keys[GLFW_KEY_B]) {
			entities[0].increaseScale(1.0f);
		} else if (window.keys[GLFW_KEY_N]) {
			entities[0].increaseScale(-1.0f);
		} else if (window.keys[GLFW_KEY_M]) {
			entities[0].increaseAllFun(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f);
		} else if (window.keys[GLFW_KEY_W]) {
			camera.increasePosition(0f, 0f, -1.0f);
		} else if (window.keys[GLFW_KEY_S]) {
			camera.increasePosition(0f, 0f, 1.0f);
		} else if (window.keys[GLFW_KEY_A]) {
			camera.increasePosition(-1.0f, 0f, 0f);
		} else if (window.keys[GLFW_KEY_D]) {
			camera.increasePosition(1.0f, 0f, 0f);
		}
		//developmental mode of rotation
		//if is left clicked and moved at the same time, rotate the scene
		if(window.getCursorChange() && window.isMouseLeftPressed()) {
			camera.increaseRotation(window.getDeltaMX(), window.getDeltaMY());
			window.setCursorChange(false);
		}
		
		/* "Gaming mode of rotating the camera"
		if(window.getCursorChange()) {
			camera.increaseRotation(window.getDeltaMX(), window.getDeltaMY());
			window.setCursorChange(false);
		}
		*/
		//if is scrolled, zoom in
		if (window.isScrolled()) {
			camera.increasePosition(0, 0, -(float)window.getScrollAmount());
			window.setScrolled(false);
		}
	}

	public GameEntity[] getGameEntities() {
		return entities;
	}
	
	public Mesh getMesh() {
		return mesh;
	}
	
	public Camera getCamera() {
		return camera;
	}

}