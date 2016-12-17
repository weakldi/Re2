package engine.core;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_NONE;
import static org.lwjgl.opengl.GL20.*;

import java.util.HashMap;
import java.util.Set;

public class Shader {
	private final int programID;
	private int vID;
	private int fID;
	private HashMap<String, Integer> uniformNames; 
	private HashMap<Integer, UniformValue> uniforms;
	
	public Shader(String vertex,String fragment){
		uniforms = new HashMap<>();
		uniformNames = new HashMap<>();
		programID = glCreateProgram();
		vID = loadShaderProgram(vertex, GL_VERTEX_SHADER);
		fID = loadShaderProgram(fragment, GL_FRAGMENT_SHADER);
		
		glAttachShader(programID, vID);
		glAttachShader(programID, fID);
		
		glLinkProgram(programID);
		getUniforms(vertex);
		getUniforms(fragment);
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
	
	private void getUniforms(String data){
		String[] liens = data.replaceAll("[\n\r]{1,}", "").split(";");
		for (String l : liens) {
			System.out.println("L: " + l);
			l = l.replaceAll("[\n\r]{1,}", "");
			if(l.matches("\\s*uniform.*")){
				l = l.replaceAll("\\s*uniform", "u");
				String uName = l.split("\\s{1,}")[2].replaceAll(";", "");
				System.out.println("Uniform " + uName);
				uniformNames.put(uName,glGetUniformLocation(programID, uName));
			}
		}
	}
	
	public void addUniformValue(String uniformName,UniformValue v){
		if(uniformNames.containsKey(uniformName)){
			System.out.println(uniformName + " " + uniformNames.get(uniformName));
			uniforms.put(uniformNames.get(uniformName), v);
		}
	}
	
	public void updateB(){
		glUseProgram(programID);
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
