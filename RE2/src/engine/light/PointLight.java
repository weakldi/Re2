package engine.light;

import static org.lwjgl.opengl.GL20.glUniform3f;

import engine.core.Shader;
import engine.util.math.Vector3;

public class PointLight extends Light{
	private Vector3 pos;
	public PointLight(Vector3 color, Vector3 pos) {
		super(color);
		this.pos = pos;
	}
	
	@Override
	public void prepareRenderer(Shader shader) {
		super.prepareRenderer(shader);
		glUniform3f(shader.getUniforms().get("lp"), pos.x, pos.y, pos.z);	
	}

	public Vector3 getPos() {
		return pos;
	}

	public void setPos(Vector3 pos) {
		this.pos = pos;
	}

}
