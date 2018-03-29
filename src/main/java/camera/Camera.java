package camera;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;

/**
 * This is the camera class, which allows us movement in our game world
 */
public class Camera {

    private static final float SPEED = 40;
    private static final float TURN_SPEED = 0.5f;
    private static final float MAX_ZOOM = 400;
    private static final float MIN_ZOOM = 15;
    private static final float MAX_PITCH = 90;
    private static final float MIN_PITCH = 10;

    private float distanceFromPlayer = 50;

    private Vector3f position = new Vector3f(0,10,0);
    //how high the camera is
    private float pitch = 30;
    //how right/left the camera is
    private float yaw;


    private Target target;

    public Camera(Target target){
        this.target = target;
    }

    public void move(){
        if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
            target.increaseRotation(0,-TURN_SPEED, 0);
        } else if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
            target.increaseRotation(0,TURN_SPEED, 0);
        }
        moveTarget();
        calculateZoom();
        calculatePitch();
        calculateCameraPosition(calculatedHorizontalDistance(), calculatedVerticalDistance());
        this.yaw = 180 - target.getRotY();
    }

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
        float dx = (float) (distance * Math.sin(Math.toRadians(target.getRotY())));
        float dz = (float) (distance * Math.cos(Math.toRadians(target.getRotY())));
        float sideDx = (float) (sideDistance * Math.sin(Math.toRadians(target.getRotY() + 90)));
        float sideDz = (float) (sideDistance * Math.cos(Math.toRadians(target.getRotY() + 90)));
        target.increasePosition(dx + sideDx, 0, dz + sideDz);
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    private void calculateCameraPosition(float horizontalDistance, float verticalDistance){
        float theta = target.getRotY();
        float offsetX = (float)(horizontalDistance * Math.sin(Math.toRadians(theta)));
        float offsetZ = (float)(horizontalDistance * Math.cos(Math.toRadians(theta)));
        position.x = target.getPosition().x - offsetX;
        position.z = target.getPosition().z - offsetZ;
        position.y = target.getPosition().y + verticalDistance;
    }

    private float calculatedHorizontalDistance(){
        return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
    }

    private float calculatedVerticalDistance(){
        return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
    }

    private void calculateZoom(){
        float zoomLevel = Mouse.getDWheel()*0.05f;
        distanceFromPlayer -= zoomLevel;
        if(distanceFromPlayer >= MAX_ZOOM) {
            distanceFromPlayer = MAX_ZOOM;
        }else if(distanceFromPlayer <= MIN_ZOOM){
            distanceFromPlayer = MIN_ZOOM;
        }
    }

    private void calculatePitch(){
        if(Mouse.isButtonDown(1)){
            float pitchChange = Mouse.getDY() * 0.1f;
            pitch -= pitchChange;
        }
        if(pitch >= MAX_PITCH) {
            pitch = MAX_PITCH;
        }else if(pitch <= MIN_PITCH){
            pitch = MIN_PITCH;
        }
    }
}
