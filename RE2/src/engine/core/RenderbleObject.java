package engine.core;

import engine.util.math.Vector3;

public abstract class RenderbleObject {
	private Vector3 pos;
	private Vector3 rot;
	private Vector3 scale;
	public abstract void prepare(Shader shader);
}
