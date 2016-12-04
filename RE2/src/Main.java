



import engine.core.Renderengine;
import engine.util.math.Matrix4x4;
import engine.util.math.Vector3;

public class Main {
	
	public static void main(String[] args) {
		Renderengine re = Renderengine.getInstance();
		
		re.start();
		Vector3 b = new Vector3(1, 1, 0);
		b.normailze();
		Vector3 v = b.cross(new Vector3(0, 0, 1));
		v.normailze();
	
	}
}

//rotate 0 um y 