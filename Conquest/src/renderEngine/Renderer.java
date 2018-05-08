package renderEngine;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import math.Matrix4f;
import shaders.ShaderProgram;
import utils.FileUtilities;
import math.Transformations;
import math.Vector3f;
import math.Vector4f;
import models.GameEntity;

/**
 * This class deals with rendering to the window
 * @author Aljoša
 * 
 */
public class Renderer {
	
	private ShaderProgram shaderProgram;
	private Window window = new Window();
	
	private final Transformations transformation;
	
	private Matrix4f translationMatrix = new Matrix4f();
    private Matrix4f projectionMatrix = new Matrix4f();
	
	
	private static double angleOfView = 60.0;
	private static final float FOVY = (float) Math.toRadians(angleOfView);
	private static final float zNear = 0.01f;
	private static final float zFar = 1000.0f;
	
	//private Matrix4f projectionMatrix = transformation.getProjectionMatrix(FOVY, window.getWidth(), window.getHeight(), zNear, zFar);
	
	public Renderer() {
		transformation = new Transformations();
	}
	
	/**
	 * This method creates shaders and starts executing them
	 * 
	 * @throws Exception 
	 */
	public void init() throws Exception {
		System.out.println("[Renderer] Initializing renderer... ");
		shaderProgram = new ShaderProgram();
		
		System.out.println("[Renderer] Creating vertex shader... ");
		shaderProgram.createVertexShader(FileUtilities.loadResource("/shaders/vertexShader.vs"));
		
		System.out.println("[Renderer] Creating fragment shader... ");
		shaderProgram.createFragmentShader(FileUtilities.loadResource("/shaders/fragmentShader.fs"));
		
		//shaderProgram.defineMappings();
		
		System.out.println("[Renderer] Linking shaderProgram... ");
		shaderProgram.link();
		
		System.out.println("[Renderer]: Finding uniform variable locations...");
		findUniformLocations();
	}
	
	/**
	 * This method clears the background colour of the screen
	 */
	public void clear() {
		glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	
	/**
	 * This method handles window resize events and draws the specified Mesh
	 * instance onto the window.
	 * 
	 * @param window The window object
	 * @param mesh An instance of Mesh class which we want to render 
	 */
	
	int i = 0;
	public void render(Window window, GameEntity[] entities) {
		i++;
		
		clear();
		
		if (window.isResized()) {
			glViewport(0, 0, window.getWidth(), window.getHeight());
			window.setResized(false);
		}

		shaderProgram.bind();
		
		//update projection matrix
		Matrix4f projectionMatrix = transformation.getProjectionMatrix(FOVY, window.getWidth(), window.getHeight(), zNear, zFar);
		shaderProgram.setUniformMatrix("projectionMatrix", projectionMatrix);
		
		if (i == 80) {
			System.out.println("\nProjection matrix looks like: \n" + projectionMatrix.toString());
			System.out.println("\nwindow width: " + window.getWidth() + " | window height: " + window.getHeight());
			System.out.println("Entity position: " + entities[0].getPosition().toString());
			projectionMatrix.displayPerspectiveMatProperties();
			i = 0;
		}
		
		//render each game item
		for(GameEntity entity : entities) {
			Matrix4f translationMat = transformation.getTranslationMatrix(entity.getPosition());
			shaderProgram.setUniformMatrix("translationMatrix", translationMat);
			entity.getMesh().render();
		}

		shaderProgram.unbind();
	}
	
	/**
	 * This method removes the active shader program from memory
	 */
	public void cleanup() {
		if (shaderProgram != null) {
			shaderProgram.cleanup();
		}
	}
	
	private void runAssertions() {
			System.out.println("Name of the active program object: " + glGetInteger(GL_CURRENT_PROGRAM));		
	}
	
	/**
	 * This method defines the location of the uniform variables in shader code.
	 * @throws Exception
	 */
	private void findUniformLocations() throws Exception {
		shaderProgram.createUniform("translationMatrix");
		shaderProgram.createUniform("projectionMatrix");
	}

	
	

}
