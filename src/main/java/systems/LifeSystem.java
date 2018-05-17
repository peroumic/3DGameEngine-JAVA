package systems;

import componentArchitecture.EntityManager;
import components.LifeComponent;

import java.util.Set;
import java.util.UUID;

/**
 * System, that simulates life and death
 */
public class LifeSystem {
    private EntityManager entityManager;
    private Set keySet;
    long lastUpdate = 0;

    /**
     * constructor
     * @param entityManager - for database use
     */
    public LifeSystem(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * update function, that finds all entities, that have lifecomponent
     * and every second changes their life
     */
    public void update() {
        if(System.currentTimeMillis() - lastUpdate >= 1000) {
            keySet = entityManager.getAllEntitiesPossessingComponent(LifeComponent.class);
            Object[] entities = keySet.toArray();
            for (Object object : entities) {
                lifeTick(object);
            }
            lastUpdate = System.currentTimeMillis();
        }
    }

    private void lifeTick(Object entity){
        int curLife = entityManager.getComponent((UUID) entity, LifeComponent.class).getCurLife() - 1;
        entityManager.getComponent((UUID) entity, LifeComponent.class).setCurLife(curLife);
        if(curLife <= 0){
            killEntity(entity);
        }
    }

    private void killEntity(Object entity){
        entityManager.killEntity((UUID) entity);
    }
}
