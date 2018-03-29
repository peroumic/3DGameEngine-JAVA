package systems;

import componentArchitecture.EntityManager;
import componentArchitecture.SubSystem;
import components.ModelComponent;
import renderEngine.RenderManager;

import java.util.Set;
import java.util.UUID;

public class RenderSystem implements SubSystem{
    private EntityManager entityManager;
    private Set keySet;
    private RenderManager renderManager;
    public RenderSystem(EntityManager entityManager, RenderManager renderManager){
        this.entityManager = entityManager;
        this.renderManager = renderManager;
    }

    public void update() {
        keySet = entityManager.getAllEntitiesPossessingComponent(ModelComponent.class);
        Object[] entities = keySet.toArray();
        int counter = 0;
        for(Object object : entities){
            counter++;
            renderManager.proccessEntity((UUID) object, entityManager.getComponent((UUID) object, ModelComponent.class).texturedModel);
        }
        System.out.println(counter);
    }

}
