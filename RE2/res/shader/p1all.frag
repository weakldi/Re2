#version 330 core
in vec2 pass_uv;
in mat3 tbn;
in vec3 position;
in vec3 norm;

out vec4 diffuse;
out vec4 normal;
out vec3 pos;

uniform sampler2D model_Matirial_Texture;

void main(){
	diffuse = vec4 (texture2D(model_Matirial_Texture,pass_uv).xyz,1);
	normal = vec4(normalize(norm*0.5+0.5),1);
	pos = position;
}