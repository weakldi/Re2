



import engine.core.Renderengine;
import engine.util.math.Matrix4x4;
import engine.util.math.Vector3;
import engine.util.math.Vector4;

public class Main {
	
	public static void main(String[] args) {
		Renderengine re = Renderengine.getInstance();
		
		re.start();
		Matrix4x4 p = new Matrix4x4(100,0,600,800,45);
		Matrix4x4 v = new Matrix4x4().makeView(new Vector3(0, 1, 0), new Vector3(0, 0, 1), new Vector3(0, 0, -100));
		Matrix4x4 m = new Matrix4x4().translate(0, 0, 10);
		System.out.println(p);
		System.out.println((v.mul(m)).mul(new Vector4(1, 1, (float) 10, 1)));
		System.out.println(v.mul(new Vector4(0, 0, (float) -0.5, 1)));
		
	}
}
