package engine.core;

import engine.util.math.Matrix4x4;
import engine.util.math.Vector3;

import static org.lwjgl.opengl.GL20.*;

public abstract class RenderbleObject {
	private Vector3 pos;
	private Vector3 rot;
	private Vector3 scale;
	private Matrix4x4 transMat;
	private boolean update = true;
	private int meshID = -1;
	
	public RenderbleObject(Vector3 pos, Vector3 rot, Vector3 scale) {
		super();
		this.pos = pos;
		this.rot = rot;
		this.scale = scale;
		
		transMat = new Matrix4x4().setIdentity();
	}

	public abstract void prepare(Shader shader);
	
	public final void render(Shader shader){
		if(update)update();
		glUniformMatrix4fv(shader.getUniforms().get("m"), false, transMat.getFloatBuffer());
	}
	
	public int getMeshID() {
		return meshID;
	}

	public void setMeshID(int meshID) {
		if(meshID>=0)
		if(this.meshID!=meshID){
			Renderengine.getInstance().getMeshes().get(meshID).getObjects().add(this);
			if(this.meshID!=-1){
				Renderengine.getInstance().getMeshes().get(this.meshID).getObjects().remove(this);
			}
		}
		this.meshID = meshID;
	}

	public boolean isUpdate() {
		return update;
	}

	public void update(){
		Vector3 trueScale = new Vector3(scale.x, scale.y*Renderengine.getInstance().getW().getAspectRatio(), scale.z);
		System.out.println(trueScale);
		
		Matrix4x4 translation = new Matrix4x4().setIdentity().translate(pos);
		Matrix4x4 rotation = new Matrix4x4().setIdentity().rotate(rot);
		Matrix4x4 scale = new Matrix4x4().setIdentity().scale(trueScale);
		Matrix4x4.mul(scale,Matrix4x4.mul(translation, rotation) , transMat);
		System.out.println(transMat);
		update = false;
	}
	
	public Vector3 getPos() {
		return pos;
	}
	public void setPos(Vector3 pos) {
		update = true;
		this.pos = pos;
	}
	public Vector3 getRot() {
		return rot;
	}
	public void setRot(Vector3 rot) {
		update = true;
		this.rot = rot;
	}
	public Vector3 getScale() {
		return scale;
	}
	public void setScale(Vector3 scale) {
		update = true;
		this.scale = scale;
	}
	public Matrix4x4 getTransMat() {
		return transMat;
	}
	public void setTransMat(Matrix4x4 transMat) {
		this.transMat = transMat;
	}
}
