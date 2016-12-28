package engine.light;

import engine.core.Shader;
import engine.util.math.Vector3;

import static org.lwjgl.opengl.GL20.*;

public class DirLight extends Light{
	public DirLight(Vector3 color,Vector3 lightDir) {
		super(color);
		this.dir = lightDir;
	}

	private Vector3 dir;

	@Override
	public void prepareRenderer(Shader shader) {
		super.prepareRenderer(shader);
		glUniform3f(shader.getUniforms().get("ld"), dir.x, dir.y, dir.z);	
	}

	public Vector3 getDir() {
		return dir;
	}

	public void setDir(Vector3 dir) {
		this.dir = dir;
	}
	
	
}
