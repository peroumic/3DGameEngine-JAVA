package components;

import componentArchitecture.Component;
import models.TexturedModel;

public class ModelComponent implements Component {
    //textured model for entity
    private TexturedModel texturedModel;

    public TexturedModel getTexturedModel() {
        return texturedModel;
    }

    public void setTexturedModel(TexturedModel texturedModel) {
        this.texturedModel = texturedModel;
    }
}
