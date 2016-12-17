
#version 330 core
in vec2 pass_uv;
in mat3 tbn;
in vec3 position;
in vec3 n;
out vec4 diffuse;



uniform sampler2D model_Matirial_Texture;

void main(){
	diffuse = vec4 (n,1);
	
}