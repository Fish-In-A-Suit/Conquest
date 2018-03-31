package renderEngine;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import utils.BufferUtilities;

/**
 * @author Aljoša
 * The main responsibility of this class is to collect 3D vertices, store them in
 * VRAM and provide a way to access them (used by Renderer to render them to the window)
 */
public class Mesh {
	private int vaoID;
	private int verticesVboID;
	private int indicesVboID;
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
			
			verticesBuffer = BufferUtilities.storeDataInFloatBuffer(vertices);
			checkVerticesBufferContent(vertices);

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
		indicesVboID = glGenBuffers();
		vbos.add(indicesVboID);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indicesVboID);
		
		IntBuffer indicesBuffer = BufferUtilities.storeDataInIntBuffer(indices);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
		
	}
	
	/**
	 * @return vaoID - the int reference to the vertex array object associated with a particular instance of Mesh
	 */
	public int getVaoID() {
		return vaoID;
	}
	
	/**
	 * @return vertexCount - the amount of vertices of a particular instance of Mesh
	 */
	public int getVertexCount() {
		return vertexCount;
	}
	
	/**
	 * @return verticesVboID - the int reference to the buffer object which stores the vertices of a
	 * particular mesh instance
	 */
	public int getVerticesVboID() {
		return verticesVboID;
	}
	
	/**
	 * @returnndicesVboID - the int reference to the  buffer object which stores the indices of a particular
	 * mesh instance
	 */
	public int getIndicesVboID() {
		return indicesVboID;
	}
	
	/**
	 * @param vertices
	 * 
	 * This is a debugging method for checking the contents of the verticesBuffer
	 */
	public void checkVerticesBufferContent(float vertices[]) {
		int i = 0;
		System.out.print("The contents of vertices array: ");
		
		for (float vertex : vertices) {
			System.out.print("[" + ++i + "]" + verticesBuffer.get((int)vertex) + ", ");
		}	
		System.out.println();
		System.out.println("Number of vertices: " + vertexCount);	
		
		System.out.println("State of verticesBuffer after putting data in but prior to flipping it: " + 
		                   verticesBuffer.toString());
	
		if(verticesBuffer.hasArray()) {
			System.out.println("verticesBuffer is backed by an accessible float array");
		} else {
			System.out.println("verticesBuffer isn't backed by an accessible float array!");
		}
	}
	
	/**
	 * Removes the mesh instance from memory
	 */
	public void cleanUp() {
		glDisableVertexAttribArray(0);
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glDeleteBuffers(verticesVboID);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glDeleteBuffers(indicesVboID);
		
		glBindVertexArray(0);
		glDeleteVertexArrays(vaoID);
	}

}
