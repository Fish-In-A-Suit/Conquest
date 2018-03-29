package renderEngine;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

/**
 * 
 * @author Aljoša
 * 
 * This class deals with rendering to the window
 */
public class Renderer {
	public void clear() {
		glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	
	public void render(Window window, Mesh mesh) {
		if (window.isResized()) {
			glViewport(0, 0, window.getWidth(), window.getHeight());
			window.setResized(false);
		}
		
		//System.out.println("vaoID of mesh prior to binding it in the render(...) method: " + mesh.getVaoID());
		
		glBindVertexArray(mesh.getVaoID());
		glEnableVertexAttribArray(0);

                //System.out.println("vertexCount of mesh prior to glDrawArrays: " + mesh.getVertexCount());
		
		glDrawArrays(GL_TRIANGLES, 0, mesh.getVertexCount());
		glDisableVertexAttribArray(0);
		glBindVertexArray(0);
	}

}
