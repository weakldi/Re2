package engine.geometry;

import engine.util.math.Vector3;

public class Cube extends Quader{

	public Cube(Vector3 pos,float dim) {
		super(pos,new Vector3(dim, dim, dim));
	}

}
