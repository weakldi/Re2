package engine.light;

import engine.util.math.Vector3;

public class AmbientLight extends Light{

	public AmbientLight(Vector3 color) {
		super.color = color;
	}

}
