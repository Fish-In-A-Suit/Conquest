package renderEngine;

import static org.lwjgl.opengl.GL11.*;

/**
 * 
 * @author Aljoša
 * 
 * This class deals with rendering to the window
 */

public class Renderer {

	public void render() {

		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		//glfwSwapBuffers(window.windowHandle); NOT CORRECT!
	}

}
