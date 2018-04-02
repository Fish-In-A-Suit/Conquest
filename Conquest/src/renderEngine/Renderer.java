package renderEngine;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import shaders.ShaderProgram;
import utils.FileUtilities;

/**
 * 
 * @author Aljoša
 * 
 * This class deals with rendering to the window
 */
public class Renderer {
	
	private ShaderProgram shaderProgram;
	
	public Renderer() {
	}
	
	public void init() throws Exception {
		shaderProgram = new ShaderProgram();
		shaderProgram.createVertexShader(FileUtilities.loadResource("/shaders/vertexShader.vs"));
		shaderProgram.createFragmentShader(FileUtilities.loadResource("/shaders/fragmentShader.fs"));
		shaderProgram.link();
	}
	public void clear() {
		glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	
	public void render(Window window, Mesh mesh) {
		clear();
		
		if (window.isResized()) {
			glViewport(0, 0, window.getWidth(), window.getHeight());
			window.setResized(false);
		}

		shaderProgram.bind();

		glBindVertexArray(mesh.getVaoID());
		glEnableVertexAttribArray(0);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, mesh.getIndicesVboID());

		glDrawElements(GL_TRIANGLES, mesh.getVertexCount(), GL_UNSIGNED_INT, 0);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glDisableVertexAttribArray(0);
		glBindVertexArray(0);
		
		shaderProgram.unbind();
	}
	
	public void cleanup() {
		if (shaderProgram != null) {
			shaderProgram.cleanup();
		}
	}
	
	public void runAssertions() {
		System.out.println("ProgramID:" + shaderProgram.getProgramID());		
	}

}
