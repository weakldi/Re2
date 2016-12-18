package engine.core;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL21.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL31.*;
import static org.lwjgl.opengl.GL32.*;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.opengl.GL40.*;
import static org.lwjgl.opengl.GL42.*;

import org.lwjgl.opengl.GL11;

import engine.util.BufferUtil;



public class FBO{
	private int id;
	private int rbo;
	
	private Texture[] textures;
	private int depthTexture = -1;
	private int[] drawBuffer;
	private int width, height;
	
	public static FBO window = new FBO(0,0,new int[]{},new int[]{},new int[]{}){
		@Override
		protected void createFBO(int[] attachments) {
			System.out.println(getTexureCount());
		}
		@Override
		public void bind() {
			bindWindowAsTarget();
		}
	};
	
	public FBO(int width,int height,int[] attachments,int[] formats,int[] internalFormats) {
		textures = new Texture[attachments.length];
		this.width = width;
		this.height = height;
		for(int i = 0; i < textures.length;i++){
			textures[i] = new Texture();
			TextureData data = new TextureData(width, height, internalFormats[i], formats[i]);
			textures[i].loadData(data);
		}
		createFBO(attachments);
		
	}
	protected void createFBO(int[] attachments){
		drawBuffer = new int[attachments.length];
		for(int i = 0; i < attachments.length; i++){
			if(attachments[i] == GL_DEPTH_ATTACHMENT){
				depthTexture = i;
				drawBuffer[i] = GL_NONE;
			}else{
				drawBuffer[i] = attachments[i];
			}
		}
		createFrameBufferID();
		bind();
		for(int i = 0; i < attachments.length;i++){
			glFramebufferTexture2D(GL_FRAMEBUFFER, attachments[i], GL_TEXTURE_2D, textures[i].getTextureID(), 0);
		}
		if(depthTexture <0){
			System.out.println("No Depth");
			rbo = glGenRenderbuffers();
			glBindRenderbuffer(GL_RENDERBUFFER, rbo);
			glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT, width, height);
			glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, rbo);
		}
		glDrawBuffers( BufferUtil.createFilpedIntbuffer(drawBuffer));
		glReadBuffer(GL_NONE);
		int framebuffer = glCheckFramebufferStatus( GL_FRAMEBUFFER ); 
		switch ( framebuffer ) {
		    case GL_FRAMEBUFFER_COMPLETE:
		    	System.out.println("Framebuffer Complete!");
		        break;
		    case GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT:
		        throw new RuntimeException( "FrameBuffer: " + 0
		                + ", has caused a GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT exception" );
		    case GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT:
		        throw new RuntimeException( "FrameBuffer: " + 0
		                + ", has caused a GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT exception" );
		   
		    case GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER:
		        throw new RuntimeException( "FrameBuffer: " + 0
		                + ", has caused a GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER exception" );
		    
		    case GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER:
		        throw new RuntimeException( "FrameBuffer: " + 0
		                + ", has caused a GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER exception" );
		        default: System.out.println("dfg");
		    }
		unbind();
		
	}
	
	private void createFrameBufferID(){
		id = glGenFramebuffers();
	}
	
	public void bind(){
		glBindTexture(GL_TEXTURE_2D, 0);
		glBindFramebuffer(GL_FRAMEBUFFER, id);
		glViewport(0, 0, width, height);
	}
	
	public void unbind(){
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
		glViewport(0, 0, Renderengine.getInstance().getW().getWIDTH(), Renderengine.getInstance().getW().getHEIGHT());
	}
	
	public static void bindWindowAsTarget(){
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
		glViewport(0, 0, Renderengine.getInstance().getW().getWIDTH(), Renderengine.getInstance().getW().getHEIGHT());
	}
	
	public Texture getTexture(int i){
		return textures[i];
	}
	/**
	 * @return the depthTexture
	 */
	public int getDepthTexture() {
		return depthTexture;
	}
	/**
	 * @param depthTexture the depthTexture to set
	 */
	public void setDepthTexture(int depthTexture) {
		this.depthTexture = depthTexture;
	}
	
	public int getTexureCount(){
		return textures.length;
	}
	public void clear() {
		GL11.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
}