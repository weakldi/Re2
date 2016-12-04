package engine.util.math;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import engine.core.UniformValue;

import static org.lwjgl.opengl.GL20.*;

import static java.lang.Math.*;
public class Matrix4x4 implements UniformValue{
	private float[][] data = new float[4][4];
	
	public Matrix4x4 setNull(){
		data[0][0] = 0; data[1][0] = 0; data[2][0] = 0; data[3][0] = 0;
		data[0][1] = 0; data[1][1] = 0; data[2][1] = 0; data[3][1] = 0; 
		data[0][2] = 0; data[1][2] = 0; data[2][2] = 0; data[3][2] = 0; 
		data[0][3] = 0; data[1][3] = 0; data[2][3] = 0; data[3][3] = 0;
		return this;
	}
	
	public Matrix4x4 setIdentity(){
		data[0][0] = 1; data[1][0] = 0; data[2][0] = 0; data[3][0] = 0;
		data[0][1] = 0; data[1][1] = 1; data[2][1] = 0; data[3][1] = 0; 
		data[0][2] = 0; data[1][2] = 0; data[2][2] = 1; data[3][2] = 0; 
		data[0][3] = 0; data[1][3] = 0; data[2][3] = 0; data[3][3] = 1;
		return this;
	}
	
	public Matrix4x4 setProjection(float far,float near,float fov, int w,int h){
		float aspectRatio = (float)w/(float)h;
		
		float f = (float) (1 / tan(fov / 2));
		data[0][0] = f / aspectRatio; data[1][0] = 0; data[2][0] = 0; data[3][0] = 0;
		data[0][1] = 0; data[1][1] = f; data[2][1] = 0; data[3][1] = 0;
		data[0][2] = 0; data[1][2] = 0; data[2][2] = (far + near) / (near - far); data[3][2] = (2 * far * near) / (near - far);
		data[0][3] = 0; data[1][3] = 0; data[2][3] = -1; data[3][3] = 0;
		return this;
	}
	
	public Matrix4x4 translate(Vector3 b){
		return translate(b.x,b.y,b.z);
	}
	
	public Matrix4x4 translate(float x, float y, float z){
		data[3][0] += x;
		data[3][1] += y;
		data[3][2] += z;
		return this;
	}
	
	public Matrix4x4 rotate(Vector3 axes){
		return rotate(axes.x,axes.y,axes.z);
	}
	
	public Matrix4x4 rotate(float x,float y,float z){
		Matrix4x4 rotX = rotateX(x);
		Matrix4x4 rotY = rotateY(y);
		Matrix4x4 rotZ = rotateZ(z);
		mul(rotZ.mul(rotY.mul(rotX)));
		return this;//https://github.com/BennyQBD/3DGameEngine/blob/master/src/com/base/engine/core/Matrix4f.java
	}
	
	public static Matrix4x4 rotateZ(float alpha){
		Matrix4x4 rotZ = new Matrix4x4().setIdentity();
		double r = toRadians(alpha);
		float c = (float)	cos(r);
		float s = (float) 	sin(r);
		rotZ.data[0][0] = c; rotZ.data[1][0] =-s; rotZ.data[2][0] = 0;
		rotZ.data[0][1] = s; rotZ.data[1][1] = c; rotZ.data[2][1] = 0;
		rotZ.data[0][2] = 0; rotZ.data[1][2] = 0; rotZ.data[2][2] = 1;
		return rotZ;
	}
	
	public static Matrix4x4 rotateX(float alpha){
		Matrix4x4 rotX = new Matrix4x4().setIdentity();
		double r = toRadians(alpha);
		float c = (float)	cos(r);
		float s = (float) 	sin(r);
		rotX.data[0][0] = 1; rotX.data[1][0] = 0; rotX.data[2][0] = 0;
		rotX.data[0][1] = 0; rotX.data[1][1] = c; rotX.data[2][1] =-s;
		rotX.data[0][2] = 0; rotX.data[1][2] = s; rotX.data[2][2] = c;
		return rotX;
	}
	
	public static Matrix4x4 rotateY(float alpha){
		Matrix4x4 rotY = new Matrix4x4().setIdentity();
		double r = toRadians(alpha);
		float c = (float)	cos(r);
		float s = (float) 	sin(r);
		rotY.data[0][0] = c; rotY.data[1][0] = 0; rotY.data[2][0] =-s;
		rotY.data[0][1] = 0; rotY.data[1][1] = 1; rotY.data[2][1] = 0;
		rotY.data[0][2] = s; rotY.data[1][2] = 0; rotY.data[2][2] = c;
		return rotY;
	}
	
	
	
	public static Matrix4x4 rotate(float alpha,Vector3 axis){
		return rotate(alpha, axis.x, axis.y, axis.z);
	}
	
	public Matrix4x4 makeTransmat(Vector3 rot,Vector3 up,Vector3 f,Vector3 pos){
		
//		Vector3 cross = up.cross(f); 
//		System.out.println(rot + " " + f + " " + up + " " + cross);
//		if(up.dot(f)!=0){
//			f = cross.cross(up);
//			System.out.println(f);
//		}
//		up.normailze();
//		cross.normailze();
//		f.normailze();
//		//mul(rotate(-rot.z, f).mul(rotate(-rot.y, up).mul(rotate(-rot.x,cross))));
//		mul(Matrix4x4.rotate(-rot.z, f).mul(Matrix4x4.rotate(-rot.y, up).mul( Matrix4x4.rotate(-rot.x, cross))));
		
		Matrix4x4 rotationMatrix = new Matrix4x4().setIdentity().rotate(rot);
		f = rotationMatrix.applyMatrix(f);
		
		makeTransmat(up,f,pos);
		return this;
	}
	
