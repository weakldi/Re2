

import engine.core.Renderengine;
import engine.util.math.Matrix4x4;
import engine.util.math.Vector3;

public class Main {
	public Main() {
		// TODO Auto-generated constructor stub
	}
	public static void main(String[] args) {
		Renderengine re = Renderengine.getInstance();
		Matrix4x4 rotTest = new Matrix4x4().setIdentity();
		//rotTest.rotate(new Vector3(1,1,1), new Vector3(0, 1, 0));
		System.out.println(rotTest);
		//re.start();
		
	}
}
