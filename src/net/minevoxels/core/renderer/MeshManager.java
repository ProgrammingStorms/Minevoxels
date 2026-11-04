package net.minevoxels.core.renderer;

import org.lwjgl.system.MemoryUtil;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import static org.lwjgl.opengl.GL46.*;

public class MeshManager {
    private int vertexSize = 8192;
    private int indicesSize = 8192;
    private FloatBuffer vertices = MemoryUtil.memAllocFloat(vertexSize);
    private IntBuffer indices = MemoryUtil.memAllocInt(indicesSize);
    private int vertexCount;
    private int VAO;
    private int VBO;
    private int EBO;

    public void addMeshData(float[] dataVertices, int[] dataIndices) {
        if(vertices.position() >= vertices.capacity() - 512) {
            int nf = vertexSize * 2;
            vertices = MemoryUtil.memRealloc(vertices, nf);
            vertexSize = nf;
        }

        vertices.put(dataVertices);

        if(indices.position() >= vertices.capacity() - 512) {
            int ni = indicesSize * 2;
            indices = MemoryUtil.memRealloc(indices, ni);
            indicesSize = ni;
        }

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

        glVertexArrayAttribBinding(VAO, 0, 0);
        glVertexArrayAttribFormat(VAO, 0, 3, GL_FLOAT, false, 0);

        glVertexArrayVertexBuffer(VAO, 0, VBO, 0, 3 * Float.BYTES);
        glVertexArrayElementBuffer(VAO, EBO);
    }

    public void flushUpdatedMesh() {
        if(vertices.position() < vertices.capacity() - 1024) {
            vertices.flip();
            glNamedBufferSubData(VBO, 0, vertices);
        } else {
            int nf = vertexSize * 2;
            vertices = MemoryUtil.memRealloc(vertices, nf);
            vertexSize = nf;

            vertices.flip();
            glNamedBufferData(VBO, vertices, GL_DYNAMIC_DRAW);
        }

        if(indices.position() < indices.capacity() - 1024) {
            indices.flip();
            glNamedBufferSubData(EBO, 0, indices);
        } else {
            int ni = indicesSize * 2;
            indices = MemoryUtil.memRealloc(indices, ni);
            indicesSize = ni;

            indices.flip();
            glNamedBufferData(EBO, indices, GL_DYNAMIC_DRAW);
        }
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
