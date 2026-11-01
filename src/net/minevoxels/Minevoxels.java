package net.minevoxels;

import net.minevoxels.core.LogicTicker;
import net.minevoxels.core.Renderer;
import net.minevoxels.core.Window;

public class Minevoxels {
    private Window window = new Window();
    private LogicTicker ticker = new LogicTicker();
    private Renderer renderer = new Renderer();

    public void createGame() {
        window.createWindow();
        renderer.createRenderer();
        ticker.createLogic();
    }

    public void gameLoop() {
        while(!window.isWindowClose()) {
            ticker.tickLogic();
            renderer.renderWorld();
            window.updateWindow();
        }
    }

    public void destroyGame() {
        ticker.destroyLogic();
        renderer.destroyRenderer();
        window.destroyWindow();
    }
}
