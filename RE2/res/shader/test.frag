
#version 330 core
in vec2 pass_uv;
in mat3 tbn;
in vec3 position;
in vec3 n;

out vec4 diffuse;
out vec4 normal;
out vec3 pos;



uniform sampler2D model_Matirial_Texture;

void main(){
	vec3 sd = vec3(1,position.z,0);
	sd = normalize(sd);
	diffuse = vec4 (n,1);
	normal = vec4(n,1);
	pos = position;
}