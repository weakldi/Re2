#version 330

layout (location = 0) in vec2 pos;
//layout (location = 1) in vec2 uv;

out vec2 p_uv;

void main(){
	p_uv = pos*0.5+0.5;
	gl_Position = vec4(pos,0,1);
}