	public static Matrix4x4 rotate(float alpha,float x,float y,float z){
		double r = toRadians(alpha);
		float c = (float)	cos(r);
		float s = (float) 	sin(r);
		float t = 1- (float)tan(r);
		//c = 299792458;
		Matrix4x4 result = new Matrix4x4();
		result.data[0][0] = t*x*x+c; 	result.data[1][0] = t*x*y-s*z; 	result.data[2][0] = t*x*z+s*y;
		result.data[0][1] = t*x*y+s*z; 	result.data[1][1] = t*y*y+c;	result.data[2][1] = t*y*z-s*x;
		result.data[0][2] = t*x*z-s*y; 	result.data[1][2] = t*z*y+s*x;	result.data[2][2] = t*z*z+c;
		return result;
	}
	
	
	public void rotate(Vector3 f,Vector3 up){
		f.normailze();
		Vector3 right = f.cross(up);
		right.normailze();
		up = right.cross(f);
		up.normailze();
		data[0][0] = right.	x;	data[1][0] = right.	y;	data[2][0] = right.	z;
		data[0][1] = up.	x;	data[1][1] = up.	y;	data[2][1] = up.	z;
		data[0][2] = f.		x;	data[1][2] = f.		y;	data[2][2] = f.		z;
	
	}
	
	public Matrix4x4 makeTransmat(Vector3 u,Vector3 f,Vector3 pos){
		f.normailze();
		Vector3 right = u.cross(f);
		right.normailze();
		u = f.cross(right);
		u.normailze();
		data[0][0] = right.	x;	data[0][1] = right.	y;	data[0][2] = right.	z;  data[0][3] = -pos.x;
		data[1][0] = u.		x;	data[1][1] = u.		y;	data[1][2] = u.		z;  data[1][3] = -pos.y;
		data[2][0] = f.		x;	data[2][1] = f.		y;	data[2][2] = f.		z;  data[2][3] = -pos.z;
		data[3][0] = 0; 		data[3][1] = 0; 		data[3][2] = 0; 		data[3][3] = 1;
		
		return this;
	}
	
	public Vector3 applyMatrix(Vector3 v){
		float x = v.getX();
		float y = v.getY();
		float z = v.getZ();
		Vector4 v4 = mul(new Vector4(x, y, z, 0));
		v.setX(v4.getX());
		v.setY(v4.getY());
		v.setZ(v4.getZ());
		return v;
	}
	
	public Vector4 mul(Vector4 in){
		float x = in.getX();
		float y = in.getY();
		float z = in.getZ();
		float w = in.getW();
		
		x = data[0][0] * x + data[1][0] * y + data[2][0] * z + data[3][0] * w;
		y = data[0][1] * x + data[1][1] * y + data[2][1] * z + data[3][1] * w;
		z = data[0][2] * x + data[1][2] * y + data[2][2] * z + data[3][2] * w;
		w = data[0][3] * x + data[1][3] * y + data[2][3] * z + data[3][3] * w;
		in.setX(x);
		in.setY(y);
		in.setZ(z);
		in.setW(w);
		return in;
	}
	
	public void set(int x,int y,float data){
		this.data[x][y] =data;
	}
	//Überprüfen
	public Matrix4x4 mul(Matrix4x4 right){
		data = mul(this,right).data;
//		System.out.println(this);
		return this;
	}
	//Überprüfen
	public static Matrix4x4 mul(Matrix4x4 left,Matrix4x4 right){
		Matrix4x4 result = new Matrix4x4().setNull();
		mul(left,right,result);
		return result;
	}
	
	public static Matrix4x4 mul(Matrix4x4 left,Matrix4x4 right,Matrix4x4 dest){
		for(int i = 0; i < 4; i ++){
			for(int j = 0; j < 4; j ++){
				dest.data[i][j] = left.data[0][j]*right.data[i][0]+
						left.data[1][j]*right.data[i][1]+
						left.data[2][j]*right.data[i][2]+
						left.data[3][j]*right.data[i][3];
			}
		}
		return dest;
	}
	
	public void copy(Matrix4x4 source){
		for(int i = 0; i < 4; i ++){
			for(int j = 0; j < 4; j ++){
				data[i][j] = source.data[i][j];
			}
		}
	}
	
	public FloatBuffer getFloatBuffer() {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
		for(int x = 0; x < 4; x++){
			for(int y = 0; y < 4; y ++){
				buffer.put(data[x][y]);
			}
		}
		buffer.flip();
		return buffer;
	}
	
	
	
	public float[][] getData(){
		return data;
	}
	
	@Override
	public String toString() {
		
		return super.toString()+"\n"
				+ "/ " + 	data[0][0] +  ",\t" + 	data[1][0] + ",\t" + data[2][0] + ",\t" + data[3][0] + "\t\\\n"
				+ "| " + 	data[0][1] +  ",\t" + 	data[1][1] + ",\t" + data[2][1] + ",\t" + data[3][1] + "\t|\n"
				+ "| " + 	data[0][2] +  ",\t" +	data[1][2] + ",\t" + data[2][2] + ",\t" + data[3][2] + "\t|\n"
				+ "\\ " + 	data[0][3] + ",\t" + 	data[1][3] + ",\t" + data[2][3] + ",\t" + data[3][3] + "\t/\n";
	}

	@Override
	public void loadValueToUniform(int uniformID) {
		glUniformMatrix4fv(uniformID, false, getFloatBuffer());
	}
	
}
