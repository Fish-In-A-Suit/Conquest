#version 330

layout (location = 0) in vec3 inVertexPosition;
layout (location = 1) in vec3 inVertexColour;

out vec3 outVertexColour;

uniform mat4 translationMatrix;
uniform mat4 rotationMatrix;
uniform mat4 projectionMatrix;

mat4 modelMatrix = rotationMatrix*translationMatrix;

void main()
{
	//gl_Position = vec4(inVertexPosition, 1.0) * rotationMatrix * translationMatrix * projectionMatrix;
    gl_Position = vec4(inVertexPosition, 1.0) * modelMatrix * projectionMatrix;
    outVertexColour = inVertexColour;
}
