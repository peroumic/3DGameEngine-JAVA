package systems;

import componentArchitecture.EntityManager;
import components.NameComponent;
import components.ReproductionComponent;
import components.TransformationComponent;
import factory.EntityFactory;
import org.lwjgl.util.vector.Vector3f;
import terrains.Terrain;

import java.util.Random;
import java.util.Set;
import java.util.UUID;

/**
 * System that simulates reproduction
 */
public class ReproductionSystem{
    private EntityManager entityManager;
    private EntityFactory factory;
    private Terrain terrain;
    private Set keySet;
    long lastUpdate = 0;
    private Random rd = new Random();

    /**
     * Contructor
     * @param entityManager - for database use
     * @param factory - to be able to create new entities
     * @param terrain - to know, where on the terrain to create the entities (mainly the non-movable ones)
     */
    public ReproductionSystem(EntityManager entityManager, EntityFactory factory, Terrain terrain) {
        this.factory = factory;
        this.entityManager = entityManager;
        this.terrain = terrain;
    }

    /**
     * Checks which entities should reproduce and checks their biological timer
     */
    public void update() {
        if(System.currentTimeMillis() - lastUpdate >= 20) {
            keySet = entityManager.getAllEntitiesPossessingComponent(ReproductionComponent.class);
            Object[] entities = keySet.toArray();
            for (Object object : entities) {
                if(System.currentTimeMillis() - entityManager.getComponent((UUID) object, ReproductionComponent.class).getLastReproductionTick()
                        >= entityManager.getComponent((UUID) object, ReproductionComponent.class).getReproductionRate()){
                    reproduce(object);
                    entityManager.getComponent((UUID) object, ReproductionComponent.class).setLastReproductionTick(System.currentTimeMillis());
                }
            }
            lastUpdate = System.currentTimeMillis();
        }
    }

    private void reproduce(Object entity){
        float posX = entityManager.getComponent((UUID) entity, TransformationComponent.class).getPosition().getX();
        float posZ = entityManager.getComponent((UUID) entity, TransformationComponent.class).getPosition().getZ();
        float entityRadius = entityManager.getComponent((UUID) entity, ReproductionComponent.class).getReproductionRadius();
        String name = entityManager.getComponent((UUID) entity, NameComponent.class).getName();
        for(int i = 0; i < rd.nextInt(entityManager.getComponent((UUID) entity, ReproductionComponent.class).getNumberOfOffsprings()); i++){
            float randomXPosition = (posX - entityRadius) + rd.nextFloat() * ((posX + entityRadius) - (posX - entityRadius));
            float randomZPosition = (posZ - entityRadius) + rd.nextFloat() * ((posZ + entityRadius) - (posZ - entityRadius));
            float randomYPosition = terrain.getHeight(randomXPosition, randomZPosition);
            if(!(randomXPosition >= terrain.getSIZE() || randomXPosition <= 0 ||
                    randomZPosition >= terrain.getSIZE() || randomZPosition <= 0)){
                factory.createEntitySelectedEntity(name, new Vector3f(randomXPosition, randomYPosition, randomZPosition), terrain);
            }
        }
    }
}
