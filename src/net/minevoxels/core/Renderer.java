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
        mesh.addVertices(vertices);
        mesh.addIndices(indices);

        mesh.createBuffers();

        shader.initBasicProgram("Basic");
    }

    public void renderWorld() {
        glClearColor(0.3f, 0.6f, 0.9f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT);

        shader.useProgram();
        mesh.bindVAO();
        GL11.glDrawElements(GL_TRIANGLES, mesh.getVertexCount(), GL_UNSIGNED_INT, 0);
        mesh.unbindVAO();
        shader.unUseProgram();
    }

    public void destroyRenderer() {
        shader.destroyProgram();
        mesh.destroyBuffers();
    }
}
