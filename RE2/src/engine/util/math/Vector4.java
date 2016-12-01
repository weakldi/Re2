package engine.util.math;

public class Vector4 {
	float x,y,z,w;

	public Vector4() {
		super();
	}

	public Vector4(float x, float y, float z,float w) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	public void normailze(){
		float len = length();
		x /= len;
		y /= len;
		z /= len;
		w /= len;
	}
	
	public void scale(float len){
		x *= len;
		y *= len;
		z *= len;
		w *= len;
	}
	
	public float length() {
		return (float) Math.sqrt((x*x+y*y+z*z+w*w));
	}
	
	public float dot(Vector4 b){
		return x*b.x+y*b.y+z*b.z+w*b.w;
	}
	
	public void add(Vector4 b){
		x+=b.x;
		y+=b.y;
		z+=b.z;
		w+=b.w;
	}
	
	public void sub(Vector4 b){
		x-=b.x;
		y-=b.y;
		z-=b.z;
		w-=b.w;
	}

	public float getW() {
		return w;
	}

	public void setW(float w) {
		this.w = w;
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
	
}
