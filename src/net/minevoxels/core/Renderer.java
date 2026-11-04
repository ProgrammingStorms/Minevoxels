package net.minevoxels.core;

import net.minevoxels.core.renderer.MeshManager;
import net.minevoxels.core.renderer.ShaderManager;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

public class Renderer {
    private MeshManager mesh = new MeshManager();
    private ShaderManager shader = new ShaderManager();

    private float[] vertices = {
            0.5f, 0.5f, 0.0f,
            -0.5f, 0.5f, 0.0f,
            0.5f, -0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f
    };

    private int[] indices = {
            0, 3, 2,
            3, 0, 1
    };

    public void createRenderer() {
        mesh.addMeshData(vertices, indices);

        mesh.flushMesh();

        shader.initBasicProgram("vertexBasic", "fragmentBasic");
    }

    public void renderWorld() {
        glClearColor(0.3f, 0.6f, 0.9f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT);

        shader.useShaderProgram();
        mesh.drawMesh();
    }

    public void destroyRenderer() {
        shader.destroyShaderProgram();
        mesh.destroyMeshData();
    }
}
