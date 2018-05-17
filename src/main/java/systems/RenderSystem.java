package systems;

import componentArchitecture.EntityManager;
import components.ModelComponent;
import renderEngine.RenderManager;

import java.util.Set;
import java.util.UUID;

/**
 * this system feeds the entityrenderer entities, that it should render
 */
public class RenderSystem{
    private EntityManager entityManager;
    private Set keySet;
    private RenderManager renderManager;

    /**
     * constructor
     * @param entityManager - for database use
     * @param renderManager - for proccessing entities to render
     */
    public RenderSystem(EntityManager entityManager, RenderManager renderManager){
        this.entityManager = entityManager;
        this.renderManager = renderManager;
    }

    /**
     * main update method that checks, which entities to render
     * and feeds them to entityrenderer
     */
    public void update() {
        keySet = entityManager.getAllEntitiesPossessingComponent(ModelComponent.class);
        Object[] entities = keySet.toArray();
        for(Object object : entities){
            renderManager.proccessEntity((UUID) object, entityManager.getComponent((UUID) object, ModelComponent.class).getTexturedModel());
        }
    }

}
