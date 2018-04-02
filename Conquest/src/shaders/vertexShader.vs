#version 330

layout (location =0) in vec3 position;
layout (location =1) in vec3 inColour;

out vec3 exColour;

void main()
{
    // gl_Position = vec4(position.x, position.y, position.z, 1.0);
	gl_Position = vec4(position, 1.0);
    exColour = vec3(position.x + 0.5, 1.0, position.y + 0.5);
}
