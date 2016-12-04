package engine.core;

import java.util.HashMap;
import java.util.Set;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLCapabilities;

import engine.geometry.Mesh;
import engine.geometry.VAO;
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
	
	public void render(){
		
	}
	
	
	public void run() {
		
		w.show();
		VAO v = new VAO();
		v.addDataStatic(0, 3, new float[]{
				-0.5f, 0.5f, -10f,    // Left top         ID: 0
                -0.5f, -0.5f, -10f,   // Left bottom      ID: 1
                0.5f, -0.5f, -10f,    // Right bottom     ID: 2
                0.5f, 0.5f,-10f      // Right left       ID: 3
		});
		
		v.addDataStatic(1, 2, new float[]{
				0,0,
				0,1,
				1,1,
				1,0
		});

		v.addElementArray(new int[]{
				// Left bottom triangle
                0, 1, 2,
                // Right top triangle
                2, 3, 0
		});
		Shader s = new Shader(FileLoader.loadFile("res/shader/test.vert"),FileLoader.loadFile("res/shader/test.frag"));
		s.addUniformValue("color", new Vector3(1, 0.5f, 0.25f));
		Camera c = new Camera();
		c.setPosition(new Vector3(0, 0, 5));
		c.setRotation(new Vector3(0,0,0));

		Texture t = new Texture();
		t.loadData(new TextureData(System.getProperty("user.home") + "/the_talos_principle_circle_icon_by_vitald2-d8dsgkr.png"));
		s.addUniformValue("vp", c.getVpMat());
		s.addUniformValue("p", c.getProjection());
		s.addUniformValue("v", c.getViewMatrix());
		while(!w.shouldClose()){
			w.clear();
			render();
			t.bind(0);
			s.updateB();
			v.bind();
			v.drawE();
			v.unbind();
			c.update();
	
			Shader.unbind();
			w.swapBuffers();
			w.pollEvents();
		}
		try {
			finalize();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
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
