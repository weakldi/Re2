package engine.light;

import engine.core.Renderengine;
import engine.core.Shader;
import engine.util.math.Vector3;

import static org.lwjgl.opengl.GL20.*;

public abstract class Light {
	protected Vector3 color;
	
	protected Light(){};
	
	public Light(Vector3 color) {
		super();
		this.color = color;
		Renderengine.getInstance().addLight(this);
	}

	public void prepareRenderer(Shader shader){
		glUniform3f(shader.getUniforms().get("lc"), color.x, color.y, color.z);	
	}

	public Vector3 getColor() {
		return color;
	}

	public void setColor(Vector3 color) {
		this.color = color;
	}
	
	
}
