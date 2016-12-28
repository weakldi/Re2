#version 330 core

layout(location = 0) in vec3 pos;
layout(location = 1) in vec3 normal;
layout(location = 2) in vec2 uv;
layout(location = 3) in vec3 tangent;

uniform mat4 m;
uniform mat4 v;
uniform mat4 p;

out vec2 pass_uv;
out mat3 tbn;
out vec3 position;
out vec3 norm;
void main(){
	pass_uv = uv;
	vec4 position4 = (m * vec4(pos,1));
	position = position4.xyz;
	mat4 m_ = m * mat4(0,1,0,0,
						0,0,1,0
						,1,0,0,0
		,0,0,0,-1);
	gl_Position = p*v* position4;
	vec3 n = normalize((m * vec4(normal,0.0)).xyz);
	vec3 t = normalize((m *vec4(tangent,0.0)).xyz);
	norm = n;
	t = normalize(t - dot(t,n)*n);
	vec3 b = cross(t,n);
	
	tbn = mat3(t,b,n);
	
}