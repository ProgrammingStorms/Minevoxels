package net.minevoxels.core.renderer;

import static org.lwjgl.opengl.GL46.*;
import java.nio.charset.StandardCharsets;
import java.io.*;

public class ShaderManager {
    private int program;

    private String loadShader(String filename) {
        String fullPath = "/net/minevoxels/shaders/" + filename + ".glsl";
        StringBuilder builder = new StringBuilder();

        try(InputStream stream = getClass().getResourceAsStream(fullPath)) {
            if(stream != null) {
                try(BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
                    String line;
                    while((line = reader.readLine()) != null) {
                        builder.append(line).append("\n");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }

        return builder.toString();
    }

    private int initShader(int shaderType, String filename) {
        int shader = glCreateShader(shaderType);
        glShaderSource(shader, loadShader(filename));
        glCompileShader(shader);

        if(glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
            System.err.println("SHADER COMPILE ERROR: " + glGetShaderInfoLog(shader));
        }

        return shader;
    }

    public void initBasicProgram(String type) {
        int vertexShader = initShader(GL_VERTEX_SHADER, "vertex" + type);
        int fragmentShader = initShader(GL_FRAGMENT_SHADER, "fragment" + type);

        program = glCreateProgram();

        glAttachShader(program, vertexShader);
        glAttachShader(program, fragmentShader);

        glLinkProgram(program);

        if(glGetProgrami(program, GL_LINK_STATUS) == GL_FALSE) {
            System.err.println("PROGRAM LINK ERROR: " + glGetProgramInfoLog(program));
        }

        glValidateProgram(program);

        if(glGetProgrami(program, GL_VALIDATE_STATUS) == GL_FALSE) {
            System.err.println("PROGRAM VALIDATE ERROR: " + glGetProgramInfoLog(program));
        }

        glDetachShader(program, vertexShader);
        glDetachShader(program, fragmentShader);

        glDeleteShader(vertexShader);
        glDeleteShader(fragmentShader);
    }

    public void useProgram() {
        glUseProgram(program);
    }
    public void unUseProgram() {
        glUseProgram(0);
    }
    public int getProgram() {
        return program;
    }
    public void destroyProgram() {
        glDeleteProgram(program);
    }
}
