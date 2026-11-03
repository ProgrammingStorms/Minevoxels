package net.minevoxels.core.renderer;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL46.*;

public class UniformManager {
    public int getUniformLoc(String name, int program) {
        return glGetUniformLocation(program, name);
    }

    public void setUniform(int loc, Object value) {
        if(value instanceof Integer val) {
            glUniform1i(loc, val);
        } else if(value instanceof Boolean val) {
            glUniform1i(loc, val ? 1 : 0);
        } else if(value instanceof Float val) {
            glUniform1f(loc, val);
        } else if(value instanceof Vector2f vec2) {
            glUniform2f(loc, vec2.x, vec2.y);
        } else if(value instanceof Vector3f vec3) {
            glUniform3f(loc, vec3.x, vec3.y, vec3.z);
        } else if(value instanceof Matrix4f mat4) {
            FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
            mat4.get(buffer);
            glUniformMatrix4fv(loc, false, buffer);
        }
    }
}
