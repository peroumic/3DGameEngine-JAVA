package camera;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;

public class Target {

    private static final float SPEED = 80;

    private Vector3f position;
    private float rotY;

    /**
     * This is the constructor of Target
     */
    public Target() {
        this.position = new Vector3f(400, 50, 400);
        this.rotY = 0;
    }

    /**
     * checks for user input and moves the target
     */
    public void moveTarget(){
        float speed = 0;
        float sideSpeed = 0;
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            sideSpeed = -SPEED;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            sideSpeed = SPEED;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_W)){
            speed = SPEED;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            speed = -SPEED;
        }
        float distance = speed * DisplayManager.getFrameTimeSeconds();
        float sideDistance = sideSpeed * DisplayManager.getFrameTimeSeconds();
        float dx = (float) (distance * Math.sin(Math.toRadians(getRotY())));
        float dz = (float) (distance * Math.cos(Math.toRadians(getRotY())));
        float sideDx = (float) (sideDistance * Math.sin(Math.toRadians(getRotY() + 90)));
        float sideDz = (float) (sideDistance * Math.cos(Math.toRadians(getRotY() + 90)));
        increasePosition(dx + sideDx, 0, dz + sideDz);
    }

    /**
     * Method used to move entity around
     * @param x - increasing position on the X axis based on value given
     * @param y - increasing position on the Y axis based on value given
     * @param z - increasing position on the Z axis based on value given
     */
    public void increasePosition(float x, float y, float z){
        this.position.x += x;
        this.position.y += y;
        this.position.z += z;
    }

    /**
     * Method used to rotate target around
     * @param ry - rotate around the Y axis based on value given
     */
    public void increaseRotation(float ry){
        this.rotY += ry;
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getRotY() {
        return rotY;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }
}
