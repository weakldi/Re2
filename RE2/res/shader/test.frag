#version 400
in vec2 passUV;
out vec4 o;
uniform vec3 color; 
uniform sampler2D textureSampler;
void main()
{
    
    o = vec4(1,0,1,1);
    //o = texture2D(textureSampler,passUV);
}
