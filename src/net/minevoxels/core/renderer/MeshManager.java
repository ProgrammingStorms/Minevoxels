package net.minevoxels.core.renderer;

import org.lwjgl.system.MemoryUtil;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import static org.lwjgl.opengl.GL46.*;

public class MeshManager {
    private int vertexSize = 262144;
    private int indicesSize = 262144;
    private FloatBuffer vertices = MemoryUtil.memAllocFloat(vertexSize);
    private IntBuffer indices = MemoryUtil.memAllocInt(indicesSize);
    private int vertexCount;
    private int VAO;
    private int VBO;
    private int EBO;

    public void addMeshData(float[] dataVertices, int[] dataIndices) {
        vertices.put(dataVertices);
        indices.put(dataIndices);
        vertexCount += dataIndices.length;
    }

    public void flushMesh() {
        vertices.flip();
        indices.flip();

        VAO = glCreateVertexArrays();

        VBO = glCreateBuffers();
        glNamedBufferData(VBO, vertices, GL_DYNAMIC_DRAW);

        EBO = glCreateBuffers();
        glNamedBufferData(EBO, indices, GL_DYNAMIC_DRAW);

        glEnableVertexArrayAttrib(VAO, 0);
        glEnableVertexArrayAttrib(VAO, 1);
        glEnableVertexArrayAttrib(VAO, 2);

        glVertexArrayAttribBinding(VAO, 0, 0);
        glVertexArrayAttribFormat(VAO, 0, 3, GL_FLOAT, false, 0);

        glVertexArrayAttribBinding(VAO, 1, 0);
        glVertexArrayAttribFormat(VAO, 1, 2, GL_FLOAT, false, 3 * Float.BYTES);

        glVertexArrayAttribBinding(VAO, 2, 0);
        glVertexArrayAttribFormat(VAO, 2, 1, GL_FLOAT, false, 5 * Float.BYTES);

        glVertexArrayVertexBuffer(VAO, 0, VBO, 0, 6 * Float.BYTES);
        glVertexArrayElementBuffer(VAO, EBO);
    }

    public void flushUpdatedMesh() {
        vertices.flip();
        indices.flip();

        glNamedBufferSubData(VBO, 0, vertices);
        glNamedBufferSubData(EBO, 0, indices);
    }

    public void destroyMeshData() {
        MemoryUtil.memFree(vertices);
        MemoryUtil.memFree(indices);
        glDeleteBuffers(EBO);
        glDeleteBuffers(VBO);
        glDeleteVertexArrays(VAO);
    }

    public void drawMesh() {
        glBindVertexArray(VAO);
        glDrawElements(GL_TRIANGLES,  vertexCount, GL_UNSIGNED_INT, 0);
        glBindVertexArray(0);
    }

    public void clearMeshData() {
        vertices.clear();
        indices.clear();
        vertexCount = 0;
    }

    public int getVAO() {
        return VAO;
    }
}
