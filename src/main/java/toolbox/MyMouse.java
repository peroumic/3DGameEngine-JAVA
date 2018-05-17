package toolbox;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

/**
 * Class for mouse interactions
 */
public class MyMouse {


    private static MyMouse ACTIVE_MOUSE = new MyMouse();

    private boolean leftClick = false;

    private float x = 0;
    private float y = 0;

    private MyMouse() {
        ACTIVE_MOUSE = this;
    }

    /**
     *
     * @return - returns the instance of an active mouse
     */
    public static MyMouse getActiveMouse() {
        return ACTIVE_MOUSE;
    }

    /**
     * checks for mouse actions
     */
    public void update() {
        resetFlags();
        checkForEvents();
        setPosition();
    }

    public boolean isLeftClick() {
        return leftClick;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    private void resetFlags() {
        leftClick = false;
    }

    private void checkForEvents() {
        while (Mouse.next()) {
            if (Mouse.getEventButtonState()) {
                if (Mouse.getEventButton() == 0) {
                    leftClick = true;
                }
            }
        }
    }

    private void setPosition() {
        x = (float) Mouse.getX() / (float) Display.getWidth();
        y = 1 - (float) Mouse.getY() / (float) Display.getHeight();
    }

}
