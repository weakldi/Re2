package engine.util.math;

import static java.lang.Math.*;

import java.util.Arrays;

import org.lwjgl.opengl.GL20;

import engine.core.UniformValue;
import engine.util.BufferUtil;

public class Matrix4x4 implements UniformValue{
	/**
	 * [0, 1, 2, 3 ]
	 * [4, 5, 6, 7 ]
	 * [8, 9, 10,11]
	 * [12,13,14,15]
	 * 
	 */
	
	public float[] m = new float[16];
	
	public Matrix4x4(){
		setIdentity();
	}
	
	public Matrix4x4(Matrix4x4 src){
		load(src);
	}
	
	public Matrix4x4 load(Matrix4x4 src){
		for(int i = 0; i < 16; i++){
			m[i] = src.m[i];
		}
		return this;
	}
	
	public Matrix4x4(float far,float near,float w,float h,float fov){
		setProjection(far, near, w, h, fov);
	}
	
	public Matrix4x4 add(Matrix4x4 toAdd){
		for(int i = 0; i < 16; i++){
			m[i] += toAdd.m[i];
		}
		return this;
	}
	
	public Matrix4x4 sub(Matrix4x4 toSub){
		for(int i = 0; i < 16; i++){
			m[i] -= toSub.m[i];
		}
		return this;
	}
	
	public Matrix4x4 mul(Matrix4x4 rightM){
		float[] left 	= Arrays.copyOf(m, 16);
		float[] right 	= rightM.m;
		
		m[0 ] = left[0 ]*right[0 ] + left[1 ]*right[4 ] + left[3 ] * right[8 ] + left[3 ] * right[12]; 
		m[1 ] = left[0 ]*right[1 ] + left[1 ]*right[5 ] + left[3 ] * right[9 ] + left[3 ] * right[13]; 
		m[2 ] = left[0 ]*right[2 ] + left[1 ]*right[6 ] + left[3 ] * right[10] + left[3 ] * right[14]; 
		m[3 ] = left[0 ]*right[3 ] + left[1 ]*right[7 ] + left[3 ] * right[11] + left[3 ] * right[15];
		                                                                                
		m[4 ] = left[4 ]*right[0 ] + left[5 ]*right[4 ] + left[6 ] * right[8 ] + left[7 ] * right[12]; 
		m[5 ] = left[4 ]*right[1 ] + left[5 ]*right[5 ] + left[6 ] * right[9 ] + left[7 ] * right[13]; 
		m[6 ] = left[4 ]*right[2 ] + left[5 ]*right[6 ] + left[6 ] * right[10] + left[7 ] * right[14]; 
		m[7 ] = left[4 ]*right[3 ] + left[5 ]*right[7 ] + left[6 ] * right[11] + left[7 ] * right[15]; 
		                                                                                
		m[8 ] = left[8 ]*right[0 ] + left[9 ]*right[4 ] + left[10] * right[8 ] + left[11] * right[12]; 
		m[9 ] = left[8 ]*right[1 ] + left[9 ]*right[5 ] + left[10] * right[9 ] + left[11] * right[13]; 
		m[10] = left[8 ]*right[2 ] + left[9 ]*right[6 ] + left[10] * right[10] + left[11] * right[14]; 
		m[11] = left[8 ]*right[3 ] + left[9 ]*right[7 ] + left[10] * right[11] + left[11] * right[15]; 
		                                                                                
		m[12] = left[12]*right[0 ] + left[13]*right[4 ] + left[14] * right[8 ] + left[15] * right[12]; 
		m[13] = left[12]*right[1 ] + left[13]*right[5 ] + left[14] * right[9 ] + left[15] * right[13]; 
		m[14] = left[12]*right[2 ] + left[13]*right[6 ] + left[14] * right[10] + left[15] * right[14]; 
		m[15] = left[12]*right[3 ] + left[13]*right[7 ] + left[14] * right[11] + left[15] * right[15]; 
		
		return this;
	}
	
