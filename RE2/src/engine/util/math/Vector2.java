package engine.util.math;

import org.lwjgl.opengl.GL20;

import engine.core.UniformValue;

public class Vector2 implements UniformValue{
	public float x,y;
	
	
	
	public Vector2() {
		super();
	}

	public Vector2(float x, float y) {
		super();
		this.x = x;
		this.y = y;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}


	public void normailze(){
		float len = length();
		x /= len;
		y /= len;
	}
	
	public void scale(float len){
		x *= len;
		y *= len;
	}
	
	public float length() {
		return (float) Math.sqrt((x*x+y*y));
	}
	
	public float dot(Vector2 b){
		return x*b.x+y*b.y;
	}
	
	public void add(Vector3 b){
		x+=b.x;
		y+=b.y;
	}
	
	public void sub(Vector3 b){
		x-=b.x;
		y-=b.y;
	}

	@Override
	public void loadValueToUniform(int uniformID) {
		GL20.glUniform2f(uniformID, x, y);
	}
	
}
