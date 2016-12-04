package engine.geometry;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import engine.core.RenderbleObject;
import engine.core.Renderengine;
import engine.core.Shader;
import engine.util.math.Vector2;
import engine.util.math.Vector3;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL15.*;


public class Mesh {
	private VAO vertexArrayObject;
	private Vertex[] data;
	
	private final int ID;
	private static int nextID = 0;
	
	private ArrayList<RenderbleObject> objects;

	public Mesh(int[] indexData,Vertex[] data) {
		super();
		vertexArrayObject = new VAO();
		objects = new ArrayList<>();
		ID = nextID ++;
		this.data = data;
		float[] posData = new float[data.length*3];
		float[] nomData = new float[data.length*3];
		float[] uvData = new float[data.length*2];
		for (int i = 0; i < data.length; i++) {
			posData[i*3+0] = data[i].getPos().x;
			posData[i*3+1] = data[i].getPos().y;
			posData[i*3+2] = data[i].getPos().z;
			
			nomData[i*3+0] = data[i].getNormal().x;
			nomData[i*3+1] = data[i].getNormal().y;
			nomData[i*3+2] = data[i].getNormal().z;
			
			uvData[i*2+0] = data[i].getUv().x;
			uvData[i*2+1] = data[i].getUv().y;
		}
		
		vertexArrayObject.addDataStatic(0, 3, posData);
		vertexArrayObject.addDataStatic(1, 3, nomData);
		vertexArrayObject.addDataStatic(2, 2, uvData);
		
		vertexArrayObject.addElementArray(indexData);
		Renderengine.getInstance().getMeshes().put(ID, this);
	}
	
	public void render(Shader shader){
		vertexArrayObject.bind();
		for (RenderbleObject renderbleObject : objects) {
			renderbleObject.prepare(shader);
			renderbleObject.render(shader);
			vertexArrayObject.drawE();
		}
		vertexArrayObject.unbind();
	}

	public Vertex[] getData() {
		return data;
	}
	
	public VAO getVertexArrayObject() {
		return vertexArrayObject;
	}
	
	public void delete(){
		vertexArrayObject.delete();
		vertexArrayObject = null;
	}
	
	public ArrayList<RenderbleObject> getObjects() {
		return objects;
	}

	public int getID() {
		return ID;
	}