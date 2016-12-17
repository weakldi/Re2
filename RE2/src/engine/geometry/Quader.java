package engine.geometry;

import engine.core.RenderbleObject;
import engine.core.Renderengine;
import engine.core.Shader;
import engine.util.math.Vector2;
import engine.util.math.Vector3;

import static java.lang.Math.*;

public class Quader extends RenderbleObject{
	
	//super(new Vector3(pos.x-dim.x/2.0f, pos.y-dim.y/2.0f, pos.z-dim.z/2.0f), new Vector3(pos.x+dim.x/2.0f, pos.y+dim.y/2.0f, pos.z+dim.z/2.0f));
	
	public Quader(Vector3 pos,Vector3 dim) {
		super(pos,
				new Vector3(0, 0, 0),
				dim);
//		super(new Vector3(pos2.x+(pos1.x-pos2.x)/2, pos2.y+(pos1.y-pos2.y)/2, pos2.z+(pos1.z-pos2.z)/2), new Vector3(0, 0, 0), new Vector3(abs(pos1.x-pos2.x), abs(pos1.y-pos2.y), abs(pos1.z-pos2.z)));
		width = getScale().x;
		height = getScale().y;
		lenght = getScale().z;
		if(meshID==-1){
			Vertex[] v = new Vertex[]{
				//FrontFace
				new Vertex(new Vector3(-0.5f, -0.5f, 0.5f), new Vector3(0, 0, 1), new Vector2(1,1)),     //0
				new Vertex(new Vector3(-0.5f,  0.5f, 0.5f), new Vector3(0, 0, 1), new Vector2(1,0)),     //1
				new Vertex(new Vector3( 0.5f,  0.5f, 0.5f), new Vector3(0, 0, 1), new Vector2(0,0)),	 //2
				new Vertex(new Vector3( 0.5f, -0.5f, 0.5f), new Vector3(0, 0, 1), new Vector2(0,1)),     //3
				//BackFace                                                                               //
				new Vertex(new Vector3(-0.5f, -0.5f, -0.5f), new Vector3(0, 0, -1), new Vector2(1,1)),   //4
				new Vertex(new Vector3(-0.5f,  0.5f, -0.5f), new Vector3(0, 0, -1), new Vector2(1,0)),   //5
				new Vertex(new Vector3( 0.5f,  0.5f, -0.5f), new Vector3(0, 0, -1), new Vector2(0,0)),	 //6
				new Vertex(new Vector3( 0.5f, -0.5f, -0.5f), new Vector3(0, 0, -1), new Vector2(0,1)),   //7
				//Left                                                                                   //
				new Vertex(new Vector3(-0.5f, -0.5f,-0.5f), new Vector3(1, 0, 0), new Vector2(1,1)),     //8
				new Vertex(new Vector3(-0.5f, -0.5f, 0.5f), new Vector3(1, 0, 0), new Vector2(1,0)),     //9
				new Vertex(new Vector3(-0.5f,  0.5f, 0.5f), new Vector3(1, 0, 0), new Vector2(0,0)),	 //10
				new Vertex(new Vector3(-0.5f,  0.5f,-0.5f), new Vector3(1, 0, 0), new Vector2(0,1)),     //11
				//right                                                                                  //
				new Vertex(new Vector3( 0.5f, -0.5f,-0.5f), new Vector3(-1, 0, 0), new Vector2(1,1)),    //12
				new Vertex(new Vector3( 0.5f, -0.5f, 0.5f), new Vector3(-1, 0, 0), new Vector2(1,0)),    //13
				new Vertex(new Vector3( 0.5f,  0.5f, 0.5f), new Vector3(-1, 0, 0), new Vector2(0,0)),	 //14
				new Vertex(new Vector3( 0.5f,  0.5f,-0.5f), new Vector3(-1, 0, 0), new Vector2(0,1)),    //15
				//Up                                                                                     //
				new Vertex(new Vector3(-0.5f,  0.5f,-0.5f), new Vector3(0, 1, 0), new Vector2(1,1)),     //16
				new Vertex(new Vector3(-0.5f,  0.5f, 0.5f), new Vector3(0, 1, 0), new Vector2(1,0)),     //17
				new Vertex(new Vector3( 0.5f,  0.5f, 0.5f), new Vector3(0, 1, 0), new Vector2(0,0)),	 //18
				new Vertex(new Vector3( 0.5f,  0.5f,-0.5f), new Vector3(0, 1, 0), new Vector2(0,1)),     //19
				//Down                                                                                   //
				new Vertex(new Vector3(-0.5f, -0.5f,-0.5f), new Vector3(0,-1, 0), new Vector2(1,1)),     //20
				new Vertex(new Vector3(-0.5f, -0.5f, 0.5f), new Vector3(0,-1, 0), new Vector2(1,0)),     //21
				new Vertex(new Vector3( 0.5f, -0.5f, 0.5f), new Vector3(0,-1, 0), new Vector2(0,0)),	 //22
				new Vertex(new Vector3( 0.5f, -0.5f,-0.5f), new Vector3(0,-1, 0), new Vector2(0,1)),     //23
			};
			
			int[] elementArray = new int[]{
				0,1,2,    2,3,0,    //F
				4,5,6,    6,7,4,	//B  
				8,9,10,   10,11,8,  //L
				12,13,14, 14,15,12, //R
				16,17,18, 18,19,16, //U
				20,21,22, 22,23,20  //D
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