	public static Matrix4x4 mul(Matrix4x4 leftM,Matrix4x4 rightM,Matrix4x4 dest){
		float[] left 	= leftM.m;
		float[] right 	= rightM.m;
		
		dest.m[0 ] = left[0 ]*right[0 ] + left[1 ]*right[4 ] + left[3 ] * right[8 ] + left[3 ] * right[12]; 
		dest.m[1 ] = left[0 ]*right[1 ] + left[1 ]*right[5 ] + left[3 ] * right[9 ] + left[3 ] * right[13]; 
		dest.m[2 ] = left[0 ]*right[2 ] + left[1 ]*right[6 ] + left[3 ] * right[10] + left[3 ] * right[14]; 
		dest.m[3 ] = left[0 ]*right[3 ] + left[1 ]*right[7 ] + left[3 ] * right[11] + left[3 ] * right[15];
		                                                                                     
		dest.m[4 ] = left[4 ]*right[0 ] + left[5 ]*right[4 ] + left[6 ] * right[8 ] + left[7 ] * right[12]; 
		dest.m[5 ] = left[4 ]*right[1 ] + left[5 ]*right[5 ] + left[6 ] * right[9 ] + left[7 ] * right[13]; 
		dest.m[6 ] = left[4 ]*right[2 ] + left[5 ]*right[6 ] + left[6 ] * right[10] + left[7 ] * right[14]; 
		dest.m[7 ] = left[4 ]*right[3 ] + left[5 ]*right[7 ] + left[6 ] * right[11] + left[7 ] * right[15]; 
		                                                                                     
		dest.m[8 ] = left[8 ]*right[0 ] + left[9 ]*right[4 ] + left[10] * right[8 ] + left[11] * right[12]; 
		dest.m[9 ] = left[8 ]*right[1 ] + left[9 ]*right[5 ] + left[10] * right[9 ] + left[11] * right[13]; 
		dest.m[10] = left[8 ]*right[2 ] + left[9 ]*right[6 ] + left[10] * right[10] + left[11] * right[14]; 
		dest.m[11] = left[8 ]*right[3 ] + left[9 ]*right[7 ] + left[10] * right[11] + left[11] * right[15]; 
		                                                                                     
		dest.m[12] = left[12]*right[0 ] + left[13]*right[4 ] + left[14] * right[8 ] + left[15] * right[12]; 
		dest.m[13] = left[12]*right[1 ] + left[13]*right[5 ] + left[14] * right[9 ] + left[15] * right[13]; 
		dest.m[14] = left[12]*right[2 ] + left[13]*right[6 ] + left[14] * right[10] + left[15] * right[14]; 
		dest.m[15] = left[12]*right[3 ] + left[13]*right[7 ] + left[14] * right[11] + left[15] * right[15]; 
		return dest;
	}
	
	public static Matrix4x4 mul(Matrix4x4 left,Matrix4x4 right){
		return mul(left,right,new Matrix4x4());
	}
	/**
	 * [0, 1, 2, 3 ] [1]
	 * [4, 5, 6, 7 ] [2]
	 * [8, 9, 10,11] [3]
	 * [12,13,14,15] [4]
	 * 
	 */
	
	public Vector4 mul(Vector4 vec){
		float x = m[0 ] * vec.x + m[1 ] * vec.y + m[2 ] * vec.z + m[3 ] * vec.w;
		float y = m[4 ] * vec.x + m[5 ] * vec.y + m[6 ] * vec.z + m[7 ] * vec.w;
		float z = m[8 ] * vec.x + m[9 ] * vec.y + m[10] * vec.z + m[11] * vec.w;
		float w = m[12] * vec.x + m[13] * vec.y + m[14] * vec.z + m[15] * vec.w;
		return new Vector4(x, y, z, w);
	}
	
	public Matrix4x4 translate(Vector2 translation){
		return translate(translation.x, translation.y);
	}
	
