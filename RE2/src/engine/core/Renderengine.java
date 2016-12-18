package engine.core;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Set;

import org.lwjgl.opengl.GLCapabilities;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;
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
	
	
	
	private Window w;
	private HashMap<Integer, Mesh> meshMap = new HashMap<>();
	private FBO renderBuffer;
	private VAO screen;
	private Camera cam;
	
	private Shader finalShader;
	private Shader gBuffer;
	Shader s;
	
	private Renderengine(){
		this(new Window());
	}
	
	private Renderengine(Window w){
		this.w = w;
		w.init();
		w.createCapabilities();
		c = w.getCapabilities();
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
		renderSolid3D(renderBuffer,cam);
		finalShader.bind();
		screen.bind();
		renderBuffer.getTexture(0).bind(0);
		renderBuffer.getTexture(1).bind(1);
		renderBuffer.getTexture(2).bind(2);
		renderBuffer.getTexture(3).bind(3);
		screen.drawE();
		renderBuffer.getTexture(0).unbind();
		screen.unbind();
		Shader.unbind();
	}
	
	public void render(FBO target){
		renderSolid3D(renderBuffer,cam);
		
//		screen.bind();
//		renderBuffer.getTexture(0).bind(0);
//		screen.drawE();
//		renderBuffer.getTexture(0).unbind();
//		screen.unbind();
	}
	
	public void renderSolid3D(FBO target, Camera cam){
		target.bind();
		target.clear();
		s.bind();
		s.update();
		for (Mesh mesh : meshMap.values()) {
			mesh.render(s);
		}
		cam.update();
		
		
//		System.out.println(q.getTransMat());
		Shader.unbind();
		target.unbind();
		
		
	}
	
	
	public void run() {
		
		w.show();
		renderBuffer = new FBO(w.getWIDTH(),w.getHEIGHT(),
				new int[]{GL_COLOR_ATTACHMENT0	,GL_COLOR_ATTACHMENT1	,GL_COLOR_ATTACHMENT2	,GL_DEPTH_ATTACHMENT},
				new int[]{GL_RGBA				,GL_RGBA				,GL_RGBA				,GL_DEPTH_COMPONENT},
				new int[]{GL_RGBA16,			GL_RGBA32F				,GL_RGBA32F				,GL_DEPTH_COMPONENT24}
				);
		screen = new VAO();
		
		screen.addDataStatic(0, 2, new float[]{
				-1,-1,
				1,-1,
				1,1,
				-1,1
				
		});
//		screen.addDataStatic(1, 2, new float[]{
//				0,0,
//				1,0,
//				1,1,
//				0,1
//		});
		screen.addElementArray(new int[]{
				0,1,2,
				2,3,0
		});
		finalShader = new Shader(FileLoader.loadFile("res/shader/p2.vert"),FileLoader.loadFile("res/shader/p2a.frag"));
		finalShader.bind();
		glUniform1ui(finalShader.getUniforms().get("diffuse"), 0);
		Shader.unbind();
		s = new Shader(FileLoader.loadFile("res/shader/test.vert"),FileLoader.loadFile("res/shader/test.frag"));
		s.addUniformValue("color", new Vector3(1, 0.5f, 0.25f));
		cam = new Camera();
		
				
		cam.setRotation(new Vector3(0,0,0));
		cam.setPosition(new Vector3(0, 0, 0));
		Vector3 vs = cam.getPosition();
		Texture t = new Texture();
		t.loadData(new TextureData(System.getProperty("user.home") + "/the_talos_principle_circle_icon_by_vitald2-d8dsgkr.png"));
		s.addUniformValue("vp", cam.getVpMat());
		s.addUniformValue("p", cam.getProjection());
		s.addUniformValue("v", cam.getViewMatrix());

		GeometryOBJ q = new Cone(new Vector3(0, 0, -4), new Vector3(0,0,0),new Vector3(1, 1, 1));

//		q.getDim().x = 10;
//		q.getScale().x = 0.25f;
		FBO.bindWindowAsTarget();

		while(!w.shouldClose()){
			w.clear();
			
			render();
			
//			t.bind(0);
//			s.bind();
//			s.update();
//			for (Mesh mesh : meshMap.values()) {
//				mesh.render(s);
//			}
//			cam.update();
			
			q.setRot(q.getRot().add(new Vector3(0, .25f, 0.f)));
//			System.out.println(q.getTransMat());
//			Shader.unbind();
			q.setRot(q.getRot().add(new Vector3(0, .25f, 0.f)));
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
