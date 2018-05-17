package textures;

import org.lwjgl.util.vector.Vector2f;

/**
 * suplements a holder for GUI texture
 */
public class GuiTexture {

    private int texture;
    private Vector2f position;
    private Vector2f scale;

    /**
     * Contructor
     * @param texture - texture of the gui
     * @param position - position of the GUI
     * @param scale - scale of the GUI
     */
    public GuiTexture(int texture, Vector2f position, Vector2f scale) {
        this.texture = texture;
        this.position = position;
        this.scale = scale;
    }

    public int getTexture() {
        return texture;
    }

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public Vector2f getScale() {
        return scale;
    }

    public void setScale(Vector2f scale) {
        this.scale = scale;
    }
}
