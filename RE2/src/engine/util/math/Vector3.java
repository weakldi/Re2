package engine.util.math;

import org.lwjgl.opengl.GL20;

import engine.core.UniformValue;

public class Vector3 implements UniformValue{
	float x,y,z;
	
	
	
	public Vector3() {
		super();
	}

	public Vector3(float x, float y, float z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
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

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}

	public void normailze(){
		float len = length();
		x /= len;
		y /= len;
		z /= len;
	}
	
	public void scale(float len){
		x *= len;
		y *= len;
		z *= len;
	}
	
	public float length() {
		return (float) Math.sqrt((x*x+y*y+z*z));
	}
	
	public float dot(Vector3 b){
		return x*b.x+y*b.y+z*b.z;
	}
	
	public Vector3 cross(Vector3 b){
		Vector3 rV = new Vector3();
		rV.x = (y*b.z)-(z*b.y);
		rV.y = (z*b.x)-(x*b.z);
		rV.z = (x*b.y)-(y*b.x);
		return rV;
	}
	
	public void add(Vector3 b){
		x+=b.x;
		y+=b.y;
		z+=b.z;
	}
	
	public void sub(Vector3 b){
		x-=b.x;
		y-=b.y;
		z-=b.z;
	}

	@Override
	public void loadValueToUniform(int uniformID) {
		GL20.glUniform3f(uniformID, x, y, z);
	}
	
}
