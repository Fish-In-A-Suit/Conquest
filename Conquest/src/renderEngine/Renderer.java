package renderEngine;

import static org.lwjgl.opengl.GL11.*;

import math.Matrix4f;
import shaders.ShaderProgram;
import utils.FileUtilities;
import math.Transformations;
import models.GameEntity;

/**
 * This class deals with rendering to the window
 * @author Aljoša
 * 
 */
public class Renderer {
	
	private ShaderProgram shaderProgram;
	private final Transformations transformation;
	private Matrix4f rotationMat;;
	
	private static double angleOfView = 60.0;
	private static final float FOVY = (float) Math.toRadians(angleOfView);
	private static final float zNear = 0.01f;
	private static final float zFar = 1000.0f;
	
	public Renderer() {
		transformation = new Transformations();
	}
	
	/**
	 * This method creates shaders and starts executing them
	 * 
	 * @throws Exception 
	 */
	public void init() throws Exception {
		System.out.println("[Renderer.init] Initializing renderer... ");
		shaderProgram = new ShaderProgram();
		
		System.out.println("[Renderer.init] Creating vertex shader... ");
		shaderProgram.createVertexShader(FileUtilities.loadResource("/shaders/vertexShader.vs"));
		
		System.out.println("[Renderer.init] Creating fragment shader... ");
		shaderProgram.createFragmentShader(FileUtilities.loadResource("/shaders/fragmentShader.fs"));
		
		System.out.println("[Renderer.init] Linking shaderProgram... ");
		shaderProgram.link();
		
		System.out.println("[Renderer.init]: Finding uniform variable locations...");
		defineUniformLocations();
	}
	
	/**
	 * This method handles window resize events and draws the specified GameEntity objects
	 * stored in entities on the screen. Furthermore, it updates the scene based on
	 * the fields of the Camera object specified (camera)
	 * 
	 * @param window The window object
	 * @param mesh An instance of Mesh class which we want to render 
	 * @param camera The Camera instance depending on which to update the scene
	 */
	
	int i = 0;
	public void render(Window window, GameEntity[] entities, Camera camera) {
		i++;
		
		clear();
		
		if (window.isResized()) {
			glViewport(0, 0, window.getWidth(), window.getHeight());
			window.setResized(false);
		}

		shaderProgram.bind();
		
		Matrix4f viewMatrix = transformation.getViewMatrix(camera);
		shaderProgram.setUniformMatrix("viewMatrix", viewMatrix);
		
		//update projection matrix
		Matrix4f projectionMatrix = transformation.getProjectionMatrix(FOVY, window.getWidth(), window.getHeight(), zNear, zFar);
		shaderProgram.setUniformMatrix("projectionMatrix", projectionMatrix);
		
		shaderProgram.setUniformInt("texture_sampler", 0);
		
		if (i == 80) {
			//System.out.println("\nProjection matrix looks like: \n" + projectionMatrix.toString());
			System.out.println("\nwindow width: " + window.getWidth() + " | window height: " + window.getHeight());
			System.out.println("Entity position: " + entities[0].getPosition().toString());
			System.out.println("Entity rotation: " + entities[0].getRotation().displayFriendly());
			System.out.println("Entity scale: " + entities[0].getScale());
			//System.out.println("Rotation matrix looks like: \n" + rotationMat.toString());
			//projectionMatrix.displayPerspectiveMatProperties();
			i = 0;
		}
		
		//render each game item
		for(GameEntity entity : entities) {
			Matrix4f translationMat = transformation.getTranslationMatrix(entity.getPosition());
			rotationMat = transformation.getRotationMatrix(entity.getRotation().x, entity.getRotation().y, entity.getRotation().z);
			Matrix4f scaleMat = transformation.getScaleMatrix(entity.getScale());
			Matrix4f modelMat = transformation.getModelMatrix(translationMat, rotationMat, scaleMat);
			shaderProgram.setUniformMatrix("modelMatrix", modelMat);
			entity.getMesh().render();
		}
		camera.resetRotation();

		shaderProgram.unbind();
	}
	
	/**
	 * This method clears the background colour of the screen
	 */
	public void clear() {
		glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	
	/**
	 * This method defines the location of the uniform variables in shader code.
	 * @throws Exception
	 */
	private void defineUniformLocations() throws Exception {
		System.out.println("[Renderer.defineUniformLocations]: Finding locations of the uniform variables... ");
		shaderProgram.createUniform("projectionMatrix");
		shaderProgram.createUniform("modelMatrix");
		shaderProgram.createUniform("viewMatrix");
		shaderProgram.createUniform("texture_sampler");
	}

	/**
	 * This method removes the active shader program from memory
	 */
	public void cleanup() {
		System.out.println("[Renderer.cleanup]: Cleaning up shader program...");
		if (shaderProgram != null) {
			shaderProgram.cleanup();
		}
	}
}
