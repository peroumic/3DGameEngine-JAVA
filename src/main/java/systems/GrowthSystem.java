package systems;

import componentArchitecture.EntityManager;
import components.GrowthComponent;
import components.TransformationComponent;

import java.util.Set;
import java.util.UUID;

public class GrowthSystem {
    private EntityManager entityManager;
    private Set keySet;
    long lastUpdate = 0;

    public GrowthSystem(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void update() {
        if(System.currentTimeMillis() - lastUpdate >= 100) {
            keySet = entityManager.getAllEntitiesPossessingComponent(GrowthComponent.class);
            Object[] entities = keySet.toArray();
            for (Object entity : entities) {
                float growtRate = entityManager.getComponent((UUID) entity, GrowthComponent.class).getGrowthRate();
                float finalScale = entityManager.getComponent((UUID) entity, GrowthComponent.class).getFinalScale();
                float currentScale = entityManager.getComponent((UUID) entity, TransformationComponent.class).getScale();
                if(currentScale >= finalScale){
                    entityManager.getComponent((UUID) entity, TransformationComponent.class).setScale(finalScale);
                }else{
                    entityManager.getComponent((UUID) entity, TransformationComponent.class).setScale(currentScale + growtRate);
                }
            }
            lastUpdate = System.currentTimeMillis();
        }
    }
}
