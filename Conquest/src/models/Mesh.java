package models;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import utils.BufferUtilities;

/**
 * The main responsibility of this class is to collect 3D vertices, store them in
 * VRAM and provide a way to access them (used by Renderer to render them to the window)
 * 
 * @author Aljoša
 */
public class Mesh {
	private int vaoID;
	private int verticesVboID;
	private int indicesVboID;
	private int colourVboID;
	private int vertexCount;
	
	private FloatBuffer verticesBuffer;
	private FloatBuffer colourBuffer;
	
	ArrayList<Integer> vbos = new ArrayList<>();
	ArrayList<Integer> vaos = new ArrayList<>();
	
	/**
	 * This constructor creates a renderable object (instance of Mesh) out of input parameters by storing them
	 * in the vao of that Mesh instance
	 * @param vertices The vertex positions of a model
	 * @param indices The indices to tell OpenGL how to connect the vertices
	 * @param colours The colours of the vertices
	 */
	public Mesh(float[] vertices, int[] indices, float[] colours) {

			//vertexCount = vertices.length/3;
		    vertexCount = indices.length;
			
		    System.out.println("[Mesh] Creating and binding the vao (vaoID)");
			vaoID = glGenVertexArrays();
			vaos.add(vaoID);
			glBindVertexArray(vaoID);
			
			bindIndicesBuffer(indices);
			
			setupColourVbo(colours);
			
			setupVerticesVbo(vertices);

			glBindBuffer(GL_ARRAY_BUFFER, 0);
			glBindVertexArray(0);
	}
	
	/**
	 * This methods loads up the indices buffer and binds it to the vao
	 * 
	 * @param indices - an array of indices to be loaded into the indices vbo 
	 */
	private void bindIndicesBuffer(int[] indices)  {
		System.out.println("[Mesh] Binding indices to a vbo... ");
		
		System.out.println("   - [Mesh] Creating vbo (indicesVboID)...");
		indicesVboID = glGenBuffers();
		vbos.add(indicesVboID);
		
		System.out.println("   - [Mesh] Binding indicesVboID to GL_ELEMENT_ARRAY_BUFFER...");
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indicesVboID);
		
		System.out.println("   - [Mesh] Creating an IntBuffer to store the array of indices (indicesBuffer)...");
		IntBuffer indicesBuffer = BufferUtilities.storeDataInIntBuffer(indices);
		
		System.out.println("   - [Mesh] Buffering data from indicesBuffer to GL_ELEMENT_ARRAY_TARGET...");
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
	}
	
	/**
	 * This is a debugging method for checking the contents of the verticesBuffer
	 * 
	 * @param vertices
	 */
	public void checkVertices(float vertices[]) {
		int i = 0;
		System.out.print("      - [Mesh] The contents of vertices array: ");
		
		for (float vertex : vertices) {
			System.out.print("[" + ++i + "]" + verticesBuffer.get((int)vertex) + ", ");
		}	

		System.out.println("\n" + "      - [Mesh] Number of vertices: " + vertexCount);	
		
		System.out.println("      - [Mesh] State of verticesBuffer after putting data in but prior to flipping it: " + 
		                   verticesBuffer.toString());
	
		if(verticesBuffer.hasArray()) {
			System.out.println("      - [Mesh] verticesBuffer is backed by an accessible float array");
		} else {
			System.out.println("      - [Mesh] verticesBuffer isn't backed by an accessible float array!");
		}
	}
	
	/**
	 * Removes the mesh instance from memory
	 */
	public void cleanUp() {
		System.out.println("[Mesh] cleaning up the mesh object");
		glDisableVertexAttribArray(0);
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glDeleteBuffers(verticesVboID);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glDeleteBuffers(indicesVboID);
		
		glBindVertexArray(0);
		glDeleteVertexArrays(vaoID);
	}
	
	/**
	 * @return  indicesVboID - the int reference to the  buffer object which stores the indices of a particular
	 * mesh instance
	 */
	public int getIndicesVboID() {
		return indicesVboID;
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
	
	public void render() {
		glBindVertexArray(getVaoID());
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, getIndicesVboID());

		glDrawElements(GL_TRIANGLES, getVertexCount(), GL_UNSIGNED_INT, 0);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glDisableVertexAttribArray(0);
		glBindVertexArray(0);
	}
	
	/**
	 * This method sets up the colour vbo for a mesh object (buffers data to it and assigns it to attribute list
	 * index 1 of the vao)
	 * 
	 * @param colours - an array of colours of the vertices of a model
	 */
	private void setupColourVbo(float[] colours) {
		System.out.println("[Mesh] Creating colour vbo (colourVboID)...");
		colourVboID = glGenBuffers();
		vbos.add(colourVboID);
		
		System.out.println("   - [Mesh] Creating colour buffer (colourBuffer)...");
		colourBuffer = BufferUtilities.storeDataInFloatBuffer(colours);
		
		System.out.println("   - [Mesh] Binding colourVboID to GL_ARRAY_BUFER...");
		glBindBuffer(GL_ARRAY_BUFFER, colourVboID);
		
		System.out.println("   - [Mesh] Buffering data from colourBuffer to GL_ARRAY_BUFFER...");
		glBufferData(GL_ARRAY_BUFFER, colourBuffer, GL_STATIC_DRAW);
		
		System.out.println("   - [Mesh] Sending colour vbo to index 1 of the active vao...");
		glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
	}
	
	/**
	 * This method creates a VBO to store the vertices of a model (Mesh instance) and stores that VBO inside
	 * the attribute list at index 0 of the VAO representing the model (Mesh instance)
	 * @param vertices
	 */
	private void setupVerticesVbo(float[] vertices) {
		System.out.println("[Mesh] Creating vertices vbo (verticesVboID)...");
		
		System.out.println("   - [Mesh] creating vertices vbo (verticesVboID) and verticers buffer (verticesBuffer)..."); 
		verticesVboID = glGenBuffers();
		vbos.add(verticesVboID);
		verticesBuffer = BufferUtilities.storeDataInFloatBuffer(vertices);
		
		System.out.println("   - [Mesh] Checking vertices");
		checkVertices(vertices);
		
		System.out.println("   - [Mesh] Binding verticesVboID to GL_ARRAY_BUFFER...");
		glBindBuffer(GL_ARRAY_BUFFER, verticesVboID);
		
		System.out.println("   - [Mesh] Buffering data of verticesBuffer to GL_ARRAY_BUFFER");
		glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);
		
		System.out.println("   - [Mesh] Sending verticesVboID to attribute list index 0 of the active vao...");
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
	}

}