	public Matrix4x4 translate(Vector3 translation){
		return translate(translation.x,translation.y,translation.z);
	}
	
	public Matrix4x4 translate(float x, float y){
		m[3 ] = m[0 ] * x + m[1 ] * y;
		m[7 ] = m[4 ] * x + m[5 ] * y;
		m[11] = m[8 ] * x + m[9 ] * y;
		m[15] = m[12] * x + m[13] * y;
		return this;
	}
	
	public Matrix4x4 translate(float x,float y, float z){
		m[3 ] = m[0 ] * x + m[1 ] * y +m[2 ] * z;
		m[7 ] = m[4 ] * x + m[5 ] * y +m[6 ] * z;
		m[11] = m[8 ] * x + m[9 ] * y +m[10] * z;
//		m[15] = m[12] * x + m[13] * y +m[14] * z;
		return this;
	}
	
	public Matrix4x4 makeView(Vector3 up,Vector3 forward,Vector3 p){
		forward.normailze();
		up.normailze();
		
		Vector3 s = forward.cross(up);
		Vector3 u = s.cross(forward);
		Vector3 f = forward;
//		System.out.println("S: " + s);
//		System.out.println("U: " + u);
//		System.out.println("F: " + f);
//		Matrix4x4 translate = new Matrix4x4().setIdentity().translate(p);
		m[0 ] =-s.x; m[4 ] =-s.y; m[8 ] =-s.z; m[12] = 0;
		m[1 ] = u.x; m[5 ] = u.y; m[9 ] = u.z; m[13] = 0;
		m[2 ] = f.x; m[6 ] = f.y; m[10] = f.z; m[14] = 0;
		m[3 ] =   0; m[7 ] =   0; m[11] =   0; m[15] = 1;
		translate(p);
		return this;
	}
	
	public Matrix4x4 set(int x,int y, float value){
		if(x>=0&&x<4&&y>=0&&y<4)
			m[x*4+y] = value;
		else
			throw new IllegalArgumentException("X und Y müssen im Intervall [0;3] liegen!");
		return this;
	}
	
	/**
	 * [0, 1, 2, 3 ]
	 * [4, 5, 6, 7 ]
	 * [8, 9, 10,11]
	 * [12,13,14,15]
	 * 
	 */
	
	public Matrix4x4 transpose(){
		float[] newM = new float[]{
				m[0],m[4],m[8 ],m[12],
				m[1],m[5],m[9 ],m[13],
				m[2],m[6],m[10],m[14],
				m[3],m[7],m[11],m[15]
		};
		m = newM;
		return this;
	}
	
	public Matrix4x4 scale(Vector3 scale){
		return scale(scale.x,scale.y,scale.z);
	}
	
	public Matrix4x4 scale(float x,float y,float z){
		m[0 ] *= x; m[1 ] *= y; m[2 ] *= z; 
		m[4 ] *= x; m[5 ] *= y; m[6 ] *= z; 
		m[8 ] *= x; m[9 ] *= y; m[10] *= z; 
		m[12] *= x; m[13] *= y; m[14] *= z; 
		
		return this;
	}
	
	public Matrix4x4 rotate(Vector3 rot){
		return rotate(rot.x, rot.y, rot.z);
	}
	
	public Matrix4x4 rotate(float x,float y,float z){
		rotate(x, 1, 0, 0);
		rotate(y, 0, 1, 0);
		rotate(z, 0, 0, 1);
		return this;
	}
	/*
	 * [0, 1, 2, 3 ] [0,1,2]
	 * [4, 5, 6, 7 ] [3,4,5]
	 * [8, 9, 10,11] [6,7,8]
	 * [12,13,14,15]
	 * 
	 */
	
