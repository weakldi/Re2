package engine.geometry;

import java.util.HashMap;

import engine.core.Renderengine;
import engine.util.math.Vector2;
import engine.util.math.Vector3;

public class Cone extends GeometryOBJ
{

	private static HashMap<Integer, Integer> meshTable = new HashMap<>();

	public Cone(Vector3 pos, Vector3 rot, Vector3 dim) {
		super(pos, rot, dim);
		genMesh(10);
	}
	
	private void genMesh(int resolution){
		if(resolution< 5){
			System.out.println("Aufloesung für Zylinder zu klein! mindestens 5");
			resolution = 5;
		}
		resolution*=2;
		float[] circalPoints = new float[resolution*2];
		float stepSize =  (360.0f / (float) resolution);
		for(int i = 0; i < resolution; i++){
			float angle = stepSize*i;
			float radians = (float) Math.toRadians(angle);
		
			circalPoints[2*i] = (float) (0.5*Math.sin(radians));
			circalPoints[2*i+1] = (float) (0.5*Math.cos(radians));
		}
		Vertex[] vert = new Vertex[resolution*2 + 2];
		
		vert[resolution] = new Vertex(new Vector3(0f, 0, 0), new Vector3(1f, 0, 0),new Vector2(1,01));
		vert[resolution*2+1] = new Vertex(new Vector3(-1f, 0, 0), new Vector3(-1f, 0, 0),new Vector2(1,01));
		for(int i = 0; i < resolution; i++){
			vert[i] = new Vertex(new Vector3(0,circalPoints[i*2],circalPoints[i*2+1]),new Vector3(1,0,0),new Vector2(0,0));
			vert[resolution+i+1] = new Vertex(new Vector3(0,circalPoints[i*2],circalPoints[i*2+1]),new Vector3(0,circalPoints[i*2],circalPoints[i*2+1]),new Vector2(0,0));
			
		}
		int[] indArray = new int[resolution*6];
		for(int i = 0; i < resolution ;i++){
			
			int index2 = i+1;
			index2 = index2==resolution?0:index2;
			//kreis 1
			indArray[i*3] = resolution;
			indArray[i*3+1] = i;
			indArray[i*3+2] = index2;
			//kreis2
			indArray[resolution*3 + i*3] = resolution*2+1;
			indArray[resolution*3 + i*3+1] =resolution+1 + i;
			indArray[resolution*3 + i*3+2] = resolution+1 +index2;

		}
		Mesh m = new Mesh(indArray, vert);
		meshID = m.getID();
		meshTable .put(resolution/2, meshID);
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
