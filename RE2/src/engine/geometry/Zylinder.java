package engine.geometry;

import java.util.HashMap;

import engine.core.Renderengine;
import engine.util.math.Vector2;
import engine.util.math.Vector3;

public class Zylinder extends GeometryOBJ {
	private int meshID = -1;	
	private static HashMap<Integer, Integer> meshTable = new HashMap<>();
	public Zylinder(Vector3 pos, Vector3 rot, Vector3 dim) {
		super(pos, rot, dim);
		setResolution(10);
		
	}
	
	private void genMesh(int resolution){
		if(resolution< 5){
			System.out.println("Aufloesung für Zylinder zu klein! mindestens 5");
			resolution = 5;
		}
		resolution*=2;
		System.out.println("ds" + resolution);
		float[] circalPoints = new float[resolution*2];
		float stepSize =  (360.0f / (float) resolution);
		for(int i = 0; i < resolution; i++){
			float angle = stepSize*i;
			float radians = (float) Math.toRadians(angle);
		
			circalPoints[2*i] = (float) (0.5*Math.sin(radians));
			circalPoints[2*i+1] = (float) (0.5*Math.cos(radians));
		}
		Vertex[] vert = new Vertex[resolution*4 + 2];
		vert[resolution+1] = new Vertex(new Vector3(-0.5f, 0, 0), new Vector3(-1f, 0, 0),new Vector2(1,01));
		vert[0] = new Vertex(new Vector3(0.5f, 0, 0), new Vector3(1f, 0, 0),new Vector2(1,01));
		for(int i = 0; i < resolution; i++){
			vert[i+1] = new Vertex(new Vector3(0.5f,circalPoints[i*2],circalPoints[i*2+1]),new Vector3(1,0,0),new Vector2(0,0));
			vert[resolution+1 + i+1] = new Vertex(new Vector3(-0.5f,circalPoints[i*2],circalPoints[i*2+1]),new Vector3(-1,0,0),new Vector2(0,0));
			
			vert[resolution*2+1+i+1] = new Vertex(new Vector3(0.5f,circalPoints[i*2],circalPoints[i*2+1]),new Vector3(0,circalPoints[i*2],circalPoints[i*2+1]),new Vector2(0,0));
			vert[resolution*3+1+i+1] = new Vertex(new Vector3(-0.5f,circalPoints[i*2],circalPoints[i*2+1]),new Vector3(0,circalPoints[i*2],circalPoints[i*2+1]),new Vector2(0,0));
			
		}
		int[] indArray = new int[resolution*12];
		for(int i = 0; i < resolution ;i++){
			
			int index2 = i+2;
			index2 = index2>resolution?1:index2;
			//kreis 1
			indArray[i*3] = 0;
			indArray[i*3+1] = i+1;
			indArray[i*3+2] = index2;
			//kreis2
			indArray[resolution*3 + i*3] = resolution+1;
			indArray[resolution*3 + i*3+1] = resolution +1+ i+1;
			indArray[resolution*3 + i*3+2] = resolution +1+ (index2);
			//Rechteck
			indArray[resolution*6 + i*6] = resolution*2+1+i+1;
			indArray[resolution*6 + i*6+1] = resolution*2+1+index2;
			indArray[resolution*6 + i*6+2] = resolution*3+1+ (index2);
			indArray[resolution*6 + i*6+3] = resolution*3+1+ (index2);
			indArray[resolution*6 + i*6+4] = resolution*3+1+ i+1;
			indArray[resolution*6 + i*6+5] = resolution*2+1+i+1;
		}
		Mesh m = new Mesh(indArray, vert);
		meshID = m.getID();
		System.out.println("PR=" + resolution/2);
		meshTable.put(resolution/2, meshID);
		m.getObjects().add(this);
	}
	
	public void setResolution(int res){
		res = res<5?5:res;
		if(!meshTable.containsKey(res)){
			genMesh(res);
		}
		Renderengine.getInstance().getMeshes().get(meshID).getObjects().remove(this);
		meshID = meshTable.get(res);
		Renderengine.getInstance().getMeshes().get(meshID).getObjects().add(this);
	}
	
	
}
