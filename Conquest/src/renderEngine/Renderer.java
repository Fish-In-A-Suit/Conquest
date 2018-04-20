package renderEngine;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import shaders.ShaderProgram;
import utils.FileUtilities;

/**
 * This class deals with rendering to the window
 * @author Aljoša
 * 
 */
public class Renderer {
	
	private ShaderProgram shaderProgram;
	
	public Renderer() {
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
	public void render(Window window, Mesh mesh) {
		clear();
		
		if (window.isResized()) {
			glViewport(0, 0, window.getWidth(), window.getHeight());
			window.setResized(false);
		}

		shaderProgram.bind();

		glBindVertexArray(mesh.getVaoID());
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, mesh.getIndicesVboID());

		glDrawElements(GL_TRIANGLES, mesh.getVertexCount(), GL_UNSIGNED_INT, 0);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glDisableVertexAttribArray(0);
		glBindVertexArray(0);
		
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

}
