package engine.util.math;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import static java.lang.Math.*;
public class Matrix4x4 {
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
		data = new float[][]{
		    { f / aspectRatio, 0, 0, 0 },
		    { 0, f, 0, 0 },
		    { 0, 0, (far + near) / (near - far),  (2 * far * near) / (near - far) }, 
		    { 0, 0, -1, 0 }
		};
		return this;
	}
	
	public Matrix4x4 transform(Vector3 b){
		return transform(b.x,b.y,b.z);
	}
	
	public Matrix4x4 transform(float x, float y, float z){
		data[3][0] = x;
		data[3][1] = y;
		data[3][2] = z;
		return this;
	}
	
	public Matrix4x4 rotate(Vector3 axes){
		return rotate(axes.x,axes.y,axes.z);
	}
	
	public Matrix4x4 rotate(float x,float y,float z){
		Matrix4x4 rotX = rotateX(x);
		Matrix4x4 rotY = rotateY(y);
		Matrix4x4 rotZ = rotateZ(z);
		return rotZ.mul(rotY.mul(rotX));//https://github.com/BennyQBD/3DGameEngine/blob/master/src/com/base/engine/core/Matrix4f.java
	}
	
	public static Matrix4x4 rotateZ(float alpha){
		Matrix4x4 rotZ = new Matrix4x4().setIdentity();
		double r = toRadians(alpha);
		float c = (float)	cos(r);
		float s = (float) 	sin(r);
		rotZ.data[0][0] = c; rotZ.data[0][0] =-s; rotZ.data[0][0] = 0;
		rotZ.data[0][0] = s; rotZ.data[0][0] = c; rotZ.data[0][0] = 0;
		rotZ.data[0][0] = 0; rotZ.data[0][0] = 0; rotZ.data[0][0] = 1;
		return rotZ;
	}
	
	public static Matrix4x4 rotateX(float alpha){
		Matrix4x4 rotX = new Matrix4x4().setIdentity();
		double r = toRadians(alpha);
		float c = (float)	cos(r);
		float s = (float) 	sin(r);
		rotX.data[0][0] = 1; rotX.data[0][0] = 0; rotX.data[0][0] = 0;
		rotX.data[0][0] = 0; rotX.data[0][0] = c; rotX.data[0][0] =-s;
		rotX.data[0][0] = 0; rotX.data[0][0] = s; rotX.data[0][0] = c;
		return rotX;
	}
	
	public static Matrix4x4 rotateY(float alpha){
		Matrix4x4 rotY = new Matrix4x4().setIdentity();
		double r = toRadians(alpha);
		float c = (float)	cos(r);
		float s = (float) 	sin(r);
		rotY.data[0][0] = c; rotY.data[0][0] = 0; rotY.data[0][0] =-s;
		rotY.data[0][0] = 0; rotY.data[0][0] = 1; rotY.data[0][0] = 0;
		rotY.data[0][0] = s; rotY.data[0][0] = 0; rotY.data[0][0] = c;
		return rotY;
	}
	
	public void rotate(float alpha,Vector3 axis){
		
	}
	
	public static Matrix4x4 rotate(float alpha,float x,float y,float z){
		double r = toRadians(alpha);
		float c = (float)	cos(r);
		float s = (float) 	sin(r);
		float t = 1- (float)tan(r);
		Matrix4x4 result = new Matrix4x4();
		
		return result;
	}
	
	
	public void set(int x,int y,float data){
		this.data[x][y] =data;
	}
	
	public Matrix4x4 mul(Matrix4x4 right){
		Matrix4x4 resutl = new Matrix4x4().setNull();
		for(int i = 0; i < 4; i ++){
			for(int j = 0; j < 4 ; j ++){
				for(int k = 0; k < 4; k ++){
					resutl.getData()[i][j]+=data[i][k]*right.getData()[k][j];
				}
			}
		}
		return resutl;
	}
	
	public static Matrix4x4 mul(Matrix4x4 left,Matrix4x4 right){
		return left.mul(right);
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
		for(float[] row : data) {
			buffer.put(row);
		}
		return buffer;
	}
	
	
	
	public float[][] getData(){
		return data;
	}
	
	
}
