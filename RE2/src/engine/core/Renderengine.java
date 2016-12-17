package engine.core;

import java.util.HashMap;
import java.util.Set;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;

//import org.lwjgl.opengl.GLCapabilities;
//import org.lwjgl.opengl.GLUtil;

import engine.geometry.Cube;
import engine.geometry.Mesh;
import engine.geometry.Quader;
import engine.geometry.VAO;
import engine.geometry.Vertex;
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
//		c = w.getCapabilities();
		
	}
	
	
	public void render(Shader s){
//		glEnable(GL_DEPTH_TEST);
//		glDepthMask(true);
		glBegin(GL_TRIANGLES);
		new Matrix4x4().setIdentity().loadValueToUniform(s.getUniforms().get("m"));
		glVertex3f(-1, -1, 	0);
		glVertex3f(0, 1, 	0);
		glVertex3f(1, -1, 	0);
		glVertex3f(-1, -1, 		1);
		glVertex3f(1, 1, 	1);
		glVertex3f(1, -1, 	-1);
		glEnd();
	}
	
	
	public void run() {
		
		w.show();
		VAO v = new VAO();
		v.addDataStatic(0, 3, new float[]{
				-0.5f, 0.5f, -10f,    // Left top         ID: 0
                -0.5f, -0.5f, 10f,   // Left bottom      ID: 1
                0.5f, -0.5f, 10f,    // Right bottom     ID: 2
                0.5f, 0.5f,10f      // Right left       ID: 3
		});
		
		v.addDataStatic(2, 2, new float[]{
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
		
				
		c.setRotation(new Vector3(0,0,0));
		c.setPosition(new Vector3(0, 0, 0));
		Vector3 vs = c.getPosition();
		Texture t = new Texture();
		t.loadData(new TextureData(System.getProperty("user.home") + "/the_talos_principle_circle_icon_by_vitald2-d8dsgkr.png"));
		s.addUniformValue("vp", c.getVpMat());
		s.addUniformValue("p", c.getProjection());
		s.addUniformValue("v", c.getViewMatrix());
//		Mesh m = new Mesh(new int[]{
//				// Left bottom triangle
//              
//                // Right top triangle
//				0, 1, 2,
//                3,4,5
//                
//		}, new Vertex[]{
//				new Vertex(new Vector3(0f, 1f, 1), 	new Vector3(0, 0, 1), new Vector2(0,1)),
//				new Vertex(new Vector3(-1f, -1f,-1), new Vector3(0, 0, 1), new Vector2(0,1)),
//				new Vertex(new Vector3(1f, -1f, 1), 	new Vector3(0, 0, 1), new Vector2(0,1)),
//				
//				new Vertex(new Vector3(-1f, -1f, 0), 	new Vector3(1, 0, 0), new Vector2(0,1)),
//				new Vertex(new Vector3(-1f, 1f,-2), new Vector3(1, 0, 0), new Vector2(0,1)),
//				new Vertex(new Vector3(1f, -1f, 0), 	new Vector3(1, 0, 0), new Vector2(0,1)),
//		});                                         
//		
//		System.out.println(m.getID());
		Cube q = new Cube(new Vector3(0, 0, -4), 1);
//		RenderbleObject obj = new RenderbleObject(new Vector3(0, 0, -10),new Vector3(0,0, 0),new Vector3(1, 1f, 1)) {
//			
//			@Override
//			public void prepare(Shader shader) {
//				// TODO Auto-generated method stub
//				
//			}
//		};
		FBO.bindWindowAsTarget();
//		obj.setMeshID(m.getID());
//		Matrix4x4 m = new Matrix4x4().setIdentity();
		while(!w.shouldClose()){
			w.clear();
//			render();
			t.bind(0);
			s.bind();
			s.update();
			for (Mesh mesh : meshMap.values()) {
				mesh.render(s);
			}
			c.update();
//			c.rotate(new Vector3(1, 0, 0));
			q.setRot(q.getRot().add(new Vector3(1, .25f, 0.5f)));
//			obj.getPos().z+=0.01f;
//			System.out.println(obj.getPos());
			Shader.unbind();
//			vs.add(new Vector3(0,0,-0.01f));
			w.swapBuffers();
			w.pollEvents();
		}
		System.out.println(s.getUniforms());
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
//	public GLCapabilities c;
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
