



import engine.core.Renderengine;
import engine.util.math.Matrix4x4;
import engine.util.math.Vector3;

public class Main {
	
	public static void main(String[] args) {
		Renderengine re = Renderengine.getInstance();
		
		re.start();
		Vector3 pos = new Vector3(0, 0, 10);
		Vector3 rot = new Vector3(0, 30, 0);
		Vector3 scale = new Vector3(1, 1, 0.5f);
		System.out.println(new Matrix4x4().setIdentity().translate(pos).rotate(rot).scale(scale));
	
	}
}
