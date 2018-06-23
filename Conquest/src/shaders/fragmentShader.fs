#version 330

//in  vec3 outVertexColour;
in vec2 outTexCoord;
layout (location = 0) out vec4 fragColour;

uniform sampler2D texture_sampler;
uniform vec3 colour;
uniform int useColour;

void main()
{
	if (useColour == 1) {
	    fragColour = vec4(colour, 1);
	} else {
	    fragColour = texture(texture_sampler, outTexCoord);
    }
}