	public Matrix4x4 rotate(float alpha, float x,float y,float z){
		float cos = (float) cos(toRadians(alpha));
		float oneMcos = 1 - cos;
		float sin = (float) sin(toRadians(alpha));
		
		float[] m3x3 = new float[]{
			x*x*oneMcos+cos  , y*x*oneMcos-z*sin, z*x*oneMcos+y*sin,
			x*y*oneMcos+z*sin, y*y*oneMcos+cos  , z*y*oneMcos-x*sin,
			x*z*oneMcos-y*sin, y*z*oneMcos+x*sin, z*z*oneMcos+cos
		};
		
		float[] _m = new float[]{
			m[0] * m3x3[0] + m[1] * m3x3[3] + m[2 ] * m3x3[6],
			m[0] * m3x3[1] + m[1] * m3x3[4] + m[2 ] * m3x3[7], 
			m[0] * m3x3[2] + m[1] * m3x3[5] + m[2 ] * m3x3[8],

			m[4] * m3x3[0] + m[5] * m3x3[3] + m[6 ] * m3x3[6],
			m[4] * m3x3[1] + m[5] * m3x3[4] + m[6 ] * m3x3[7], 
			m[4] * m3x3[2] + m[5] * m3x3[5] + m[6 ] * m3x3[8],
			
			m[8] * m3x3[0] + m[9] * m3x3[3] + m[10] * m3x3[6],
			m[8] * m3x3[1] + m[9] * m3x3[4] + m[10] * m3x3[7], 
			m[8] * m3x3[2] + m[9] * m3x3[5] + m[10] * m3x3[8],
		};
		
		m[0 ] = _m[0];
		m[1 ] = _m[1];
		m[2 ] = _m[2];
		    
		m[4 ] = _m[3]; 
		m[5 ] = _m[4]; 
		m[6 ] = _m[5]; 
		    
		m[8 ] = _m[6]; 
		m[9 ] = _m[7]; 
		m[10] = _m[8]; 
		
		return this;
	}
	
	public Matrix4x4 rotate(Vector3 up,Vector3 forward){
		forward.normailze();
		Vector3 l = up.cross(forward);
		Vector3 u = l.cross(forward);
		Vector3 f = forward;
		l.normailze();
		u.normailze();
		
		float[] m3x3 = new float[]{
			l.x,l.y,l.z,
			u.x,u.y,u.z,
			f.x,f.y,f.z
		};
		
		float[] _m = new float[]{
				m[0] * m3x3[0] + m[1] * m3x3[3] + m[2 ] * m3x3[6],
				m[0] * m3x3[1] + m[1] * m3x3[4] + m[2 ] * m3x3[7], 
				m[0] * m3x3[2] + m[1] * m3x3[5] + m[2 ] * m3x3[8],

				m[4] * m3x3[0] + m[5] * m3x3[3] + m[6 ] * m3x3[6],
				m[4] * m3x3[1] + m[5] * m3x3[4] + m[6 ] * m3x3[7], 
				m[4] * m3x3[2] + m[5] * m3x3[5] + m[6 ] * m3x3[8],
				
				m[8] * m3x3[0] + m[9] * m3x3[3] + m[10] * m3x3[6],
				m[8] * m3x3[1] + m[9] * m3x3[4] + m[10] * m3x3[7], 
				m[8] * m3x3[2] + m[9] * m3x3[5] + m[10] * m3x3[8],
			};
			
			m[0 ] = _m[0];
			m[1 ] = _m[1];
			m[2 ] = _m[2];
			    
			m[4 ] = _m[3]; 
			m[5 ] = _m[4]; 
			m[6 ] = _m[5]; 
			    
			m[8 ] = _m[6]; 
			m[9 ] = _m[7]; 
			m[10] = _m[8];
			
		return this;
	}
	
	public Matrix4x4 setIdentity(){
		m[0 ] = 1; m[1 ] = 0; m[2 ] = 0; m[3 ] = 0;
		m[4 ] = 0; m[5 ] = 1; m[6 ] = 0; m[7 ] = 0;
		m[8 ] = 0; m[9 ] = 0; m[10] = 1; m[11] = 0;
		m[12] = 0; m[13] = 0; m[14] = 0; m[15] = 1;
				
		return this;
	}
	
