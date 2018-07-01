#version 330

layout (location = 0) in vec3 inVertexPosition;
layout (location = 1) in vec2 textureCoordinate;
layout (location = 2) in vec3 normalVector;

//uniform mat4 projectionMatrix;
//uniform mat4 modelMatrix;
//uniform mat4 viewMatrix;

out vec2 outTexCoord;
out vec3 surfaceNormal;
out vec3 toLightVector; //vector pointing towards the light source

uniform mat4 projectionMatrix;
uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform vec3 lightPosition;

mat4 MVP;

void main()
{
	vec4 worldPosition = vec4(inVertexPosition, 1.0) * modelMatrix; //changed position of a vertex if it is transformed
	
	MVP = modelMatrix * viewMatrix * projectionMatrix;
	gl_Position = vec4(inVertexPosition, 1.0) * MVP;
	
    //gl_Position = vec4(inVertexPosition, 1.0) * modelMatrix * viewMatrix * projectionMatrix;
    //outVertexColour = inVertexColour;
    
    outTexCoord = textureCoordinate;
	
	//0 for normalVector to remove effect of translation
	
	//use this in the case of a non-uniform scale
	//surfaceNormal = (vec4(normalVector, 0.0) * (transpose(inverse(modelMatrix)))).xyz; //0 to remove translation, since it shouldn't affect lighting
	
	//this is used in the case of a uniform scale:
	surfaceNormal = (vec4(normalVector, 0.0) * modelMatrix).xzy;
	toLightVector = lightPosition - worldPosition.xzy; 
}