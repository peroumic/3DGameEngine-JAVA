package camera;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

/**
 * This is the camera class, which allows us movement in our game world
 */
public class Camera extends Thread{

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

    /**
     * constructor for camera
     * @param target - the traget that the Camera follows
     */
    public Camera(Target target){
        this.target = target;
    }

    /**
     * update method for camera, which does the main calculations and checks for user input
     */
    public void move(){
        if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
            target.increaseRotation(-TURN_SPEED);
        } else if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
            target.increaseRotation(TURN_SPEED);
        }
        target.moveTarget();
        calculateZoom();
        calculatePitch();
        calculateCameraPosition(calculatedHorizontalDistance(), calculatedVerticalDistance());
        this.yaw = 180 - target.getRotY();
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
