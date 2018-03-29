package camera;

import models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;
import terrains.Terrain;

public class Target {
    private TexturedModel model;
    private Vector3f position;
    private float rotX, rotY, rotZ;
    private float scale;
    private Terrain terrain;

    /**
     * This is the constructor of entities
     * @param position - 3D vector for position
     * @param rotX - rotation on the X axis
     * @param rotY - rotation on the Y axis
     * @param rotZ - rotation on the Z axis
     * @param scale - scale of the object based on the model
     */
    public Target(Vector3f position, float rotX, float rotY, float rotZ, float scale, Terrain terrain) {
        this.position = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scale = scale;
        this.terrain = terrain;
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
     * Method used to rotate entity around
     * @param rx - rotate around the X axis based on value given
     * @param ry - rotate around the Y axis based on value given
     * @param rz - rotate around the Z axis based on value given
     */
    public void increaseRotation(float rx, float ry, float rz){
        this.rotX += rx;
        this.rotY += ry;
        this.rotZ += rz;
    }

    public TexturedModel getModel() {
        return model;
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getRotX() {
        return rotX;
    }

    public float getRotY() {
        return rotY;
    }

    public float getRotZ() {
        return rotZ;
    }

    public float getScale() {
        return scale;
    }

    public void setModel(TexturedModel model) {
        this.model = model;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void setRotX(float rotX) {
        this.rotX = rotX;
    }

    public void setRotY(float rotY) {
        this.rotY = rotY;
    }

    public void setRotZ(float rotZ) {
        this.rotZ = rotZ;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }
}
