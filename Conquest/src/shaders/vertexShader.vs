#version 330

layout (location = 0) in vec3 inVertexPosition;
layout (location = 1) in vec3 inVertexColour;

out vec3 outVertexColour;

void main()
{
    // gl_Position = vec4(inVertexPosition.x, inVertexPosition.y, inVertexPosition.z, 1.0);
	gl_Position = vec4(inVertexPosition, 1.0);
    outVertexColour = inVertexColour;
}
