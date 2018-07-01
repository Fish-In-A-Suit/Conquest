#version 330

in vec2 outTexCoord;
in vec3 surfaceNormal;
in vec3 toLightVector;

layout (location = 0) out vec4 fragColour;

uniform sampler2D texture_sampler;
uniform vec3 colour;
uniform vec3 lightColour;
uniform int useColour;

void main() {
  
    float ambientStrength = 0.1f;
    vec3 ambient = ambientStrength * lightColour;
    
    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitLightNormal = normalize(toLightVector);
    
    float diffuseDot = dot(unitNormal, unitLightNormal);
    float diffuseBrightness = max(diffuseDot, 0.0);
    vec3 diffuse = diffuseBrightness * lightColour;
    
	if (useColour == 1) {
	    fragColour = vec4(ambient, 1.0) * vec4(colour, 1);
	} else {
	    fragColour = (vec4(ambient, 1.0) + vec4(diffuse, 1.0)) * texture(texture_sampler, outTexCoord);
    }
}