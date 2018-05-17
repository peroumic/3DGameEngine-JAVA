package systems;

import componentArchitecture.EntityManager;
import components.TransformationComponent;
import components.WanderComponent;
import org.lwjgl.util.vector.Vector3f;
import terrains.Terrain;
import toolbox.Maths;
import java.util.Set;
import java.util.UUID;

/**
 * My proudest achievement in movement, the single most time-sinking class I made
 * It uses the mindset of having the entities have a steering wheel before them
 * which corrects their "wanted" movement and creates more life-like behaviour
 */
public class WanderSystem {
    private EntityManager entityManager;
    private Terrain terrain;
    private Set keySet;
    private float WIDTH;

    /**
     * Contructor
     * @param entityManager - for database use
     * @param terrain - for collision detection
     */
    public WanderSystem(EntityManager entityManager, Terrain terrain){
        this.entityManager = entityManager;
        this.terrain = terrain;
        this.WIDTH = terrain.getSIZE();
    }

    /**
     * checks which entities should move and how and moves them, also does collision detection.
     */
    public void update() {
        keySet = entityManager.getAllEntitiesPossessingComponent(WanderComponent.class);
        Object[] entities = keySet.toArray();
        for(Object object : entities){
            entityManager.getComponent((UUID) object, WanderComponent.class).setAcceleration(seek(
                    entityManager.getComponent((UUID) object, TransformationComponent.class).getPosition(),
                    entityManager.getComponent((UUID) object, WanderComponent.class).getVelocity(), object));
            entityManager.getComponent((UUID) object, WanderComponent.class).setVelocity(Maths.sumTwoVectors(
                    entityManager.getComponent((UUID) object, WanderComponent.class).getAcceleration(),
                    entityManager.getComponent((UUID) object, WanderComponent.class).getVelocity()));

            if(entityManager.getComponent((UUID) object, WanderComponent.class).getVelocity().length() >
                    entityManager.getComponent((UUID) object, WanderComponent.class).getMaxSpeed()){
                entityManager.getComponent((UUID) object, WanderComponent.class).getVelocity().scale(
                        entityManager.getComponent((UUID) object, WanderComponent.class).getMaxSpeed());
            }

            entityManager.getComponent((UUID) object, TransformationComponent.class).setPosition(Maths.sumTwoVectors(
                    entityManager.getComponent((UUID) object, TransformationComponent.class).getPosition(),
                    entityManager.getComponent((UUID) object, WanderComponent.class).getVelocity()));

            if(entityManager.getComponent((UUID) object, TransformationComponent.class).getPosition().x > WIDTH){
                entityManager.getComponent((UUID) object, TransformationComponent.class).setPositionX(0);
            }
            if(entityManager.getComponent((UUID) object, TransformationComponent.class).getPosition().x < 0){
                entityManager.getComponent((UUID) object, TransformationComponent.class).setPositionX(WIDTH);
            }
            if(entityManager.getComponent((UUID) object, TransformationComponent.class).getPosition().z > WIDTH){
                entityManager.getComponent((UUID) object, TransformationComponent.class).setPositionZ(0);
            }
            if(entityManager.getComponent((UUID) object, TransformationComponent.class).getPosition().z < 0){
                entityManager.getComponent((UUID) object, TransformationComponent.class).setPositionZ(WIDTH);
            }

            entityManager.getComponent((UUID) object, TransformationComponent.class).setPositionY(terrain.getHeight(
                    entityManager.getComponent((UUID) object, TransformationComponent.class).getPosition().x,
                    entityManager.getComponent((UUID) object, TransformationComponent.class).getPosition().z));

            float angle = (float) Math.toDegrees(Vector3f.angle(
                    new Vector3f(0,0,800),
                    entityManager.getComponent((UUID) object, WanderComponent.class).getVelocity()
            ));
            if(entityManager.getComponent((UUID) object, WanderComponent.class).getVelocity().x <0){
                angle = -angle;
            }
            entityManager.getComponent((UUID) object, TransformationComponent.class).setOrientationY(angle);
        }
    }

    private Vector3f seek(Vector3f position, Vector3f velocity, Object entity){
        Vector3f target = wandering(position, velocity, entity);
        Vector3f desired = Maths.multiplyVectorByScale(Maths.normalizeVector(Maths.subtractTwoVectors(target, position)),
                entityManager.getComponent((UUID) entity, WanderComponent.class).getMaxSpeed());
        Vector3f steer = Maths.subtractTwoVectors(desired, velocity);
        if(steer.length() > entityManager.getComponent((UUID) entity, WanderComponent.class).getMaxForce()){
            steer.scale(entityManager.getComponent((UUID) entity, WanderComponent.class).getMaxForce());
        }
        return steer;
    }

    private Vector3f wandering(Vector3f position, Vector3f velocity, Object entity){
        Vector3f ringCenter = Maths.sumTwoVectors(position, Maths.multiplyVectorByScale(Maths.normalizeVector(velocity),
                entityManager.getComponent((UUID) entity, WanderComponent.class).getWanderRingDistance()));
        Vector3f ringVector = new Vector3f(entityManager.getComponent((UUID) entity, WanderComponent.class).getWanderRingRadius(), 0, 0);
        float angle = entityManager.getComponent((UUID) entity, WanderComponent.class).getRd().nextInt(360);
        Vector3f target = Maths.sumTwoVectors(ringCenter, Maths.rotateVector(ringVector, angle));
        return target;
    }
}
