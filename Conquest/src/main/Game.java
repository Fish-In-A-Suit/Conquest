package main;

import static org.lwjgl.glfw.GLFW.*;

import models.GameEntity;
import models.Mesh;
import models.Quad;
import renderEngine.Window;

/**
 * Responsible for providing methods to handle all of the game logic
 * @author Aljoša
 *
 */
public class Game {
	private Quad quad = new Quad();
	private GameEntity[] entities;
	private Mesh mesh;
	
	/**
	 * Creates a new mesh instance and uses positions, indices and colours of class Main.
	 * Creates a new GameEntity object (entity) and passes mesh as parameter
	 * Creates a new array of GameEntities and stores entity in that array.
	 */
	public Game() {
		System.out.println("[Game.Game]: Creating a new GameEntity based on data provided by quad...");
		GameEntity entity = new GameEntity(quad.positions, quad.indices, quad.colours);
		
		System.out.println("[Game.Game]: Updating entity position...");
		entity.updatePosition(0, 0, 0);
		
		System.out.println("[Game.Game]: Adding entity inside array entities...");
		entities = new GameEntity[] { entity };
	}

	public void updateLogic(Window window) {
		//this isn't ok, since it can only handle english keyboard... if I press z, GLW_KEY_Y gets called
		if (window.keys[GLFW_KEY_W]) {
            entities[0].updatePosition(0, 0, -1.0f);
		} else if (window.keys[GLFW_KEY_S]){
			entities[0].updatePosition(0, 0, 1.0f);
		} else if (window.keys[GLFW_KEY_A]) {
            entities[0].updatePosition(-1.0f, 0, 0);
		} else if (window.keys[GLFW_KEY_D]) {
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
		} else if (window.keys[GLFW_KEY_I]) {
			entities[0].increaseRotation(1.0f, 1.0f, 1.0f);
		} else if (window.keys[GLFW_KEY_J]) {
			entities[0].increaseScale(1.0f);
		} else if (window.keys[GLFW_KEY_K]) {
			entities[0].increaseScale(-1.0f);
		} else if (window.keys[GLFW_KEY_F]) {
			entities[0].increaseAllFun(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f);
		}
		
	}
/*	
	public void setupMesh(float[] vertices, int[] indices, float[] colours) {
		mesh = new Mesh(vertices, indices, colours);
	}
*/	
	public GameEntity[] getGameEntities() {
		return entities;
	}
	
	public Mesh getMesh() {
		return mesh;
	}

}