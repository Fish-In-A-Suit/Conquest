package renderEngine;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;

/**
 * This class provides the functionality to load PNG textures to the OpenGL context
 * @author Aljoša
 *
 */
public class Texture {
	private final int id;
	
	/**
	 * This constructor creates a Texture instance out of the specified texture path and stores
	 * the handle for the VRAM-stored texture (along with all of its parameters) as id
	 * @param texturePath File path to the .png image
	 * @throws Exception
	 */
	public Texture(String texturePath) throws Exception {
		this(loadTexture(texturePath)); //call for Texture(int id) constructor
	}
	
	public Texture(int id) {
		this.id = id;
	}
	
	/**
	 * Reads the image specified with fileName into a ByteBuffer,
	 * defines texture parameters to be used and stores the texture
	 * (as a ByteBuffer) to the OpenGL context
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	private static int loadTexture(String fileName) throws Exception {
		System.out.println("[Texture.loadTexture]: Loading texture " + fileName + " to the OpenGL context");
        InputStream in = new FileInputStream(fileName);
		//InputStream in = Texture.class.getResourceAsStream(fileName);

        try {
		    //load texture contents into a byte buffer
		    PNGDecoder decoder = new PNGDecoder(in);
		    ByteBuffer buf = ByteBuffer.allocateDirect(decoder.getWidth()*decoder.getHeight()*4);
		    decoder.decode(buf, decoder.getWidth()*4, Format.RGBA);
		    buf.flip();

			//create a new OpenGL texture
			int textureId = glGenTextures();
			//make the texture active
			glBindTexture(GL_TEXTURE_2D, textureId);
			
			glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
			
			//setup filtering - how OpenGL interpolates pixels when scaling up or down
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
			
			//setup wrap mode - how OpenGL handles pixels outside of the expected range
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
			
			//upload the texture data
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buf);
			return textureId;
		} finally {
			in.close();
		}
	}
	
	//not currently used
	public void bind() {
		glBindTexture(GL_TEXTURE_2D, id);
	}
	
	public int getId() {
		return id;
	}

	public void cleanup() {
		glDeleteTextures(id);
	}
}
