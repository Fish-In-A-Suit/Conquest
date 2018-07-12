package models;

import loaders.ModelData;
import math.Vector3f;
import models.Mesh;
import renderEngine.Texture;

/**
 * A class which has references for storing model mesh and texture, along with its
 * position, rotation, scale and state. This class represents a renderable entity, which can
 * be moved around in the game.
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
	
	/**
	 * Creates a new GameEntity out of data found in ModelData and a filepath that specifies a corresponding
	 * texture. This constructor is used by OBJLoader to load .obj model
	 * @param data A ModelData instance which holds an array of vertex positional vector components, vertex texture coordinates, vertex normal vector components and an array of indices
	 * @param texturePath A file path of a corrsponding texture
	 * @throws Exception
	 */
	public GameEntity(ModelData data, String texturePath) throws Exception {
		modelTexture = new renderEngine.Texture(texturePath);
		
		if (modelTexture.getId() == -1) {
			//doesn't exist
			modelTexture = null;
		}
		
		mesh = new Mesh(data.getVertices(), data.getIndices(), data.getTextureCoordinates(), data.getNormals(), modelTexture);
		setDefaultPRS();
	}
	
	public Vector3f getPosition() {
		return position;
	}
	
	//move the entity around
	public void updatePosition(float x, float y, float z) {
		position.x += x * 0.05f;
	    position.y += y * 0.05f;
		position.z += z * 0.05f;
	}
	
	//rotate the entity
	public void increaseRotation(float x, float y, float z) {
		rotation.x += x * 0.05f;
		rotation.y += y * 0.05f;
		rotation.z += z * 0.05f;
	}
	
	//increate the scale of an entity
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