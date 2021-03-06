package models;

import textures.ModelTexture;

/**
 * combines texture and the model
 */
public class TexturedModel {

    private RawModel rawModel;
    private ModelTexture texture;

    public TexturedModel(RawModel rawModel, ModelTexture texture){
        this.rawModel = rawModel;
        this.texture = texture;
    }

    public ModelTexture getTexture() {
        return texture;
    }

    public RawModel getRawModel() {
        return rawModel;
    }
}
