package net.minevoxels.core;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

public class Window {
    private long window;
    private int width = 1280;
    private int height = 760;
    private boolean isResize = true;

    public void createWindow() {
        GLFWErrorCallback.createPrint(System.err).set();

        glfwInitHint(GLFW_PLATFORM, GLFW_PLATFORM_X11);

        if(!glfwInit()) {
            throw new IllegalStateException("GLFW not initialized!!!");
        }

        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 6);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

        window = glfwCreateWindow(width, height, "Minevoxels", NULL, NULL);

        if(window == NULL) {
            throw new RuntimeException("Window equal null!!!");
        }

        glfwMakeContextCurrent(window);
        GL.createCapabilities();

        glViewport(0, 0, width, height);

        glfwSetFramebufferSizeCallback(window, (win, w, h) -> {
            this.width = w;
            this.height = h;
            this.isResize = true;
            glViewport(0, 0, w, h);
        });

        glfwSetKeyCallback(window, (win, key, scancode, action, mods) -> {
            if(key == GLFW_KEY_ESCAPE && action == GLFW_PRESS) {
                glfwSetWindowShouldClose(window, true);
            }
        });

        glfwShowWindow(window);
    }

    public void destroyWindow() {
        Callbacks.glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
        glfwTerminate();

        GLFWErrorCallback.createPrint(System.err).free();
    }

    public long getWindow() {
        return window;
    }

    public float getAspect() {
        return (float)width / (float)height;
    }

    public boolean getResize() {
        return isResize;
    }

    public boolean isWindowClose() {
        return glfwWindowShouldClose(window);
    }

    public void updateWindow() {
        glfwSwapBuffers(window);
        glfwPollEvents();
    }
}
