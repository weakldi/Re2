
#version 330
in vec2 p_uv;

out vec4 color;

uniform sampler2D diffuse;
uniform sampler2D normal;
uniform sampler2D pos;
uniform sampler2D depth;

uniform vec3 lp;
uniform vec3 lc;

void main(){
	vec3 pos = texture2D(pos,p_uv).xyz;
	vec3 dir = lp-pos; 
	vec3 normal = normalize((texture2D(normal,p_uv).xyz*2-1));
	float intensity = dot(normalize(dir),normal);

	color.rgb = texture2D(diffuse,p_uv).rgb * vec3(lc)*intensity;
	//color.rgb = vec3(intensity);
	//color.rgb = normal;
}