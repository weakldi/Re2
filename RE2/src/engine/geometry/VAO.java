package engine.geometry;


import  static org.lwjgl.opengl.GL11.*;

import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL15.*;

public class VAO {
	private int ID;
	private int[] vbos = new int[16];
	private int elementArray = 0;
	private int count;
	
	public VAO() {
		create();
	}
	
	private void create(){
		ID  = glGenVertexArrays();
	}
	
	public void bind(){
		glBindVertexArray(ID);
		for(int i = 0; i < 16;i++){
			if(vbos[i]>0){
				glEnableVertexAttribArray(i);

			}
			
		}
		if(elementArray>0){
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, elementArray);
		}
	}
	
	public void addDataStatic(int attrib,int size, float[] data){
		if(attrib>=0&&attrib<16)
		if(vbos[attrib]==0){
			bind();
			vbos[attrib] = glGenBuffers();
			glBindBuffer(GL_ARRAY_BUFFER, vbos[attrib]);
			glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
			glVertexAttribPointer(attrib, size, GL_FLOAT, false, 0, 0L);
			glBindBuffer(GL_ARRAY_BUFFER, 0);
			unbind();
		}else{
			System.err.println("Das Attribut " + attrib + " wird schon genutzt!");
		}
	}
	
	public void addElementArray(int[] data){
		elementArray = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, elementArray);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, data, GL_STATIC_DRAW);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		count = data.length;
	}
	
	public void unbind(){
		glBindVertexArray(GL_NONE);
		for(int i = 0; i < 16;i++){
			if(vbos[i]>0)glDisableVertexAttribArray(i);
		}
		if(elementArray>0){
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		}
	}
	
	public void delete(){
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		for(int i = 0; i < 16;i++){
			glDisableVertexAttribArray(i);
			if(vbos[i]!=0)glDeleteBuffers(vbos[i]);
		}
		unbind();
		glDeleteVertexArrays(ID);
		System.out.println("VAO[" + ID+"] geloescht!");
	}
	
	public void drawE(int c,int offset){
		glDrawElements(GL_TRIANGLES, c, GL_UNSIGNED_INT, offset);
	}
	
	public void drawE(){
		glDrawElements(GL_TRIANGLES, count, GL_UNSIGNED_INT, 0);
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		System.out.println("finalize");
		delete();
	}
}
