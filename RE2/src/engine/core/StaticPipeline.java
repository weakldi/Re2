package engine.core;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_DEPTH_COMPONENT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_ONE;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_RGBA16;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL14.GL_DEPTH_COMPONENT24;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT0;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT1;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT2;
import static org.lwjgl.opengl.GL30.GL_DEPTH_ATTACHMENT;
import static org.lwjgl.opengl.GL30.GL_RGBA32F;

import java.util.ArrayList;
import java.util.Map;

import engine.geometry.Mesh;
import engine.geometry.VAO;
import engine.light.AmbientLight;
import engine.light.DirLight;
import engine.light.Light;
import engine.light.PointLight;
import engine.util.math.Vector3;

public class StaticPipeline extends RenderPipeline{
	private Shader gBuffer,
					dirLight,
					pointLight,
					spotLight,
					ambientLight,
					allBuffers;
	private FBO gBufferFBO, target;
	private VAO screen;
	
	
	private AmbientLight ambient;
	private ArrayList<DirLight> dirLightList;
	private ArrayList<DirLight> spotLightList;
	private ArrayList<PointLight> pointLightList;
	
	public StaticPipeline(FBO target) {
		this.target = target;
		gBuffer = new Shader(FileLoader.loadFile("res/shader/p1all.vert"),FileLoader.loadFile("res/shader/p1all.frag"));
		gBuffer.bind();
		glUniform1i(gBuffer.getUniforms().get("model_Matirial_Texture"), 0);
		
		ambientLight = new Shader(FileLoader.loadFile("res/shader/p2.vert"),FileLoader.loadFile("res/shader/p2a.frag"));
		ambientLight.bind();
		glUniform1i(ambientLight.getUniforms().get("diffuse"), 0);
		
		allBuffers = new Shader(FileLoader.loadFile("res/shader/p2.vert"), FileLoader.loadFile("res/shader/p2_allBuffers.frag"));
		allBuffers.bind();
		
		glUniform1i(allBuffers.getUniforms().get("diffuse"), 0);
		glUniform1i(allBuffers.getUniforms().get("normal"), 1);
		glUniform1i(allBuffers.getUniforms().get("pos"), 2);
		glUniform1i(allBuffers.getUniforms().get("depth"), 3);
		
		dirLight = new Shader(FileLoader.loadFile("res/shader/p2.vert"), FileLoader.loadFile("res/shader/p2dl.frag"));
		dirLight.bind();
		
		glUniform1i(dirLight.getUniforms().get("diffuse"), 0);
		glUniform1i(dirLight.getUniforms().get("normal"), 1);
		glUniform1i(dirLight.getUniforms().get("pos"), 2);
		glUniform1i(dirLight.getUniforms().get("depth"), 3);
		
		pointLight = new Shader(FileLoader.loadFile("res/shader/p2.vert"), FileLoader.loadFile("res/shader/p2pl.frag"));
		pointLight.bind();
		
		glUniform1i(pointLight.getUniforms().get("diffuse"), 0);
		glUniform1i(pointLight.getUniforms().get("normal"), 1);
		glUniform1i(pointLight.getUniforms().get("pos"), 2);
		glUniform1i(pointLight.getUniforms().get("depth"), 3);
		Shader.unbind();
		
		gBufferFBO  = new FBO(target.getWidth(),target.getHeight(),
				new int[]{GL_COLOR_ATTACHMENT0	,GL_COLOR_ATTACHMENT1	,GL_COLOR_ATTACHMENT2	,GL_DEPTH_ATTACHMENT},
				new int[]{GL_RGBA				,GL_RGBA				,GL_RGBA				,GL_DEPTH_COMPONENT},
				new int[]{GL_RGBA16,			GL_RGBA32F				,GL_RGBA32F				,GL_DEPTH_COMPONENT24}
				);
		
		screen = new VAO();
		
		screen.addDataStatic(0, 2, new float[]{
				-1,-1,
				1,-1,
				1,1,
				-1,1
				
		});
//		screen.addDataStatic(1, 2, new float[]{
//				0,0,
//				1,0,
//				1,1,
//				0,1
//		});
		screen.addElementArray(new int[]{
				0,1,2,
				2,3,0
		});
		
		
		dirLightList = new ArrayList<>();
		pointLightList = new ArrayList<>();
		ambient = new AmbientLight(new Vector3(0.01f, 0.01f, 0.01f));
		
	}
	
	public void render3D(Map<Integer, Mesh> meshMap,Camera cam){
		gBufferFBO.bind();
		gBufferFBO.clear();
		gBuffer.bind();
		gBuffer.update(cam);
		for (Mesh mesh : meshMap.values()) {
			mesh.render(gBuffer);
		}
		Shader.unbind();
		gBufferFBO.unbind();
		
		target.bind();
		target.clear();
		screen.bind();
		
		
		ambientLight.bind();
		ambient.prepareRenderer(ambientLight);
		gBufferFBO.getTexture(0).bind(0);
		gBufferFBO.getTexture(1).bind(1);
		gBufferFBO.getTexture(2).bind(2);
		gBufferFBO.getTexture(3).bind(3);
		screen.drawE();
		glDisable(GL_DEPTH_TEST);
		glEnable(GL_BLEND);
		glBlendFunc(GL_ONE, GL_ONE);
		dirLight.bind();
		for (DirLight dirLight : dirLightList) {
			dirLight.prepareRenderer(this.dirLight);
			screen.drawE();
		}
		pointLight.bind();
		for (PointLight pLight : pointLightList) {
			pLight.prepareRenderer(this.pointLight);
			screen.drawE();
		}
		
		glDisable(GL_BLEND);
		glEnable(GL_DEPTH_TEST);
		
		
		screen.unbind();
		target.unbind();
		
		
	}
		
	public void render2D(){
		
	}
	
	public void addLight(Light light){
		if(light instanceof DirLight){
			dirLightList.add((DirLight)light);
		}else if(light instanceof PointLight){
			pointLightList.add((PointLight)light);
		}else if(light instanceof AmbientLight){
			ambient = (AmbientLight) light;
		}
	}
	
}
