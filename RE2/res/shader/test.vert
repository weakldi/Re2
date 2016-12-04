#version 330

layout(location = 0)in vec3 vertexPosition;
layout(location = 2)in vec3 normal;
layout(location = 1) in vec2 uv;
uniform mat4 vp;
uniform mat4 m;

out vec2 passUV;
void main()
{
    passUV = uv;
    gl_Position =       vp *  m * vec4(vertexPosition,1.0);

   // gl_Position = vec4(vertexPosition, 1.0);
}
