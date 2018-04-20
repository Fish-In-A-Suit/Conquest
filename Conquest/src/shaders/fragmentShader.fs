#version 330

in  vec3 outVertexColour;
layout (location = 0) out vec4 fragColour;

void main()
{
	fragColour = vec4(outVertexColour, 1.0);
}