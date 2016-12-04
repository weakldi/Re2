package engine.core;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;



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
		this.data = data;
		bind(0);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexImage2D(GL_TEXTURE_2D, 0, data.getInternalFormat(), data.getWidth(), data.getHeight(), 0, data.getFormat(), GL_UNSIGNED_BYTE, data.getData());
		unbind();
	}
	
	public TextureData getData() {
		return data;
	}


	public void setData(TextureData data) {
		loadData(data);
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


	
	
	
}
