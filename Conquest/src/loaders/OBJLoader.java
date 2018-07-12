package loaders;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import math.Vector2f;
import math.Vector3f;
import models.GameEntity;

public class OBJLoader {
	public static int lineCount;
	/**
	 * This method loads a model represented by a wavefront .obj file from resources/models using
	 * the specified name of the file (without the .obj extension) and a full path to the texture used
	 * by the model. It passes the information (vertex positions, texture coordinates, indices)
	 * obtained from the .obj file to the GameEntity constructor.
	 * @param fileName The name of the .obj file in resources/models/ without extension
	 * @param textureName The name of the texture file in resources/textures/ without extension
	 * @return
	 * @throws Exception
	 */
	public static GameEntity loadObjModel(String fileName, String textureName) throws Exception {
	    double start = System.nanoTime();
	    lineCount = 0;
	    String texturePath = "resources/textures/" + textureName+ ".png";
	    
		List<Vertex> vertices = null;
		List<Vector2f> textures = null;
		List<Vector3f> normals = null;
		List<Integer> indices = null;
		
		String line;
		
		float[] vertexPosArray = null;
		float[] texturesArray = null;
		float[] normalsArray = null;
		int[] indicesArray = null;
	    		
		try {
			FileReader fr = new FileReader(new File("resources/models/" + fileName + ".obj"));
			BufferedReader br = new BufferedReader(fr);
			vertices = new ArrayList<>();
			textures = new ArrayList<>();
			normals = new ArrayList<>();
			indices = new ArrayList<>();
			
			//reads lines starting with v, vt or vn and stores inside appropriate lists
			while((line = br.readLine()) != null) {	
				lineCount++;
				if (!line.equals("") || !line.startsWith("#")) {
					String[] splitLine = line.split(" ");
					
					switch(splitLine[0]) {
					case "v":
						Vector3f vertexPos = new Vector3f(Float.parseFloat(splitLine[1]), Float.parseFloat(splitLine[2]), Float.parseFloat(splitLine[3]));
						Vertex newVertex = new Vertex(vertices.size(), vertexPos); //vertices.size() equals to how much there are currently elements in vertices --> index of the vertex
						vertices.add(newVertex);
						break;
					case "vt":
						Vector2f texture = new Vector2f(Float.parseFloat(splitLine[1]), Float.parseFloat(splitLine[2]));
	    				textures.add(texture);
	    				break;
					case "vn":
						Vector3f normal = new Vector3f(Float.parseFloat(splitLine[1]), Float.parseFloat(splitLine[2]), Float.parseFloat(splitLine[3]));
	    				normals.add(normal);
	    				break;
					case "f":
						String[] vertex1 = splitLine[1].split("/");
						String[] vertex2 = splitLine[2].split("/");
						String[] vertex3 = splitLine[3].split("/");
						
						processVertex(vertex1, vertices, indices);
						processVertex(vertex2, vertices, indices);
						processVertex(vertex3, vertices, indices);
					}
				}
			}
			br.close();
		} catch (IOException e) {
			System.err.println("[OBJLoader.loadObjModel]: Error reading file: " + fileName);
		}
		removeUnusedVertices(vertices);
			
		vertexPosArray = new float[vertices.size() * 3];
		texturesArray = new float[vertices.size() * 2];
		normalsArray = new float[vertices.size() * 3];
		
		convertDataToArrays(vertices, textures, normals, vertexPosArray, texturesArray, normalsArray);
		indicesArray = convertIndicesListToArray(indices);
		
		ModelData data = new ModelData(vertexPosArray, texturesArray, normalsArray, indicesArray);
		
		double end = System.nanoTime();
		double delta = (end - start) / 1000_000;
		System.out.println("It took " + delta + " milliseconds to load model: " + fileName);
		
		return new GameEntity(data, texturePath);
		}

