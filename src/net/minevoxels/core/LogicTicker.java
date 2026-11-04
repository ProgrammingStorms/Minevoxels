package net.minevoxels.core;

import net.minevoxels.core.utilities.DeltaTime;

public class LogicTicker {
    private DeltaTime delta;

    public void createLogic() {
        delta = new DeltaTime();
    }

    public void tickLogic() {
        delta.updateDelta();
    }

    public void destroyLogic() {

    }
}
