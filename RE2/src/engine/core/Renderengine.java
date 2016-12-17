package engine.core;

import java.util.HashMap;
import java.util.Set;

import org.lwjgl.opengl.GLCapabilities;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;

//import org.lwjgl.opengl.GLCapabilities;
//import org.lwjgl.opengl.GLUtil;

import engine.geometry.Cube;
import engine.geometry.GeometryOBJ;
import engine.geometry.Mesh;
import engine.geometry.Quader;
import engine.geometry.VAO;
import engine.geometry.Vertex;
import engine.geometry.Zylinder;
import engine.util.math.Matrix4x4;
import engine.util.math.Vector2;
import engine.util.math.Vector3;

public final class Renderengine implements Runnable{
	private static HashMap<Window, Renderengine> re = new HashMap<>();
	
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
	
	
	
	private Window w;
	private HashMap<Integer, Mesh> meshMap = new HashMap<>();
	private Renderengine(){
		this(new Window());
	}
	
	private Renderengine(Window w){
		this.w = w;
		w.init();
		w.createCapabilities();
		c = w.getCapabilities();
		
	}
	
	
	public void render(Shader s){
		
	}
	
	
	public void run() {
		
		w.show();
		
		Shader s = new Shader(FileLoader.loadFile("res/shader/test.vert"),FileLoader.loadFile("res/shader/test.frag"));
		s.addUniformValue("color", new Vector3(1, 0.5f, 0.25f));
		Camera c = new Camera();
		
				
		c.setRotation(new Vector3(0,0,0));
		c.setPosition(new Vector3(0, 0, 0));
		Vector3 vs = c.getPosition();
		Texture t = new Texture();
		t.loadData(new TextureData(System.getProperty("user.home") + "/the_talos_principle_circle_icon_by_vitald2-d8dsgkr.png"));
		s.addUniformValue("vp", c.getVpMat());
		s.addUniformValue("p", c.getProjection());
		s.addUniformValue("v", c.getViewMatrix());

		GeometryOBJ q = new Zylinder(new Vector3(0, 0, -4), new Vector3(0,0,0),new Vector3(1, 1, 1));

//		q.getDim().x = 10;
//		q.getScale().x = 0.25f;
		FBO.bindWindowAsTarget();

		while(!w.shouldClose()){
			w.clear();

			t.bind(0);
			s.bind();
			s.update();
			for (Mesh mesh : meshMap.values()) {
				mesh.render(s);
			}
			c.update();
			
			q.setRot(q.getRot().add(new Vector3(0, .25f, 0.f)));
//			System.out.println(q.getTransMat());
			Shader.unbind();

			w.swapBuffers();
			w.pollEvents();
		}
		System.out.println(s.getUniforms());
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
}