    private static void processVertex(String[] vertexData, List<Vertex> vertices, List<Integer> indices) {
        int index = Integer.parseInt(vertexData[0]) - 1;
        Vertex currentVertex = vertices.get(index);
        
        int textureIndex;
        if (!vertexData[1].equals("")) {
        	textureIndex = Integer.parseInt(vertexData[1]) - 1;
        } else {
        	//System.out.println("Found a line that contains /: " + lineCount + " " + vertexData[1]);
        	textureIndex = -1;
        }
     
        //int textureIndex = Integer.parseInt(vertexData[1]) - 1;
        
        int normalIndex = Integer.parseInt(vertexData[2]) - 1;
        if (!currentVertex.isSet()) {
            currentVertex.setTextureIndex(textureIndex);
            currentVertex.setNormalIndex(normalIndex);
            indices.add(index);
        } else {
            dealWithAlreadyProcessedVertex(currentVertex, textureIndex, normalIndex, indices,
                    vertices);
        }
    }
 
    private static int[] convertIndicesListToArray(List<Integer> indices) {
        int[] indicesArray = new int[indices.size()];
        for (int i = 0; i < indicesArray.length; i++) {
            indicesArray[i] = indices.get(i);
        }
        return indicesArray;
    }

    static int numCalls = 0;
    private static void convertDataToArrays(List<Vertex> vertices, List<Vector2f> textures,
    		List<Vector3f> normals, float[] verticesArray, float[] texturesArray, float[] normalsArray) {
    	numCalls++;
    	//System.out.println("Call to method convertDataToArrays: " + numCalls);
    	
    	for (int i = 0; i < vertices.size(); i++) {
            Vertex currentVertex = vertices.get(i);
            //currentVertex.display();
            Vector3f position = currentVertex.getPosition();
            
            
            if (currentVertex.isTextured()) {
            	//System.out.println("Converting vertex " + currentVertex.getIndex());
            	//System.out.println("[OBJLoader.convertDataToArrays]: texture index of the vertex currently processed: " + currentVertex.getTextureIndex());
            	
            	Vector2f textureCoord = textures.get(currentVertex.getTextureIndex());
            	if(textureCoord == null) {
            		//System.out.println("[OBJLoader.convertDataToArrays]: texture coordinates are not presentr!");
            	}
            	
            	texturesArray[i * 2] = textureCoord.x;
                texturesArray[i * 2 + 1] = 1 - textureCoord.y;
            } else {
            	//texturesArray = null; this works for non-textured models, but screws up textured models resulting in null pointer exception
            }
            Vector3f normalVector = normals.get(currentVertex.getNormalIndex());
            verticesArray[i * 3] = position.x;
            verticesArray[i * 3 + 1] = position.y;
            verticesArray[i * 3 + 2] = position.z;
            normalsArray[i * 3] = normalVector.x;
            normalsArray[i * 3 + 1] = normalVector.y;
            normalsArray[i * 3 + 2] = normalVector.z;
    	}
    }
 
    private static void dealWithAlreadyProcessedVertex(Vertex processedVertex, int newTextureIndex,
            int newNormalIndex, List<Integer> indices, List<Vertex> vertices) {
        if (processedVertex.hasSameTextureAndNormal(newTextureIndex, newNormalIndex)) {
            indices.add(processedVertex.getIndex());
        } else {
            Vertex anotherVertex = processedVertex.getDuplicateVertex();
            if (anotherVertex != null) {
                dealWithAlreadyProcessedVertex(anotherVertex, newTextureIndex, newNormalIndex,
                        indices, vertices);
            } else {
                Vertex duplicateVertex = new Vertex(vertices.size(), processedVertex.getPosition());
                
                duplicateVertex.setTextureIndex(newTextureIndex);
                duplicateVertex.setNormalIndex(newNormalIndex);
                processedVertex.setDuplicateVertex(duplicateVertex);
                vertices.add(duplicateVertex);
                indices.add(duplicateVertex.getIndex());
            }
 
        }
    }
     
    private static void removeUnusedVertices(List<Vertex> vertices){
        for(Vertex vertex:vertices){
            if(!vertex.isSet()){
                //vertex.setTextureIndex(0);
            	vertex.setTextureIndex(-1);
                vertex.setNormalIndex(0);
            }
        }
    }
 
}

