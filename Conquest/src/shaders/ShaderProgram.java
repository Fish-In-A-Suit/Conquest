package shaders;

import static org.lwjgl.opengl.GL20.*;

public class ShaderProgram {
	private final int programID;
	private int vertexShaderID;
	private int fragmentShaderID;
	
	public ShaderProgram() throws Exception {
		programID = glCreateProgram();
		
		if (programID == 0) {
			throw new Exception("Couldn't create shader!");
		}
	} 
	
	public void createVertexShader(String shaderPath) throws Exception {
		vertexShaderID = createShader(shaderPath, GL_VERTEX_SHADER);
	}
	
	public void createFragmentShader(String shaderPath) throws Exception {
		fragmentShaderID = createShader(shaderPath, GL_FRAGMENT_SHADER);
	}
	
	protected int createShader(String shaderPath, int shaderType) throws Exception {
		int shaderID = glCreateShader(shaderType);
		
		if (shaderID == 0) {
			throw new Exception("Error creating shader. Type: " + shaderType);
		}
		
		glShaderSource(shaderID, shaderPath);
		glCompileShader(shaderID);
		
		if (glGetShaderi(shaderID, GL_COMPILE_STATUS) == 0) {
			throw new Exception("Error compiling shader code: " + glGetShaderInfoLog(shaderID, 1024));
		}
		
		glAttachShader(programID, shaderID);
		return shaderID;
	}
	
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
	
	public void bind() {
		glUseProgram(programID);
	}
	
	public void unbind() {
		glUseProgram(0);
	}
	
	public void cleanup() {
		unbind();
		if (programID != 0) {
			glDeleteProgram(programID);
		}
	}	
	
	public int getProgramID() {
		return programID;
	}
}