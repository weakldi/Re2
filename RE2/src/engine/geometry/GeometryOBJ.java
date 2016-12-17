package engine.geometry;

import engine.core.RenderbleObject;
import engine.core.Shader;
import engine.util.math.Vector3;

public class GeometryOBJ extends RenderbleObject{
	protected Vector3 dim;
	protected Vector3 objScale = new Vector3(1, 1, 1);
	public GeometryOBJ(Vector3 pos, Vector3 rot, Vector3 dim) {
		super(pos, rot, dim);
		this.dim = dim;
	}

	@Override
	public void prepare(Shader shader) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void update() {
		setScale(objScale);
	}

	
	public float getWidth() {
		return dim.x;
	}



	public void setWidth(float width) {
		this.dim.x = width;
		update = true;
	}



	public float getHeight() {
		return dim.y;
	}



	public void setHeight(float height) {
		this.dim.y = height;
		update = true;
	}

	public Vector3 getDim(){
		update = true;
		return dim;
	}

	public float getLenght() {
		return dim.z;
	}



	public void setLenght(float lenght) {
		this.dim.z = lenght;
		update = true;
	}
	
	@Override
	public void setScale(Vector3 scale) {
		super.setScale(new Vector3(scale.x*dim.x,scale.y*dim.y,scale.z*dim.z));
		objScale = scale;
		update = true;
	}
	
	public Vector3 getScale(){
		update = true;
		return objScale;
	}

	

}
