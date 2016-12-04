package engine.core;

import engine.util.math.Matrix4x4;
import engine.util.math.Vector3;

public class Camera {
	private Matrix4x4 	projection 	= new Matrix4x4().setProjection(100, 0, 45, 800, 800);
	private Matrix4x4 	viewMatrix 	= new Matrix4x4().setIdentity();
	private Matrix4x4	vpMat		= new Matrix4x4().setIdentity();
	private Vector3		deltaRot;
	private Vector3 	position;
	private Vector3 	rotation;
	private Vector3		forward;
	private Vector3		up;
	
	public Camera() {
		super();
		projection 	= new Matrix4x4().setProjection(100, 0, 45, 800, 800);
		viewMatrix 	= new Matrix4x4().setIdentity();                      
		position = new Vector3();                                                         
		rotation = new Vector3();    
		up = new Vector3(0, 1, 0);
		forward = new Vector3(0, 0, 1);
		deltaRot = new Vector3(0, 0, 0);
	}
	
	

	public Vector3 getPosition() {
		return position;
	}



	public void setPosition(Vector3 position) {
		this.position = position;
	}



	public Vector3 getRotation() {
		return rotation;
	}
	
	

	public Matrix4x4 getVpMat() {
		return vpMat;
	}



	public void setVpMat(Matrix4x4 vpMat) {
		this.vpMat = vpMat;
	}



	public void setRotation(Vector3 rotation) {
		deltaRot.y = -(rotation.x - this.rotation.x);
		deltaRot.x = rotation.y - this.rotation.y;
		deltaRot.z = rotation.z - this.rotation.z;
		this.rotation = rotation;
		
	}

	public void rotate(Vector3 rot){
		deltaRot.y = -rot.x;
		deltaRot.x = rot.y;
		deltaRot.z = rot.z;
		rotation.add(rotation);
	}

	public void update(){
		viewMatrix.makeTransmat(deltaRot,up, forward, position);
		System.out.println(vpMat);
		Matrix4x4.mul(projection, viewMatrix, vpMat);
		System.out.println(vpMat);
		deltaRot.setNull();
	}
	
	public Vector3 getForward() {
		return forward;
	}



	public void setForward(Vector3 forward) {
		this.forward = forward;
	}



	public Vector3 getUp() {
		return up;
	}



	public void setUp(Vector3 up) {
		this.up = up;
	}



	public Matrix4x4 getViewMatrix() {
		return viewMatrix;
	}

	public void setViewMatrix(Matrix4x4 viewMatrix) {
		this.viewMatrix = viewMatrix;
	}

	public Matrix4x4 getProjection() {
		return projection;
	}

	public void setProjection(Matrix4x4 projection) {
		this.projection = projection;
	}
	
	public Vector3 getPoinerLocation(){
		return new Vector3(position.x, position.y, position.z).add(forward);
	}
	
	public void lookAt(Vector3 pos){
		forward.x = pos.x - position.x;
		forward.y = pos.y - position.y;
		forward.z = pos.z - position.z;
	}

}