package systems;

import componentArchitecture.EntityManager;
import components.EatingComponent;
import components.LifeComponent;
import components.NameComponent;
import components.TransformationComponent;
import org.lwjgl.util.vector.Vector3f;

import java.util.Set;
import java.util.UUID;

public class EatingSystem {
    private EntityManager entityManager;
    private Set keySet;
    private float radius = 10;
    long lastUpdate = 0;

    public EatingSystem(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void update() {
        if(System.currentTimeMillis() - lastUpdate >= 1000) {
            keySet = entityManager.getAllEntitiesPossessingComponent(EatingComponent.class);
            Object[] entities = keySet.toArray();
            Set foodKeySet = entityManager.getAllEntitiesPossessingComponent(TransformationComponent.class);
            Object[] foods = foodKeySet.toArray();
            for (Object entity : entities) {
                String nutrition = entityManager.getComponent((UUID) entity, EatingComponent.class).getWhatDoIEat();
                float eatingSpeed = entityManager.getComponent((UUID) entity, EatingComponent.class).getEatingSpeed();
                Vector3f entityposition = entityManager.getComponent((UUID) entity, TransformationComponent.class).getPosition();
                for(Object food : foods){
                    String name = entityManager.getComponent((UUID) food, NameComponent.class).getName();
                    Vector3f foodPosition = entityManager.getComponent((UUID) food, TransformationComponent.class).getPosition();
                    if(nutrition == name){
                        if(isInCircle(entityposition, foodPosition)){
                            entityManager.getComponent((UUID) food, LifeComponent.class).adjustCurLife(-eatingSpeed);
                        }
                    }
                }
            }
            lastUpdate = System.currentTimeMillis();
        }
    }

    private boolean isInCircle(Vector3f center, Vector3f position){
        float dx = position.x - center.x;
        float dz = position.z - center.z;
        return dx * dx + dz * dz <= radius * radius;
    }
}
