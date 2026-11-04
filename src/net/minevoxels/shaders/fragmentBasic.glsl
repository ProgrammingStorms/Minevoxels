#version 460 core

in vec3 combinedTexID;

out vec4 outColor;

uniform sampler2DArray texArray;

void main() {
    outColor = texture(texArray, combinedTexID);
}
