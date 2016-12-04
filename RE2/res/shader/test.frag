#version 400
in vec2 passUV;
out vec4 o;
uniform vec3 color; 
uniform sampler2D textureSampler;
void main()
{
    
    o = texture2D(textureSampler,passUV);
}
