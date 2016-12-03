package engine.core;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL40.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL15.*;

public class Shader {
	private final int programID;
	private int vID;
	private int fID;
	private HashMap<String, Integer> uniformNames; 
	private HashMap<Integer, UniformValue> uniforms;
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
		uniforms = new HashMap<>();
		uniformNames = new HashMap<>();
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
			getUniforms(data);
			return shaderID;
		}
		return 0;
	}
	
	private void getUniforms(String data){
		String[] liens = data.split("\n");
		for (String l : liens) {
			if(l.startsWith("uniform")){
				String uName = l.split("\\s*")[2].replaceAll(";", "");
				uniformNames.put(uName,glGetUniformLocation(programID, uName));
			}
		}
	}
	
	public void addUniformValue(String uniformName,UniformValue v){
		if(uniformNames.containsKey(uniformName)){
			uniforms.put(uniformNames.get(uniformName), v);
		}
	}
	
	public void updateB(){
		bind();
		Set<Integer> keySet = uniforms.keySet();
		for (Integer id : keySet) {
			uniforms.get(id).loadValueToUniform(id);
		}
	}
	
	public void update(){
		Set<Integer> keySet = uniforms.keySet();
		for (Integer id : keySet) {
			uniforms.get(id).loadValueToUniform(id);
		}
	}
	
	public HashMap<String, Integer> getUniforms(){
		return uniformNames;
	}
	
	public void bind(){
		glUseProgram(programID);
	}
	
	public static void unbind(){
		glUseProgram(GL_NONE);
	}
	
	public int getProgramID(){
		return programID;
	}
}
