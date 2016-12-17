#version 330 core

layout(location = 0) in vec3 pos;
layout(location = 1) in vec3 normal;
layout(location = 2) in vec2 uv;
layout(location = 3) in vec3 tangent;

uniform mat4 m;
uniform mat4 v;
uniform mat4 p;
uniform mat4 pv;
out vec2 pass_uv;
out mat3 tbn;
out vec3 position;
out vec3 n;
void main(){
	pass_uv = uv;
	n = (m * vec4(normal,0)).xyz;
	mat4 m_ = mat4( 1,0,0,-0,
				    0,1,0,-0,
				    0,0,1,0,
				    0,0,-10,1);
	float sx  = ((1f/tan(radians(45/2f))));
	float sy = sx * (600/800);
	float v1 = (100-1)/99;
	float v2 = 0;
	mat4 p_ = mat4( 1,0,0,0,
					0,1,0,0,
					0,0,1,-1,
					0,0,0,0);
	gl_Position = vec4((p * v *   m* vec4(pos.xyz,1)).xyzw);
	position = (m *  vec4(pos,1)).xyz;
	n = n*0.5+0.5;
}