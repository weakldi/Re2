package engine.core;

import engine.util.BufferUtil;
import engine.util.math.Matrix4x4;
import engine.util.math.Vector3;

import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;

public abstract class RenderbleObject {
	private Vector3 pos;
	private Vector3 rot;
	private Vector3 scale;
	private Matrix4x4 transMat;
	protected boolean update = true;
	protected int meshID = -1;
	
	public RenderbleObject(Vector3 pos, Vector3 rot, Vector3 scale) {
		super();
		this.pos = pos;
		this.rot = rot;
		this.scale = scale;
		
		transMat = new Matrix4x4().setIdentity();
	}

	public abstract void prepare(Shader shader);
	public abstract void update();
	
	public final void render(Shader shader){
		if(update)
			_update();
		FloatBuffer buffer = BufferUtil.createFilpedFloatbuffer(transMat.m);
		glUniformMatrix4fv(shader.getUniforms().get("m"), true, buffer);
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

	public void _update(){
		update();
		 
		transMat.setIdentity().translate(pos).rotate(rot).scale(scale);
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
	
	
}
