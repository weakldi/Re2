package engine.core;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL40.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL15.*;

public class Shader {
	private int programID;
	private int vID;
	private int fID;
	public Shader(String vertex,String fragment){
		programID = glCreateProgram();
		vID = loadShaderProgram(vertex, GL_VERTEX_SHADER);
		fID = loadShaderProgram(fragment, GL_FRAGMENT_SHADER);
		
		glAttachShader(programID, vID);
		glAttachShader(programID, fID);
		
		glLinkProgram(programID);
		if(glGetProgrami(programID,GL_LINK_STATUS)==0){
			System.out.println("ERROR " + glGetProgramInfoLog(programID, 1000));
			System.exit(-1);
		}
		
	}
	
	private int loadShaderProgram(String data, int type){
		int shaderID = glCreateShader(type);
		if(shaderID>0) {
			glShaderSource(shaderID, data);
			glCompileShader(shaderID);
			int shaderStatus = glGetShaderi(shaderID, GL_COMPILE_STATUS);
			if( shaderStatus == GL_FALSE)
			{
				throw new IllegalStateException("compilation error " + glGetShaderInfoLog(shaderID, 1000));
			}
			return shaderID;
		}
		return 0;
	}
	
	public void bind(){
		glUseProgram(programID);
	}
	
	public static void unbind(){
		glUseProgram(GL_NONE);
	}
}
