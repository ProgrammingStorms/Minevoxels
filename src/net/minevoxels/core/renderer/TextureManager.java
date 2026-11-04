package net.minevoxels.core.renderer;

import net.minevoxels.core.utilities.TextureData;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import static org.lwjgl.opengl.GL46.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class TextureManager {
    private int arrayID;
    private final String[] textureList = {
            "deep_stone", "dirt", "earth_core",
            "grass", "grass_side", "gravel",
            "sand", "stone", "wet_sand"
    };

    private TextureData loadTexture(String textureName) {
        String texPath = "/net/minevoxels/textures/" + textureName + ".png";
        TextureData data = null;

        try(InputStream stream = getClass().getResourceAsStream(texPath)) {
            if(stream != null) {
                byte[] bytes = stream.readAllBytes();
                ByteBuffer compressedBuffer = MemoryUtil.memAlloc(bytes.length);
                compressedBuffer.put(bytes);
                compressedBuffer.flip();

                try (MemoryStack stack = MemoryStack.stackPush()) {
                    IntBuffer x = stack.mallocInt(1);
                    IntBuffer y = stack.mallocInt(1);
                    IntBuffer channels = stack.mallocInt(1);

                    STBImage.stbi_set_flip_vertically_on_load(true);
                    ByteBuffer clearBuffer = STBImage.stbi_load_from_memory(compressedBuffer, x, y, channels, 4);

                    if (clearBuffer == null) {
                        throw new RuntimeException("Texture Buffer Null!");
                    }

                    data = new TextureData(clearBuffer, x.get(), y.get(), channels.get());
                } {
                    MemoryUtil.memFree(compressedBuffer);
                }
            }
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }

        return data;
    }

    public void initTextureArray() {
       arrayID = glCreateTextures(GL_TEXTURE_2D_ARRAY);

       glTextureStorage3D(arrayID, 4, GL_RGBA8, 16, 16, textureList.length);

       glTextureParameteri(arrayID, GL_TEXTURE_WRAP_S, GL_REPEAT);
       glTextureParameteri(arrayID, GL_TEXTURE_WRAP_T, GL_REPEAT);

       glTextureParameteri(arrayID, GL_TEXTURE_MIN_FILTER, GL_NEAREST_MIPMAP_NEAREST);
       glTextureParameteri(arrayID, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

       for(int i = 0; i < textureList.length; i++) {
           TextureData data = loadTexture(textureList[i]);
           glTextureSubImage3D(arrayID, 0, 0, 0, i, data.x(), data.y(), 1, GL_RGBA, GL_UNSIGNED_BYTE, data.image());
           STBImage.stbi_image_free(data.image());
       }

       glGenerateTextureMipmap(arrayID);
    }

    public void bindTextureId(int unit) {
        glBindTextureUnit(unit, arrayID);
    }

    public void unbindTextureId(int unit) {
        glBindTextureUnit(unit, 0);
    }

    public void destroyTextureId() {
        glDeleteTextures(arrayID);
    }
}
