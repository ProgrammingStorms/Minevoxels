package net.minevoxels.core.utilities;

import java.nio.ByteBuffer;

public record TextureData(ByteBuffer image, int x, int y, int channels) {}
