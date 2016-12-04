package engine.geometry;

import java.util.Vector;

import engine.util.math.Vector2;
import engine.util.math.Vector3;

public class Vertex {
	private Vector3 pos,normal;
	private Vector2 uv;
	
	
	
	public Vertex(Vector3 pos, Vector3 normal, Vector2 uv) {
		super();
		this.pos = pos;
		this.normal = normal;
		this.uv = uv;
	}
	
	
	
	public Vertex(Vector3 pos, Vector3 normal) {
		super();
		this.pos = pos;
		this.normal = normal;
		uv = new Vector2();
	}


	public Vector3 getPos() {
		return pos;
	}
	public void setPos(Vector3 pos) {
		this.pos = pos;
	}
	public Vector3 getNormal() {
		return normal;
	}
	public void setNormal(Vector3 normal) {
		this.normal = normal;
	}
	public Vector2 getUv() {
		return uv;
	}
	public void setUv(Vector2 uv) {
		this.uv = uv;
	}
	
	
}
