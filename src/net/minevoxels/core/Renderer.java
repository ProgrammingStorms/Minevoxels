package net.minevoxels.core;

import net.minevoxels.core.renderer.MeshManager;
import net.minevoxels.core.renderer.ShaderManager;
import net.minevoxels.core.renderer.TextureManager;
import net.minevoxels.core.renderer.UniformManager;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;

public class Renderer {
    private final MeshManager mesh = new MeshManager();
    private final ShaderManager shader = new ShaderManager();
    private final TextureManager texture = new TextureManager();
    private final UniformManager uniform = new UniformManager();
    private int texLoc;

    private final float[] vertices = {
            0.5f, 0.5f, 0.0f, 1.0f, 1.0f, 4.0f,
            -0.5f, 0.5f, 0.0f, 0.0f, 1.0f, 4.0f,
            0.5f, -0.5f, 0.0f, 1.0f, 0.0f, 4.0f,
            -0.5f, -0.5f, 0.0f, 0.0f, 0.0f, 4.0f
    };

    private final int[] indices = {
            0, 3, 2,
            3, 0, 1
    };

    public void createRenderer() {
        mesh.addMeshData(vertices, indices);
        mesh.flushMesh();
        shader.initBasicProgram("vertexBasic", "fragmentBasic");
        texture.initTextureArray();
        texLoc = uniform.getUniformLoc("texArray", shader.getShaderProgram());
    }

    public void renderWorld() {
        glClearColor(0.5f, 0.8f, 1.0f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT);

        shader.useShaderProgram(true);
        uniform.setUniform(texLoc, 0);
        texture.bindTextureId(0);
        mesh.drawMesh();
        texture.unbindTextureId(0);
        shader.useShaderProgram(false);
    }

    public void destroyRenderer() {
        texture.destroyTextureId();
        shader.destroyShaderProgram();
        mesh.destroyMeshData();
    }
}
