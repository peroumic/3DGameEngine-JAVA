package components;

import componentArchitecture.Component;
import org.lwjgl.util.vector.Vector3f;

public class TransformationComponent implements Component {
    private Vector3f position;
    private float orientationY;
    private float scale;

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void setPositionX(float positionX){
        this.position.x = positionX;
    }

    public void setPositionY(float positionY){
        this.position.y = positionY;
    }

    public void setPositionZ(float positionZ){
        this.position.z = positionZ;
    }

    public float getOrientationY() {
        return orientationY;
    }

    public void setOrientationY(float orientationY) {
        this.orientationY = orientationY;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }
}
