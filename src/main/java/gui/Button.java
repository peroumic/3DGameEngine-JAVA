package gui;

import org.lwjgl.util.vector.Vector2f;
import textures.GuiTexture;
import toolbox.MyMouse;

/**
 * Button for the only GUI in the game
 */
public class Button {

    private GuiTexture guiTexture;
    private float upScale;
    private MyMouse mouse;
    private String name;
    private boolean bigger, normal = false;
    private Vector2f normalSize, biggerSize;

    /**
     * Constructor
     * @param guiTexture - the image that is shown on the button
     * @param upScale - how much the size changes when mouse hovers over it
     * @param mouse - instance of the mouse
     * @param name - name for the button, so we know, what the button represents
     */
    public Button(GuiTexture guiTexture, float upScale, MyMouse mouse, String name) {
        this.guiTexture = guiTexture;
        this.upScale = upScale;
        this.mouse = mouse;
        this.name = name;
        this.biggerSize = new Vector2f(guiTexture.getScale().x + upScale, guiTexture.getScale().y + upScale);
        this.normalSize = new Vector2f(guiTexture.getScale().x, guiTexture.getScale().y);
    }

    /**
     * based on the two booleans sets the scale of the button
     */
    public void update(){
        if(bigger){
            guiTexture.setScale(biggerSize);
        }else if (normal){
            guiTexture.setScale(normalSize);
        }
    }

    /**
     * checks for the position of the mouse in relationship to the button
     * @return boolean, if mouse is over the button
     */
    public boolean isOnButton(){
        boolean isInX = false;
        boolean isInY = false;
        float mousePosX = (mouse.getX() * 2) - 1;
        float mousePosY = -((mouse.getY() * 2) - 1);
        if(mousePosX >= guiTexture.getPosition().x - (guiTexture.getScale().x) &&
                mousePosX <= guiTexture.getPosition().x + (guiTexture.getScale().x)){
            isInX = true;
        }
        if(mousePosY >= guiTexture.getPosition().y - (guiTexture.getScale().y) &&
                mousePosY <= guiTexture.getPosition().y + (guiTexture.getScale().y)){
            isInY = true;
        }
        if(isInX && isInY){
            bigger = true;
            normal = false;
            return true;
        }else{
            normal = true;
            bigger = false;
            return false;
        }
    }

    public String getName() {
        return name;
    }
}
