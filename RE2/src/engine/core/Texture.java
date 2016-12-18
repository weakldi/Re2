package engine.core;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL21.*;
import static org.lwjgl.opengl.GL31.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL32.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL14.*;


import java.nio.ByteBuffer;



public class Texture {
	protected int textureID;
	protected TextureData data;
	
	public Texture(){
		textureID = genTextureID();
	}
	
	
	/**
	 * @return the textureID
	 */
	public int getTextureID() {
		return textureID;
	}

	public void loadData(TextureData data){
		bind(0);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexImage2D(GL_TEXTURE_2D, 0, data.getInternalFormat(), data.getWidth(), data.getHeight(), 0, data.getFormat(), data.getDataType(), data.getData());
		unbind();
	}
	
	private int genTextureID(){
		return glGenTextures();
	}
	
	public void bind(int samplerSlot){
		glActiveTexture(GL_TEXTURE0+samplerSlot);
		glBindTexture(GL_TEXTURE_2D, textureID);
	}
	
	public void unbind(){
		glBindTexture(GL_TEXTURE_2D, GL_NONE);
	}
	
	private static Texture missing = null;
	public static Texture getMissing(){
		if(missing==null){
			missing = new Texture();
			ByteBuffer buffer = ByteBuffer.allocate(16);
			buffer.	put(new byte[]{0,0,0,1}).
					put(new byte[]{1,0,1,1}).
					put(new byte[]{1,0,1,1}).
					put(new byte[]{0,0,0,1});
			buffer.flip();
			missing.bind(0);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
	        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
	        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA,2, 2, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
			missing.unbind();
			
		}
		return missing;
	}
	
	
	
}
