package models;

import loaders.ModelData;
import math.Vector3f;
import models.Mesh;
import renderEngine.Texture;

/**
 * A class which has references for storing model position, rotation, scale and state
 * @author Aljoša
 *
 */
public class GameEntity {
	private final Mesh mesh;
	private Texture modelTexture;
	
	private Vector3f position;
	private float scale;
	private Vector3f rotation;
	
	/**
	 * Creates a new textured GameEntity
	 * @param vPositions The vertex coordinates of a model
	 * @param indices The indices of a model (in which order should the vertices be bound by OpenGL?)
	 * @param textureCoordinates The coordinates of a texture (which texture coordinate should be applied to which vertex?)
	 * @param texturePath The path of the texture 
	 * @throws Exception
	 */
	public GameEntity(float[] vPositions, int[] indices, float[] textureCoordinates, float[] normals, String texturePath) throws Exception{
		System.out.println("[GameEntity.GameEntity]: Creating a new model texture...");
		modelTexture = new renderEngine.Texture(texturePath);
		System.out.println("[GameEntity.GameEntity]: Creating new mesh based on parameters... ");
		mesh = new Mesh(vPositions, indices, textureCoordinates, normals, modelTexture);
		
		System.out.println("[GameEntity.GameEntity]: Initializing position, scale and rotation instance fields... ");
		position = new Vector3f(0, 0, 0);
		scale = 1;
		rotation = new Vector3f(0, 0, 0);
	}
	
	public GameEntity(ModelData data, String texturePath) throws Exception {
		modelTexture = new renderEngine.Texture(texturePath);
		mesh = new Mesh(data.getVertices(), data.getIndices(), data.getTextureCoordinates(), data.getNormals(), modelTexture);
		setDefaultPRS();
	}
	
	public GameEntity(float[] vPos, float[] texCoords, float[] normals, int[] indices) {
		mesh = new Mesh(vPos, texCoords, normals, indices);
		modelTexture = mesh.getTexture();
		
		position = new Vector3f(0, 0, 0);
		scale = 1;
		rotation = new Vector3f(0, 0, 0);
	}
	
	public GameEntity(float[] vPos, float[] texCoords, float[] normals, int[] indices, String texturePath) {
		mesh = new Mesh(vPos, texCoords, normals, indices);
		modelTexture = null;
		try {
			modelTexture = new Texture(texturePath);
		} catch (Exception e) {
			System.err.println("[Game.Game]: Failed to create modelTexture!");
			e.printStackTrace();
		}
		
		position = new Vector3f(0, 0, 0);
		scale = 1;
		rotation = new Vector3f(0, 0, 0);
	}
	
	/**
	 * Creates a texture-less GameEntity
	 * @param vPositions The vertex coordinates of a model
	 * @param indices The indices of a model (in which order should the vertices be bound by OpenGL?)
	 * @param colours The colours of the vertices
	 */
	public GameEntity(float[] vPositions, int[] indices, float[] colours) {
		modelTexture = null;
		mesh = new Mesh(vPositions, indices, colours);
		
		position = new Vector3f(0, 0, 0);
		scale = 1;
		rotation = new Vector3f(0, 0, 0);
		
	}
	
	public Vector3f getPosition() {
		return position;
	}
	
	/**
	 * This method moves an entity around in the world
	 * @param x
	 * @param y
	 * @param z
	 */
	public void updatePosition(float x, float y, float z) {
		position.x += x * 0.05f;
	    position.y += y * 0.05f;
		position.z += z * 0.05f;
	}
	
	public void increaseRotation(float x, float y, float z) {
		rotation.x += x * 0.05f;
		rotation.y += y * 0.05f;
		rotation.z += z * 0.05f;
	}
	
	public void increaseScale(float scale) {
		this.scale += scale * 0.01f;
	}
 
	public float getScale() {
		return scale;
	}
	
	public void setScale(float scale) {
		this.scale = scale;
	}
	
	public Vector3f getRotation() {
		return rotation;
	}
	
	public void setRotation(float x, float y, float z) {
		rotation.x = x;
		rotation.y = y;
		rotation.z = z;
	}
	
	public Mesh getMesh() {
		return mesh;
	}
	
	public void increaseAllFun(float tx, float ty, float tz, float scale, float rx, float rz, float ry) {
		//position.x += tx * 0.05f;
	    //position.y += ty * 0.05f;
		//position.z += tz * 0.05f;
		
		if (this.scale < 2.0f) {
			this.scale += scale * 0.02f;
		} else {
			this.scale = -this.scale;
		}
		
		rotation.x += rx * 0.05f;
		rotation.y += ry * 0.05f;
		rotation.z += rz * 0.05f;
	}
	
	public void displayInformation() {
		System.out.println("[GameEntity.GameEntity]:  Displaying GameEntity information: ");
		if (mesh != null) {
			System.out.println("  - mesh exists");
		} else System.out.println("mesh DOESN'T EXIST!");
		
		if (modelTexture != null) {
			System.out.println("  - modelTexture exists");
			System.out.println("  - modelTexture id = " + modelTexture.getId());
		} else {
			System.out.println("  - modelTexture DOESN'T EXIST");
		}
	}
	
	//sets default position, rotation and scale
	private void setDefaultPRS() {
		position = new Vector3f(0, 0, 0);
		rotation = new Vector3f(0, 0, 0);
		scale = 1;
	}
}