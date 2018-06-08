#version 330

layout (location = 0) in vec3 inVertexPosition;
//layout (location = 1) in vec3 inVertexColour;
layout (location = 1) in vec2 textureCoordinate;

//uniform mat4 projectionMatrix;
//uniform mat4 modelMatrix;
//uniform mat4 viewMatrix;

//out vec3 outVertexColour;
out vec2 outTexCoord;

uniform mat4 projectionMatrix;
uniform mat4 modelMatrix;
uniform mat4 viewMatrix;

void main()
{
    gl_Position = vec4(inVertexPosition, 1.0) * modelMatrix * viewMatrix * projectionMatrix;
    //outVertexColour = inVertexColour;
    outTexCoord = textureCoordinate;
}