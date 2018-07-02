package models;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import math.Vector3f;
import renderEngine.Texture;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import utils.ArrayUtils;
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
	private int textureVboID;
	private int normalsVboID;
	private int colourVboID;
	private int vertexCount;
	
	private FloatBuffer vPosBuffer;
	private FloatBuffer textureBuffer;
	private FloatBuffer normalsBuffer;
	private FloatBuffer colourBuffer;
	private IntBuffer indicesBuffer;
	
	private renderEngine.Texture texture;
	private Vector3f DEFAULT_COLOUR = new Vector3f(0.93f, 0.05f, 0.77f);
	private Vector3f colour;
	
	ArrayList<Integer> vbos;
	ArrayList<Integer> vaos;
	ArrayList<renderEngine.Texture> textures;
	
	/**
	 * This constructor creates a renderable object (instance of Mesh with its texture) out of input parameters by storing them
	 * in the vao of that Mesh instance
	 * @param vPos A float array of vertex positional vector components
	 * @param indices The indices to tell OpenGL how to connect the vertices
	 * @param texCoords Texture coordinates (used for texture mapping)
	 * @param normals A float array of vertex normal vector components
	 * @param texture A Texture object
	 */
	public Mesh(float[] vPos, int[] indices, float[] texCoords, float[] normals, renderEngine.Texture texture) {
		System.out.println("[Mesh.Mesh]: Creating a new textured Mesh instance... ");
		
		vPosBuffer = null;
		textureBuffer = null;
		indicesBuffer = null;
		
		try {
			this.texture = texture;
		    vertexCount = indices.length;
		    colour = DEFAULT_COLOUR;
		    
		    vbos = new ArrayList<>();
		    vaos = new ArrayList<>();
		    textures = new ArrayList<>();
			
		    System.out.println("[Mesh] Creating and binding the vao (vaoID)");
			vaoID = glGenVertexArrays();
			vaos.add(vaoID);
			glBindVertexArray(vaoID);
			
			setupVerticesVbo(vPos);
			
			setupIndicesBuffer(indices);
			
			setupTextureVbo(texCoords);
			
			setupNormalsVbo(normals);
			
			textures.add(texture);
			
			glBindBuffer(GL_ARRAY_BUFFER, 0);
			glBindVertexArray(0);
		} finally {
			System.out.println("[Mesh.Mesh]: Textured Mesh instance has been created successfully... ");
			
			System.out.println("[Mesh.Mesh]: Displaying Mesh information: ");
			System.out.println("[Mesh.Mesh]:  Vertices array: " + ArrayUtils.getFloatArray(vPos));
			System.out.println("[Mesh.Mesh]:  Indices array: " + ArrayUtils.getIntArray(indices));
			System.out.println("[Mesh.Mesh]:  Texture coordinates: " + ArrayUtils.getFloatArray(texCoords));
		}
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
		vPosBuffer = BufferUtilities.storeDataInFloatBuffer(vertices);
		
		System.out.println("   - [Mesh] Binding verticesVboID to GL_ARRAY_BUFFER...");
		glBindBuffer(GL_ARRAY_BUFFER, verticesVboID);
		
		System.out.println("   - [Mesh] Buffering data of verticesBuffer to GL_ARRAY_BUFFER");
		glBufferData(GL_ARRAY_BUFFER, vPosBuffer, GL_STATIC_DRAW);
		
		System.out.println("   - [Mesh] Sending verticesVboID to attribute list index 0 of the active vao...");
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
	}
	
	/**
	 * This methods loads up the indices buffer and binds it to the vao
	 * 
	 * @param indices - an array of indices to be loaded into the indices vbo 
	 */
	private void setupIndicesBuffer(int[] indices)  {
		System.out.println("[Mesh] Binding indices to a vbo... ");
		
		System.out.println("   - [Mesh] Creating vbo (indicesVboID)...");
		indicesVboID = glGenBuffers();
		vbos.add(indicesVboID);
		
		System.out.println("   - [Mesh] Binding indicesVboID to GL_ELEMENT_ARRAY_BUFFER...");
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indicesVboID);
		
		System.out.println("   - [Mesh] Creating an IntBuffer to store the array of indices (indicesBuffer)...");
		indicesBuffer = BufferUtilities.storeDataInIntBuffer(indices);
		
		System.out.println("   - [Mesh] Buffering data from indicesBuffer to GL_ELEMENT_ARRAY_TARGET...");
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
	}
	
	/**
	 * This method sets up the texture vbo for a mesh object (buffers data to it and assigns it to attribute list
	 * index 1 of the vao)
	 * 
	 * @param colours - an array of colours of the vertices of a model
	 */
	private void setupTextureVbo(float[] textures) {
		System.out.println("[Mesh] Creating texture vbo (textureVboID)...");
		textureVboID = glGenBuffers();
		vbos.add(textureVboID);
		
		System.out.println("   - [Mesh] Creating texture buffer (textureBuffer)...");
		textureBuffer = BufferUtilities.storeDataInFloatBuffer(textures);
		
		System.out.println("   - [Mesh] Binding textureVboID to GL_ARRAY_BUFER...");
		glBindBuffer(GL_ARRAY_BUFFER, textureVboID);
		
		System.out.println("   - [Mesh] Buffering data from textureBuffer to GL_ARRAY_BUFFER...");
		glBufferData(GL_ARRAY_BUFFER, textureBuffer, GL_STATIC_DRAW);
		
		System.out.println("   - [Mesh] Sending texture vbo to index 1 of the active vao...");
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
	}
	
	/**
	 * This method stores normal vectors of a model as a vbo at index 2 of the model vao
	 * @param normals
	 */
	private void setupNormalsVbo(float[] normals) {
		normalsVboID = glGenBuffers();
		vbos.add(normalsVboID);
		normalsBuffer = BufferUtilities.storeDataInFloatBuffer(normals);
		glBindBuffer(GL_ARRAY_BUFFER, normalsVboID);
		glBufferData(GL_ARRAY_BUFFER, normalsBuffer, GL_STATIC_DRAW);
		glVertexAttribPointer(2, 3, GL_FLOAT, false, 0, 0);
		
	}
	
	/*
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
	*/
	
	/**
	 * Renders a Mesh with enable attribute lists 0 (vertices), 1 (texture coords) and 2
	 * (normals) 
	 */
	public void render() {
		if (texture != null) {
			glActiveTexture(GL_TEXTURE0);
			//bind the texture
			glBindTexture(GL_TEXTURE_2D, texture.getId());
		}
		
		glBindVertexArray(getVaoID());
		
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, getIndicesVboID());

		glDrawElements(GL_TRIANGLES, getVertexCount(), GL_UNSIGNED_INT, 0);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		
		glDisableVertexAttribArray(2);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(0);
		
		glBindVertexArray(0);
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	/**
	 * Removes the mesh instance from memory
	 */
	public void cleanUp() {
		System.out.println("[Mesh.cleanUp]: Cleaning up the mesh object...");
		glDisableVertexAttribArray(0);
		
		System.out.println("[Mesh.cleanUp]: Size of textures: " + textures.size());
		
		System.out.println("[Mesh.cleanUp]: Cleaning up textures...");
		for(Texture texture : textures) {
			texture.cleanup();
		}
		
		if (texture != null) {
			texture.cleanup();
		}
		
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
	
	public void setTexture(Texture texture) {
		this.texture = texture;
	}
	
	public Texture getTexture() {
		return texture;
	}
	
	public boolean isTextured() {
		return this.texture != null;
	}
	
	public void setColour(float x, float y, float z) {
		Vector3f newColour = new Vector3f(x, y, z);
		colour = newColour;
	}
	
	public Vector3f getColour() {
		return colour;
	}
	

	
	

}