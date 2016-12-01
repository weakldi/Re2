package engine.core;

import engine.util.math.Matrix4x4;

public class Camera {
	private Matrix4x4 projection 	= new Matrix4x4().setProjection(100, 0, 45, 800, 800);
	private Matrix4x4 viewMatrix 	= new Matrix4x4().setIdentity();
	
	
	
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
	};

}