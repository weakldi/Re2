package engine.geometry;

import engine.core.RenderbleObject;
import engine.core.Renderengine;
import engine.core.Shader;
import engine.util.math.Vector2;
import engine.util.math.Vector3;

import static java.lang.Math.*;

public class Quader extends RenderbleObject{
	
	public Quader(Vector3 pos1,Vector3 pos2) {
		super(new Vector3(pos2.x+(pos1.x-pos2.x)/2, pos2.y+(pos1.y-pos2.y)/2, pos2.z+(pos1.z-pos2.z)/2), new Vector3(0, 0, 0), new Vector3(abs(pos1.x-pos2.x), abs(pos1.y-pos2.y), abs(pos1.z-pos2.z)));
		width = getScale().x;
		height = getScale().y;
		lenght = getScale().z;
		if(meshID==-1){
			Vertex[] v = new Vertex[]{
				//FrontFace
				new Vertex(new Vector3(-0.5f, -0.5f, 0.5f), new Vector3(0, 0, 1), new Vector2(1,1)),
				new Vertex(new Vector3(-0.5f,  0.5f, 0.5f), new Vector3(0, 0, 1), new Vector2(1,0)),
				new Vertex(new Vector3( 0.5f,  0.5f, 0.5f), new Vector3(0, 0, 1), new Vector2(0,0)),	
				new Vertex(new Vector3( 0.5f, -0.5f, 0.5f), new Vector3(0, 0, 1), new Vector2(0,1)),
				//BackFace
				new Vertex(new Vector3(-0.5f, -0.5f, -0.5f), new Vector3(0, 0, -1), new Vector2(1,1)),
				new Vertex(new Vector3(-0.5f,  0.5f, -0.5f), new Vector3(0, 0, -1), new Vector2(1,0)),
				new Vertex(new Vector3( 0.5f,  0.5f, -0.5f), new Vector3(0, 0, -1), new Vector2(0,0)),	
				new Vertex(new Vector3( 0.5f, -0.5f, -0.5f), new Vector3(0, 0, -1), new Vector2(0,1)),
				//Left
				new Vertex(new Vector3(-0.5f, -0.5f,-0.5f), new Vector3(1, 0, 0), new Vector2(1,1)),
				new Vertex(new Vector3(-0.5f, -0.5f, 0.5f), new Vector3(1, 0, 0), new Vector2(1,0)),
				new Vertex(new Vector3(-0.5f,  0.5f, 0.5f), new Vector3(1, 0, 0), new Vector2(0,0)),	
				new Vertex(new Vector3(-0.5f,  0.5f,-0.5f), new Vector3(1, 0, 0), new Vector2(0,1)),
				//right
				new Vertex(new Vector3( 0.5f, -0.5f,-0.5f), new Vector3(-1, 0, 0), new Vector2(1,1)),
				new Vertex(new Vector3( 0.5f, -0.5f, 0.5f), new Vector3(-1, 0, 0), new Vector2(1,0)),
				new Vertex(new Vector3( 0.5f,  0.5f, 0.5f), new Vector3(-1, 0, 0), new Vector2(0,0)),	
				new Vertex(new Vector3( 0.5f,  0.5f,-0.5f), new Vector3(-1, 0, 0), new Vector2(0,1)),
				//Up
				new Vertex(new Vector3(-0.5f,  0.5f,-0.5f), new Vector3(1, 0, 0), new Vector2(1,1)),
				new Vertex(new Vector3(-0.5f,  0.5f, 0.5f), new Vector3(1, 0, 0), new Vector2(1,0)),
				new Vertex(new Vector3( 0.5f,  0.5f, 0.5f), new Vector3(1, 0, 0), new Vector2(0,0)),	
				new Vertex(new Vector3( 0.5f,  0.5f,-0.5f), new Vector3(1, 0, 0), new Vector2(0,1)),
				//Down
				new Vertex(new Vector3(-0.5f, -0.5f,-0.5f), new Vector3(1, 0, 0), new Vector2(1,1)),
				new Vertex(new Vector3(-0.5f, -0.5f, 0.5f), new Vector3(1, 0, 0), new Vector2(1,0)),
				new Vertex(new Vector3( 0.5f, -0.5f, 0.5f), new Vector3(1, 0, 0), new Vector2(0,0)),	
				new Vertex(new Vector3( 0.5f, -0.5f,-0.5f), new Vector3(1, 0, 0), new Vector2(0,1)),
			};
			
			int[] elementArray = new int[]{
				0,1,2,    2,3,0,
				4,5,6,    6,7,4,
				8,9,10,   10,11,9,
				12,13,14, 14,15,12,
				16,17,18, 18,19,16,
				20,21,22, 22,23,20,
				24,25,26, 26,27,24,
				28,29,30, 30,31,28,
			};
			
			Mesh m = new Mesh(elementArray, v);
			meshID = m.getID();
			m.getObjects().add(this);
		}else{
			Renderengine.getInstance().getMeshes().get(meshID).getObjects().add(this);
		}
	}
	private int meshID = -1;
	private float 	width,
					height,
					lenght;
	@Override
	public void prepare(Shader shader) {
		
	}
}
