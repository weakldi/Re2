package engine.core;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Set;

import org.lwjgl.opengl.GLCapabilities;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL21.*;
import static org.lwjgl.opengl.GL31.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL32.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL14.*;


//import org.lwjgl.opengl.GLCapabilities;
//import org.lwjgl.opengl.GLUtil;
import engine.geometry.Cone;
import engine.geometry.Cube;
import engine.geometry.GeometryOBJ;
import engine.geometry.Mesh;
import engine.geometry.Quader;
import engine.geometry.VAO;
import engine.geometry.Vertex;
import engine.geometry.Zylinder;
import engine.light.DirLight;
import engine.light.Light;
import engine.light.PointLight;
import engine.util.math.Matrix4x4;
import engine.util.math.Vector2;
import engine.util.math.Vector3;

public final class Renderengine implements Runnable{
	private static HashMap<Window, Renderengine> re = new HashMap<>();
	public static Texture stdTexture;
	public static Renderengine getInstance(){
		if(re.isEmpty()) {
			Window w = new Window();
			re.put(w, new Renderengine(w));
			return re.get(w);
		}
		return re.get(re.keySet().toArray()[0]);
		
	}
	
	public static Renderengine getInstance(Window w){
		if(!re.containsKey(w))
			re.put(w, new Renderengine(w));
		return re.get(w);
	}
	
	private StaticPipeline pipeline;
	
	private Window w;
	private HashMap<Integer, Mesh> meshMap = new HashMap<>();

	private Camera cam;
		
	
	private Renderengine(){
		this(new Window());
	}
	
	private Renderengine(Window w){
		this.w = w;
		w.init();
		w.createCapabilities();
		c = w.getCapabilities();
		
		FBO.window = new FBO(w.getWIDTH(),w.getHEIGHT(),new int[]{},new int[]{},new int[]{}){
			@Override
			protected void createFBO(int[] attachments) {
				System.out.println(getTexureCount());
			}
			@Override
			public void bind() {
				bindWindowAsTarget();
			}
		};
		
		
		if(stdTexture==null){
			stdTexture = new Texture();
			ByteBuffer b = ByteBuffer.allocate(16);
			
			b.put((byte) 0b11111111);
			b.put((byte) 0b11111111);
			b.put((byte) 0b11111111);
			b.put((byte) 0b11111111);
			
			b.put((byte) 0b11111111);
			b.put((byte) 0b11111111);
			b.put((byte) 0b11111111);
			b.put((byte) 0b11111111);
			
			b.put((byte) 0b11111111);
			b.put((byte) 0b11111111);
			b.put((byte) 0b11111111);
			b.put((byte) 0b11111111);
			
			b.put((byte) 0b11111111);
			b.put((byte) 0b11111111);
			b.put((byte) 0b11111111);
			b.put((byte) 0b11111111);
			b.flip();
			TextureData data = new TextureData(2, 2, b);
			stdTexture.loadData(data);
		}
		
		
	}
	
	public void render(){
		pipeline.render3D(meshMap, cam);
	}
	
	
	
	
	public void run() {
		
		w.show();
		pipeline = new StaticPipeline(FBO.window);
		DirLight l = new DirLight(new Vector3(1,1,1),new Vector3(0,-1,-1));
		PointLight pl = new PointLight(new Vector3(1, 0.5f, 0.25f), new Vector3(0, 0, 0));
		cam = new Camera();
		
				
		cam.setRotation(new Vector3(0,0,0));
		cam.setPosition(new Vector3(0, 0, 0));
		Vector3 vs = cam.getPosition();
		Texture t = new Texture();
		t.loadData(new TextureData(System.getProperty("user.home") + "/the_talos_principle_circle_icon_by_vitald2-d8dsgkr.png"));
		
		GeometryOBJ q = new Cube(new Vector3(0,0,-4),1);
//		GeometryOBJ q = new Cone(new Vector3(0, 0, -4), new Vector3(0, 0, 0), new Vector3(1, 1, 1));
		q.setModleTexture(t);
//		q.getDim().x = 10;
//		q.getScale().x = 0.25f;
		FBO.bindWindowAsTarget();

		while(!w.shouldClose()){
			render();
			cam.update();
			q.setRot(q.getRot().add(new Vector3(0, .5f, 0.0f)));

			q.setRot(q.getRot().add(new Vector3(0.5f, .0f, .0f)));
			w.swapBuffers();
			w.pollEvents();
		}
		try {
			
			finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	public void start(){
		run();
	}
	
	
	public Window getW() {
		return w;
	}
	public GLCapabilities c;
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		System.out.print("Deleting Meshes...");
		Set<Integer> keys = meshMap.keySet();
		for (Integer integer : keys) {
			meshMap.get(integer).delete();
		}
		meshMap.clear();
		System.out.println(" Done!");
	}
	
	public HashMap<Integer, Mesh> getMeshes(){
		return meshMap;
	}
	
	public void addLight(Light light){
		pipeline.addLight(light);
	}
}
