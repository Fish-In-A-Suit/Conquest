package renderEngine;

import java.nio.FloatBuffer;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import org.lwjgl.BufferUtils;

/**
 * @author Aljoša
 * The main responsibility of this class is to collect 3D vertices, store them in
 * VRAM and provide a way to access them (used by Renderer to render them to the window)
 */
public class Mesh {
	private int vaoID;
	private int verticesVboID;
	private int vertexCount;
	private FloatBuffer verticesBuffer;
	
	public Mesh(float[] vertices) {
		// FloatBuffer verticesBuffer = null;

			vertexCount = vertices.length/3;
			
			vaoID = glGenVertexArrays();
			glBindVertexArray(vaoID);
			
			verticesVboID = glGenBuffers();
			
			verticesBuffer = BufferUtils.createFloatBuffer(vertices.length);
			verticesBuffer.put(vertices);
			checkVerticesBufferContent(vertices);
			verticesBuffer.flip();
			System.out.println("State of verticesBuffer after being flipped: " + verticesBuffer.toString());

			System.out.println("Number of vertices: " + vertexCount);
			
			glBindBuffer(GL_ARRAY_BUFFER, verticesVboID);
			glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);
			glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
			
			glBindBuffer(GL_ARRAY_BUFFER, 0);
			glBindVertexArray(0);

	}

	public int getVaoID() {
		return vaoID;
	}

	public int getVertexCount() {
		return vertexCount;
	}
	
	public int getVerticesVboID() {
		return verticesVboID;
	}
	
	public void checkVerticesBufferContent(float vertices[]) {
		int i = 0;
		System.out.print("The contents of vertices array: ");
		
		for (float vertex : vertices) {
			System.out.print("[" + ++i + "]" + verticesBuffer.get((int)vertex) + ", ");
		}
		System.out.println();
		
		System.out.println("State of verticesBuffer after putting data in but prior to flipping it: " + 
		                   verticesBuffer.toString());
		
		/*System.out.println("The contents of verticesBuffer:"
				+ "[0]: " + verticesBuffer.get(0) + ", "
				+ "[1]: " + verticesBuffer.get(1) + ", "
				+ "[2]: " + verticesBuffer.get(2) + ", "
				+ "[3]: " + verticesBuffer.get(3) + ", "
				+ "[4]: " + verticesBuffer.get(4) + ", "
				+ "[5]: " + verticesBuffer.get(5) + ", "
				+ "[6]: " + verticesBuffer.get(6) + ", "
				+ "[7]: " + verticesBuffer.get(7) + ", "
				+ "[8]: " + verticesBuffer.get(8) + ", "
				+ "[9]: " + verticesBuffer.get(9) + ", "
				+ "[10]: " + verticesBuffer.get(10) + ", "
				+ "[11]: " + verticesBuffer.get(11) + ", "
				);*/
		
		if(verticesBuffer.hasArray()) {
			System.out.println("verticesBuffer is backed by an accessible float array");
		} else {
			System.out.println("verticesBuffer isn't backed by an accessible float array!");
		}
	}
	
	public void cleanUp() {
		glDisableVertexAttribArray(0);
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glDeleteBuffers(verticesVboID);
		
		glBindVertexArray(0);
		glDeleteVertexArrays(vaoID);
	}

}
