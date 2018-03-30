package renderEngine;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

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
	
	ArrayList<Integer> vbos = new ArrayList<>();
	ArrayList<Integer> vaos = new ArrayList<>();
	
	public Mesh(float[] vertices, int[] indices) {
		// FloatBuffer verticesBuffer = null;

			//vertexCount = vertices.length/3;
		    vertexCount = indices.length;
			
			vaoID = glGenVertexArrays();
			vaos.add(vaoID);
			glBindVertexArray(vaoID);
			
			bindIndicesBuffer(indices);
			
			verticesVboID = glGenBuffers();
			vbos.add(verticesVboID);
			
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
	
	/**
	 * @param indices - an array of indices to be loaded into the indices vbo
	 * 
	 * This methods loads up the indices buffer and binds it to the vao
	 */
	private void bindIndicesBuffer(int[] indices)  {
		int indicesVboID = glGenBuffers();
		vbos.add(indicesVboID);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indicesVboID);
		
		IntBuffer indicesBuffer = storeDataInIntBuffer(indices);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
	}
	/**
	 * @param data
	 * @return indicesBuffer
	 * 
	 * This method stores an array of indices into an IntBuffer
	 */
	private IntBuffer storeDataInIntBuffer(int[] data) {
		IntBuffer indicesBuffer = BufferUtils.createIntBuffer(data.length);
		indicesBuffer.put(data);
		indicesBuffer.flip();
		return indicesBuffer;
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
