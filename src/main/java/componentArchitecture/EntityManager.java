package componentArchitecture;

import java.io.*;
import java.util.*;

/**
 * This Manager is the bread and butter of this projects.
 * It distances itself from the problem with inheritance
 * and implements a database-like system. Components are
 * database tables, which hold data. Systems checks, if
 * Entities have data input for some component, and if they
 * do, they do what the system should do for that entity.
 */
public class EntityManager implements Serializable{
    boolean frozen;
    List<UUID> allEntities;
    HashMap<Class, HashMap<UUID, ? extends Component>> componentStores;

    public EntityManager(){
        frozen = false;
        allEntities = new LinkedList<UUID>();
        componentStores = new HashMap<Class, HashMap<UUID, ? extends Component>>();
    }

    public <T extends Component> T getComponent(UUID entity, Class<T> componentType){
        synchronized (componentStores)
        {
            HashMap<UUID, ? extends Component> store = componentStores
                    .get(componentType);

            if (store == null)
                throw new IllegalArgumentException(
                        "GET FAIL: there are no entities with a Component of class: "
                                + componentType);

            T result = (T) store.get(entity);

            if (result == null){
                throw new IllegalArgumentException("GET FAIL: " + entity
                        + " does not possess Component of class\n   missing: "
                        + componentType);
            }

            return result;
        }
    }

    public <T extends Component> void removeComponent(UUID entity, T component){
        synchronized (componentStores){
            HashMap<UUID, ? extends Component> store = componentStores
                    .get(component.getClass());

            if (store == null)
                throw new IllegalArgumentException(
                        "REMOVE FAIL: there are no entities with a Component of class: "
                                + component.getClass());

            T result = (T) store.remove(entity);
            if (result == null)
                throw new IllegalArgumentException("REMOVE FAIL: " + entity
                        + " does not possess Component of class\n   missing: "
                        + component.getClass());
        }
    }

    public <T extends Component> List<T> getAllComponentsOnEntity(UUID entity ){
        synchronized (componentStores){
            LinkedList<T> components = new LinkedList<T>();

            for (HashMap<UUID, ? extends Component> store : componentStores.values()){
                if (store == null)
                    continue;

                T componentFromThisEntity = (T) store.get(entity);

                if (componentFromThisEntity != null)
                    components.addLast(componentFromThisEntity);
            }

            return components;
        }
    }

    public <T extends Component> Set<UUID> getAllEntitiesPossessingComponent(Class<T> componentType){
        synchronized (componentStores){
            HashMap<UUID, ? extends Component> store = componentStores.get(componentType);

            if (store == null)
                return new HashSet<UUID>();

            return store.keySet();
        }
    }

    public <T extends Component> void addComponent(UUID entity, T component){
        if (frozen)
            return;

        synchronized (componentStores){
            HashMap<UUID, ? extends Component> store = componentStores
                    .get(component.getClass());

            if (store == null){
                store = new HashMap<UUID, T>();
                componentStores.put(component.getClass(), store);
            }

            ((HashMap<UUID, T>) store).put(entity, component);
        }
    }

    public UUID createEntity(){
        if (frozen)
            return null;

        final UUID uuid = UUID.randomUUID();
        allEntities.add(uuid);

        return uuid;
    }

    public void killEntity(UUID entity){
        if (frozen)
            return;

        synchronized (componentStores){

            for (HashMap<UUID, ? extends Component> componentStore : componentStores.values()){
                componentStore.remove(entity);
            }
            allEntities.remove(entity);
        }
    }
}