#version 460 core

layout(location = 0) in vec3 aPos;
layout(location = 1) in vec2 aTexC;
layout(location = 2) in float aTexId;

out vec3 combinedTexID;

void main() {
    gl_Position = vec4(aPos, 1.0);
    combinedTexID = vec3(aTexC, aTexId);
}
