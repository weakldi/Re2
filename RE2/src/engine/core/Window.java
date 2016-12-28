package engine.core;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL32.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.nio.ByteBuffer;


public class Window {
	private long window;
	private int WIDTH = 800,HEIGHT = 600;
	private boolean visible = false;
	private boolean isInit = false;
	public Window(){
	}
	
	public void init(){
		if(isInit)return;
		// Setup an error callback. The default implementation
				// will print the error message in System.err.
				GLFWErrorCallback.createPrint(System.err).set();

				// Initialize GLFW. Most GLFW functions will not work before doing this.
				if ( !glfwInit() )
					throw new IllegalStateException("Unable to initialize GLFW");

				// Configure our window
				glfwDefaultWindowHints(); // optional, the current window hints are already the default
				glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
				glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable
				
	
				// Create the window
				window = glfwCreateWindow(WIDTH, HEIGHT, "Hello World!", NULL, NULL);
				if ( window == NULL )
					throw new RuntimeException("Failed to create the GLFW window");

				// Setup a key callback. It will be called every time a key is pressed, repeated or released.
				glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
					if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
						glfwSetWindowShouldClose(window, true); // We will detect this in our rendering loop
				});

				// Get the resolution of the primary monitor
				GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
				// Center our window
				glfwSetWindowPos(
					window,
					(vidmode.width() - WIDTH) / 2,
					(vidmode.height() - HEIGHT) / 2
				);

				// Make the OpenGL context current
				glfwMakeContextCurrent(window);
				// Enable v-sync
				glfwSwapInterval(1);

				// Make the window visible
				glfwShowWindow(window);
		isInit = true;
		
	}
	
	public void initGL(){
		glClearDepth(1.0f);
		glDepthFunc(GL_LESS);
		glDepthMask(true);
		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}
	
	
	public boolean isInit() {
		return isInit;
	}

	public void setInit(boolean isInit) {
		this.isInit = isInit;
	}

	public int getWIDTH() {
		return WIDTH;
	}

	public int getHEIGHT() {
		return HEIGHT;
	}

	public void show(){
		glfwShowWindow(window);visible = true;
	}
	
	public boolean isVisible(){
		return visible;
	}
	
	public void hide(){
		glfwHideWindow(window);visible = false;
	}
	
	public void clear(){
		glClear( GL_DEPTH_BUFFER_BIT  | GL_COLOR_BUFFER_BIT );
	}
	
	public void pollEvents(){
		glfwPollEvents();

	}
	
	public void swapBuffers(){
		glfwSwapBuffers(window);
	}
	
	public boolean shouldClose() {
		return glfwWindowShouldClose(window);
	}
	
	public void createCapabilities(){
		GL.createCapabilities();
		initGL();
	}
	
	public GLCapabilities getCapabilities(){
		return GL.getCapabilities();
	}

	public long getID() {
		return window;
	}
	
	public float getAspectRatio(){
		return (float) WIDTH/HEIGHT;
	}
}
