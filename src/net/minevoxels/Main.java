package net.minevoxels;

public class Main {
    public static void main(String[] args) {
        Minevoxels minevoxels = new Minevoxels();

        minevoxels.createGame();
        minevoxels.gameLoop();
        minevoxels.destroyGame();
    }
}
