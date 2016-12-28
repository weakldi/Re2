#version 330
in vec2 p_uv;

out vec4 color;

uniform sampler2D diffuse;
uniform sampler2D normal;
uniform sampler2D pos;
uniform sampler2D depth;

void main(){
	color.a = 1;
	if(p_uv.x<0.5&& p_uv.y >=0.5){
		p_uv.y-0.5; 
		color.rgb = texture2D(diffuse,p_uv * 2).rgb;
	}else if(p_uv.x>=0.5&& p_uv.y >=0.5){
		color.rgb = texture2D(normal,(p_uv-0.5)*2.0).rgb;
	}else if(p_uv.x<0.5&& p_uv.y <0.5){
		color.rgb = texture2D(pos,p_uv*2.0).rgb;
	}else if(p_uv.x>=0.5&& p_uv.y <0.5){
		p_uv.x-0.5; 
		color.rgb = texture2D(depth,p_uv*2.0).rgb;
	}
	
}