	public Matrix4x4 setNull(){
		m[0 ] = 0; m[1 ] = 0; m[2 ] = 0; m[3 ] = 0;
		m[4 ] = 0; m[5 ] = 0; m[6 ] = 0; m[7 ] = 0;
		m[8 ] = 0; m[9 ] = 0; m[10] = 0; m[11] = 0;
		m[12] = 0; m[13] = 0; m[14] = 0; m[15] = 0;
				
		return this;
	}
	
	public Matrix4x4 setProjection(float far,float near,float w,float h,float fov){
		near = near < .001f ? .001f:near; //NEAR DARF NICHT ZU KLEIN SEIN!!!!!!!! 3 TAGE DEBUGGING ...
		float aspectRatio = w/h;
		float scaleX = (float) ((1f/tan(toRadians(fov/2f))));
		float scaleY = scaleX*aspectRatio;
		float len = far-near;
//		
//		m[0 ] = scaleX; m[1 ] = 0; 		m[2 ] = 0; 					m[3 ] = 0;
//		m[4 ] = 0; 		m[5 ] = scaleY; m[6 ] = 0; 					m[7 ] = 0;
//		m[8 ] = 0;		m[9 ] = 0; 		m[10] = -((far+near)/len); 	m[11] = -((2 * far * near) / len);
//		m[12] = 0; 		m[13] = 0; 		m[14] = -1; 				m[15] = 0;
//		
		float rad = (float) toRadians(fov/2);
		
		float sin = (float) sin(rad), ctan =  (float) (cos(rad) / sin),dZ = far-near;
		setIdentity();
		m[0] = ctan / aspectRatio;
		m[5] = ctan;
		m[10] = -((far+near)/dZ);
		m[11] = -2 * near*far / dZ;
		m[14] = -1;
		m[15] = 0;
//
//		__gluMakeIdentityf(matrix);
//
//		matrix.put(0 * 4 + 0, cotangent / aspect);
//		matrix.put(1 * 4 + 1, cotangent);
//		matrix.put(2 * 4 + 2, - (zFar + zNear) / deltaZ);
//		matrix.put(2 * 4 + 3, -1);
//		matrix.put(3 * 4 + 2, -2 * zNear * zFar / deltaZ);
//		matrix.put(3 * 4 + 3, 0);
//
//		glMultMatrix(matrix);
		return this;
	}
	
	@Override
	public String toString() {
		return super.toString() + "\n"
				+ "[" + m[0 ] + " ," + m[1 ] + " ," + m[2 ] + " ," + m[3 ] + " ]\n"
				+ "[" + m[4 ] + " ," + m[5 ] + " ," + m[6 ] + " ," + m[7 ] + " ]\n"
				+ "[" + m[8 ] + " ," + m[9 ] + " ," + m[10] + " ," + m[11] + " ]\n"
				+ "[" + m[12] + " ," + m[13] + " ," + m[14] + " ," + m[15] + " ]\n";
	}

	@Override
	public void loadValueToUniform(int uniformID) {
		GL20.glUniformMatrix4fv(uniformID, true, BufferUtil.createFilpedFloatbuffer(m));
	}

	public Matrix4x4 makeView(Vector3 rotation, Vector3 up, Vector3 forward, Vector3 position) {
		Matrix4x4 rot = new Matrix4x4();
		rot.rotate(rotation.x,-rotation.y,rotation.z);		
		Vector4 f_ = rot.mul(new Vector4(forward.x, forward.y, forward.z, 0));
//		rot.setIdentity().rotate(0,0,rotation.z);		
//		Vector4 u_ = rot.mul(new Vector4(up.x, up.y, up.z, 0));
		
//		makeView(new Vector3(u_.x, u_.z, u_.y), new Vector3(f_.x, f_.y, f_.z), position);
		makeView(up, new Vector3(f_.x, f_.y, f_.z), position);
		return this;
	}
	
}
