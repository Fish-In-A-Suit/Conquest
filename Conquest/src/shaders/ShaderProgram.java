package shaders;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL40.*;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import math.Matrix4f;
import math.Vector3f;
import models.Light;
import utils.BufferUtilities;

public class ShaderProgram {
	private final int programID;
	private int vertexShaderID;
	private int fragmentShaderID;
	
	private final Map<String, Integer> uniforms;

    public ShaderProgram() throws Exception {
		programID = glCreateProgram();
		
		System.out.println("[ShaderProgram]: Creating new hashmap named uniforms...");
		uniforms = new HashMap();

		System.out.println("\n" + "[ShaderProgram] Starting ShaderProgram... ");
		System.out.println("[ShaderProgram] programID: " + programID);
		
		if (programID == 0) {
			throw new Exception("[ShaderProgram] Couldn't create shader! (class ShaderProgram)");
		} else {
			System.out.println("[ShaderProgram] shader program (programID) has been successfully created!"); 
		}
	} 
	
	/**
	 * This method creates (creates, compiles and attaches to the program) a vertex shader
	 * based on the specified path of its source code file
	 * 
	 * @param shaderPath - the shader path of the vertex shader file
	 * @throws Exception
	 */
	public void createVertexShader(String shaderPath) throws Exception {
		vertexShaderID = createShader(shaderPath, GL_VERTEX_SHADER);
	}
	
	/**
	 * This method creates (creates, compiles and attaches to the program) a fragment shader
	 * based on the specified path of its source code file
	 * 
	 * @param shaderPath - the path of the fragment shader file
	 * @throws Exception
	 */
	public void createFragmentShader(String shaderPath) throws Exception {
		fragmentShaderID = createShader(shaderPath, GL_FRAGMENT_SHADER);
	}
	
	/**
	 * @param shaderPath The path of the shader-associated file
	 * @param shaderType The type of a shader object
	 * @return shaderID The int reference to the modified shader object
	 * @throws Exception
	 * 
	 * This method creates, populates with source code, compiles the source code and
	 * attaches a shader object of particular type to the currently active program.
	 */
	protected int createShader(String shaderPath, int shaderType) throws Exception {
		int shaderID = glCreateShader(shaderType);
		System.out.println("[ShaderProgram] The ID of shader object" + shaderType + ": " + shaderID);
		
		if (shaderID == 0) {
			throw new Exception("[ShaderProgram] Error creating shader. Type: " + shaderType);
		}
		
		glShaderSource(shaderID, shaderPath);
		glCompileShader(shaderID);
		
		if (glGetShaderi(shaderID, GL_COMPILE_STATUS) == 1) {
			System.out.println("[ShaderProgram] " + glGetShaderi(shaderID, GL_SHADER_TYPE) + " has been successfully compiled!");
		} else {
			throw new Exception("[ShaderProgram]: Error compiling shader code for shader " + shaderPath + ": " + glGetShaderInfoLog(shaderID, 1024));
		}
		
		glAttachShader(programID, shaderID);
		return shaderID;
	}
	
	/**
	 * This method provides functionality for linking, possibly detaching shaders from and validating the program
	 * (programID) 
	 * 
	 * @throws Exception
	 */
	public void link() throws Exception {
		glLinkProgram(programID);
		if (glGetProgrami(programID, GL_LINK_STATUS) == 0) {
			throw new Exception("Error linking shader code: " + glGetProgramInfoLog(programID, 1024));
		}
		
		if (vertexShaderID != 0) {
			glDetachShader(programID, vertexShaderID);
		}
		
		if (fragmentShaderID != 0) {
			glDetachShader(programID, fragmentShaderID);
		}
		
		glValidateProgram(programID);
		if (glGetProgrami(programID, GL_VALIDATE_STATUS) == 0) {
			System.out.println("Warning validating shader code: " + glGetShaderInfoLog(programID, 1024));
		}
	}
	
	/**
	 * This defines the mapping between uniform locations and Java code and stores the obtained location in a HashMap (named uniforms).
	 * It is invoked inside Renderer class, once the shader program has been compiled. Also make sure that the variable
	 * has already been initialized before calling this method.
	 * 
	 * @param uniformName - the name of the uniform variable whose name is to be queried
	 * @throws Exception 
	 */
	public void createUniform(String uniformName) throws Exception {
		System.out.println("Finding a uniform location for: " + uniformName);
		int uniformLocation = glGetUniformLocation(programID, uniformName);
		System.out.println("The uniform location for " + uniformName + " is: " + uniformLocation);
		
		if (uniformLocation < 0) {
			throw new Exception("[ShaderProgram.createUniform]: Couldn't find uniform: " + uniformName);
		}
		System.out.println("[ShaderProgram.createUniform]: Storing " + uniformName + " in " + uniforms);
		uniforms.put(uniformName, uniformLocation);
	}
	
	/**
	 * This method sets the uniform values for 4x4 matrices. Use this method in the rendering method after the
	 * program has been bound.
	 * @param uniformName
	 * @param value
	 */
	public void setUniformMatrix(String uniformName, Matrix4f inMatrix) {
		FloatBuffer matrixBuffer = BufferUtilities.storeDataInFloatBuffer(inMatrix.mat4fToArray());
		glUniformMatrix4fv(uniforms.get(uniformName), false, matrixBuffer);	
	}
	
	/**
	 * Allows to set an integer value for a uniform
	 * @param uniformName
	 * @param value
	 */
	public void setUniformInt(String uniformName, int value) {
		glUniform1i(uniforms.get(uniformName), value);
	}
	
	public void setUniformVector(String uniformName, Vector3f vec) {
		glUniform3f(uniforms.get(uniformName), vec.x, vec.y, vec.z);
	}
	
	public void loadLight(Light light) {
		setUniformVector("lightPosition", light.getPosition());
		setUniformVector("lightColour", light.getColour());
	}
	
/*	
	public void defineMappings() {
		glBindAttribLocation(programID, 0, "position");
		glBindAttribLocation(programID, 0, "inColour");
		glBindFragDataLocation(programID, 0, 
	}
*/	
	/**
	 * Installs the program object specified by programID as part of current rendering state
	 */
	public void bind() {
		glUseProgram(programID);
	}
    
	/**
	 * Removes the current program object from the current rendering state
	 */
	public void unbind() {
		glUseProgram(0);
	}
	
	/**
	 * Deletes the current shader program specified by programID
	 */
	public void cleanup() {
		unbind();
		if (programID != 0) {
			glDeleteProgram(programID);
		}
	}	
	
	/**
	 * This method returns the int reference to the program object of a ShaderObject instance
	 * @return programID the int reference to a program object
	 */
	public int getProgramID() {
		return programID;
	}
}