package net.minevoxels.core.renderer;

import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import static org.lwjgl.opengl.GL46.*;

public class MeshManager {
    private int VAO;
    private int VBO;
    private int EBO;
    private int vertexSize = 8192;
    private int indicesSize = 8192;
    private FloatBuffer vertices = MemoryUtil.memAllocFloat(vertexSize);
    private IntBuffer indices = MemoryUtil.memAllocInt(indicesSize);
    private int vertexCount;

    public void addVertices(float... verts) {
        if(vertices.position() >= vertices.capacity() - 512) {
            int nf = vertexSize * 2;
            vertices = MemoryUtil.memRealloc(vertices, nf);
            vertexSize = nf;
        }

        vertices.put(verts);
    }

    public void addIndices(int... inds) {
        if(indices.position() >= vertices.capacity() - 512) {
            int ni = indicesSize * 2;
            indices = MemoryUtil.memRealloc(indices, ni);
            indicesSize = ni;
        }

        indices.put(inds);

        vertexCount += inds.length;
    }

    public void createBuffers() {
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

    public void clearBuffers() {
        vertices.clear();
        indices.clear();
        vertexCount = 0;
    }

    public void updateBuffers() {
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

    public void destroyBuffers() {
        MemoryUtil.memFree(vertices);
        MemoryUtil.memFree(indices);
        glDeleteBuffers(EBO);
        glDeleteBuffers(VBO);
        glDeleteVertexArrays(VAO);
    }

    public void bindVAO() {
        glBindVertexArray(VAO);
    }

    public void unbindVAO() {
        glBindVertexArray(0);
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public int getVAO() {
        return VAO;
    }
}
