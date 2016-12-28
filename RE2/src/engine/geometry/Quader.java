package engine.geometry;

import engine.core.Renderengine;
import engine.core.Shader;
import engine.util.math.Vector2;
import engine.util.math.Vector3;


public class Quader extends GeometryOBJ{

	public Quader(Vector3 pos,Vector3 dim) {
		super(pos,new Vector3(0, 0, 0),dim);
		
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
				new Vertex(new Vector3(-0.5f, -0.5f,-0.5f), new Vector3(-1, 0, 0), new Vector2(1,1)),    //8
				new Vertex(new Vector3(-0.5f, -0.5f, 0.5f), new Vector3(-1, 0, 0), new Vector2(1,0)),    //9
				new Vertex(new Vector3(-0.5f,  0.5f, 0.5f), new Vector3(-1, 0, 0), new Vector2(0,0)),	 //10
				new Vertex(new Vector3(-0.5f,  0.5f,-0.5f), new Vector3(-1, 0, 0), new Vector2(0,1)),    //11
				//right                                                                                  //
				new Vertex(new Vector3( 0.5f, -0.5f,-0.5f), new Vector3( 1, 0, 0), new Vector2(1,1)),    //12
				new Vertex(new Vector3( 0.5f, -0.5f, 0.5f), new Vector3( 1, 0, 0), new Vector2(1,0)),    //13
				new Vertex(new Vector3( 0.5f,  0.5f, 0.5f), new Vector3( 1, 0, 0), new Vector2(0,0)),	 //14
				new Vertex(new Vector3( 0.5f,  0.5f,-0.5f), new Vector3( 1, 0, 0), new Vector2(0,1)),    //15
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
		
	@Override
	public void prepare(Shader shader) {
		
	}
	
}
