#version 330
in vec2 p_uv;

out vec4 color;

uniform sampler2D diffuse;
uniform vec3 lc;

void main(){
	color = vec4(lc * texture2D(diffuse,p_uv).rgb,1);
}