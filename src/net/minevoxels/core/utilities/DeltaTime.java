package net.minevoxels.core.utilities;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class DeltaTime {
    private double lastTime = glfwGetTime();
    private double deltaTime = 0.0;

    public void updateDelta() {
        double currentTime = glfwGetTime();
        this.deltaTime = currentTime - lastTime;
        lastTime = currentTime;
    }

    public float getDeltaTime() {
        return (float)deltaTime;
    }
}
