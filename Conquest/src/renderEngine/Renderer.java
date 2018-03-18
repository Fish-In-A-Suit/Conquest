package renderEngine;

import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

/**
 * 
 * @author Aljoša
 * 
 * This class deals with rendering to the window
 */

public class Renderer {

	private int vboID = glGenBuffers();
	
	//create a method to connect to the shader program here --> create the VAO and bind to it, create the VBO and bind to it.
	
	public void render() {
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		glfwSwapBuffers(Window.windowHandle);
	}

}
