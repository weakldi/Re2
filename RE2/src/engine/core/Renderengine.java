package engine.core;

import java.util.HashMap;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLCapabilities;

import engine.geometry.VAO;

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
				-0.5f, 0.5f, 0f,    // Left top         ID: 0
                -0.5f, -0.5f, 0f,   // Left bottom      ID: 1
                0.5f, -0.5f, 0f,    // Right bottom     ID: 2
                0.5f, 0.5f, 0f      // Right left       ID: 3
		});
		v.addElementArray(new int[]{
				// Left bottom triangle
                0, 1, 2,
                // Right top triangle
                2, 3, 0
		});
		while(!w.shouldClose()){
			w.clear();
			render();
			v.bind();
			GL11.glColor3f(1, 1, 1);
			v.drawE();
			v.unbind();
			w.swapBuffers();
			w.pollEvents();
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
		
		
	}
}
