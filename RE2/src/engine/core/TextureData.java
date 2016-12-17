package engine.core;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
/**
 * Diese Klasse speichert die Bildinformationen der Textur
 * 
 *
 */
public class TextureData {
	private int width;
	private int height;
	private ByteBuffer data;
	private int internalFormat;
	private int format;
	
	public TextureData(int width, int height, int internalFormat, int format) {
		super();
		this.width = width;
		this.height = height;
		this.internalFormat = internalFormat;
		this.format = format;
		data = null;
	}

	public TextureData(int width,int height,ByteBuffer data){
		this.width = width;
		this.height = height;
		this.data = data;
		format = internalFormat = GL11.GL_RGBA;
	}
	
	public TextureData(String file) {
		this(new File(file));
	}
	
	public TextureData(File f){
		try {
			BufferedImage img = ImageIO.read(f);
			width = img.getWidth();
			height = img.getHeight();
			
			int[] pixels = new int[img.getWidth() * img.getHeight()];
			img.getRGB(0, 0, img.getWidth(), img.getHeight(), pixels, 0, img.getWidth());

			data = BufferUtils.createByteBuffer(img.getWidth() * img.getHeight() * 4); //4 for RGBA, 3 for RGB

			for(int y = 0; y < img.getHeight(); y++){
                for(int x = 0; x < img.getWidth(); x++){
                    int pixel = pixels[y * img.getWidth() + x];
                    data.put((byte) ((pixel >> 16) & 0xFF));     
                    data.put((byte) ((pixel >> 8) & 0xFF));     
                    data.put((byte) (pixel & 0xFF));             
                    data.put((byte) ((pixel >> 24) & 0xFF));    
                }
            }

			data.flip();
			format = internalFormat = GL11.GL_RGBA;
		} catch (IOException e) {
			e.printStackTrace();
			
		}
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @return the data
	 */
	public ByteBuffer getData() {
		return data;
	}
	
	/**
	 * @return the internalFormat
	 */
	public int getInternalFormat() {
		return internalFormat;
	}

	/**
	 * @return the format
	 */
	public int getFormat() {
		return format;
	}
	
	
}
