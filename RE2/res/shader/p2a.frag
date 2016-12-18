#version 330
in vec2 p_uv;

out vec4 color;

uniform  sampler2D diffuse;


void main(){
	color = vec4(texture2D(diffuse,p_uv).rgb,1);
}