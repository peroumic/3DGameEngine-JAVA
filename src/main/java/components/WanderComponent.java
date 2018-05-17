package components;

import componentArchitecture.Component;
import org.lwjgl.util.vector.Vector3f;

import java.util.Random;

public class WanderComponent implements Component{
    //speed of the entity
    private Vector3f velocity;
    //how quickly it changes direction
    private Vector3f acceleration;
    //maximal speed
    private float maxSpeed;
    //maximal force it can use to change direction
    private float maxForce;
    //distance from the steering wheel
    private float wanderRingDistance;
    //radius of the steering wheel
    private float wanderRingRadius;
    private Random rd;

    public Vector3f getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector3f velocity) {
        this.velocity = velocity;
    }

    public Vector3f getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Vector3f acceleration) {
        this.acceleration = acceleration;
    }

    public float getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public float getMaxForce() {
        return maxForce;
    }

    public void setMaxForce(float maxForce) {
        this.maxForce = maxForce;
    }

    public float getWanderRingDistance() {
        return wanderRingDistance;
    }

    public void setWanderRingDistance(float wanderRingDistance) {
        this.wanderRingDistance = wanderRingDistance;
    }

    public float getWanderRingRadius() {
        return wanderRingRadius;
    }

    public void setWanderRingRadius(float wanderRingRadius) {
        this.wanderRingRadius = wanderRingRadius;
    }

    public Random getRd() {
        return rd;
    }

    public void setRd(Random rd) {
        this.rd = rd;
    }
}
