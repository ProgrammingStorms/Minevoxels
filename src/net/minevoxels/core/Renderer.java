package net.minevoxels.core;

import static org.lwjgl.opengl.GL11.*;

public class Renderer {
    public void createRenderer() {

    }

    public void renderWorld() {
        glClearColor(0.3f, 0.6f, 0.9f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT);
    }

    public void destroyRenderer() {

    }
}
