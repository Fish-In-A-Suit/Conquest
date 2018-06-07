#version 330

//in  vec3 outVertexColour;
in vec2 outTexCoord;
layout (location = 0) out vec4 fragColour;

uniform sampler2D texture_sampler;

void main()
{
	//fragColour = vec4(outVertexColour, 1.0);
	fragColour = texture(texture_sampler, outTexCoord);
}