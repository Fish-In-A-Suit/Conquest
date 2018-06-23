package loaders;

/**
 * This class is a storage for data representing a 3D model. This includes a float array of vertex
 * positional vector components (VPVs), float array of texture coordinates, float array of normal vector components
 * and an int array of indices.
 * @author Aljoša
 *
 */
public class ModelData {
	private float[] vertices;
	private float[] textureCoords;
	private float[] normals;
	private int[] indices;
	//private float furthestPoint;
	
	public ModelData(float[] vertices, float[] textureCoords, float[] normals, int[] indices) {
		this.vertices = vertices;
		this.textureCoords = textureCoords;
		this.normals = normals;
		this.indices = indices;
		//this.furthestPoint = furthestPoint;
	}
	
	public float[] getVertices() {
		return vertices;
	}
	
	public float[] getTextureCoordinates() {
		return textureCoords;
	}
	
	public float[] getNormals() {
		return normals;
	}
	
	public int[] getIndices() {
		return indices;
	}
}
