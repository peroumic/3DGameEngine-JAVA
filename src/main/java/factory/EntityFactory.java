package factory;

import componentArchitecture.EntityManager;
import components.*;
import models.RawModel;
import models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.Loader;
import renderEngine.OBJLoader;
import terrains.Terrain;
import textures.ModelTexture;

import java.util.Random;
import java.util.UUID;

/**
 * Entity factory implements the ability to spawn stuff in the world
 */
public class EntityFactory {
    private EntityManager entityManager;
    private Loader loader;
    private TexturedModel deer, bear, boar, rabbit, tree;
    private Random rd = new Random();

    /**
     * contructor
     * @param entityManager - needed to be able to work with the database
     * @param loader - lets the factory load and create textured models to be used
     */
    public EntityFactory(EntityManager entityManager, Loader loader){
        this.entityManager = entityManager;
        this.loader = loader;
        this.deer = createModel("deer", "deer-skin");
        this.bear = createModel("bear", "bear-skin");
        this.boar = createModel("boar", "boar-skin");
        this.rabbit = createModel("rabbit", "rabbit-skin");
        this.tree = createModel("tree", "tree-skin");
    }

    private TexturedModel createModel(String modelName, String skinName){
        RawModel model = OBJLoader.loadObjModel(modelName, loader);
        ModelTexture modelTexture = new ModelTexture(loader.loadTexture(skinName));
        TexturedModel texturedModel = new TexturedModel(model, modelTexture);
        return  texturedModel;
    }

    /**
     * Creates entity, that was selected (default is deer)
     * @param entity - name of the entity, that is selected
     * @param position - position where the user wants the entity to be spawned
     */
    public void createEntitySelectedEntity(String entity, Vector3f position, Terrain terrain){
        if(!(position.x <= 0 || position.x >= terrain.getSIZE() || position.z <= 0 || position.z >= terrain.getSIZE())){
            if (entity == "DEER") {
                createEntity(position, deer, 0.01f,
                        30, 0.25f, 80, 10,
                        18, 22,
                        "DEER",
                        2,10000,5,
                        "TREE", 10,
                        2f, 0.02f);
            }
            else if (entity == "BEAR") {
                createEntity(position, bear, 0.01f,
                        15, 0.125f, 80, 10,
                        20, 25,
                        "BEAR",
                        2,10000,5,
                        "BOAR", 1.5f,
                        1.5f, 0.01f);
            }
            else if (entity == "BOAR") {
                createEntity(position, boar, 0.01f,
                        22, 0.375f, 80, 10,
                        20, 25,
                        "BOAR",
                        2,10000,5,
                        "RABBIT", 0.5f,
                        1f, 0.01f);
            }
            else if (entity == "RABBIT") {
                createEntity(position, rabbit, 0.01f,
                        40, 0.5f, 40, 10,
                        10, 12,
                        "RABBIT",
                        4,7000,5,
                        "TREE", 5f,
                        0.2f, 0.002f);
            }
            else if (entity == "TREE") {
                createEntity(position, tree, 0.01f,
                        0, 0, 0, 0,
                        30, 50,
                        "TREE",
                        3,20000,30,
                        null,0,
                        2f, 0.008f);
            }
        }
    }

    private void createEntity(Vector3f position, TexturedModel model, float scale,
                              float maxForce, float maxSpeed, float wanderRingDistance, float wanderRingRadius,
                              int minLife, int maxLife,
                              String name,
                              int numberOfOffsprings, int reproductionRate, float reproductionRadius,
                              String foodName, float eatingSpeed,
                              float finalScale, float growthRate) {
        if(position != null){
            UUID entity = entityManager.createEntity();

            ModelComponent modComp = new ModelComponent();
            modComp.setTexturedModel(model);

            NameComponent nameComponent = new NameComponent();
            nameComponent.setName(name);

            entityManager.addComponent(entity, createTransformationComponent(position, scale));
            entityManager.addComponent(entity, modComp);
            entityManager.addComponent(entity, nameComponent);
            entityManager.addComponent(entity, createLifeComponent(minLife, maxLife));
            entityManager.addComponent(entity, createGrowthComponent(finalScale, growthRate));
            if(maxForce != 0 && maxSpeed != 0 && wanderRingRadius != 0 && wanderRingDistance != 0){
                entityManager.addComponent(entity, createWanderComponent(maxForce, maxSpeed, wanderRingDistance, wanderRingRadius));
            }
            if(numberOfOffsprings != 0 && reproductionRate != 0 && reproductionRadius != 0){
                entityManager.addComponent(entity, createReproductionComponent(numberOfOffsprings, reproductionRate, reproductionRadius));
            }
            if(eatingSpeed != 0 && foodName != null){
                entityManager.addComponent(entity, createEatingComponent(foodName, eatingSpeed));
            }
        }
    }

    // below are methods that create components and add them to a entity

    private TransformationComponent createTransformationComponent(Vector3f position, float scale){
        TransformationComponent transformationComponent = new TransformationComponent();
        transformationComponent.setPosition(position);
        transformationComponent.setOrientationY(0);
        transformationComponent.setScale(scale);
        return transformationComponent;
    }

    private WanderComponent createWanderComponent(float maxForce, float maxSpeed, float wanderRingDistance, float wanderRingRadius){
        WanderComponent wanderComponent = new WanderComponent();
        wanderComponent.setMaxForce(maxForce);
        wanderComponent.setMaxSpeed(maxSpeed);
        wanderComponent.setAcceleration(new Vector3f(0,0,0));
        wanderComponent.setVelocity(new Vector3f(0,0,0));
        wanderComponent.setWanderRingDistance(wanderRingDistance);
        wanderComponent.setWanderRingRadius(wanderRingRadius);
        wanderComponent.setRd(new Random());
        return wanderComponent;
    }

    private LifeComponent createLifeComponent(int minLife, int maxLife){
        LifeComponent lifeComponent = new LifeComponent();
        lifeComponent.setCurLife(rd.nextInt(maxLife - minLife) + minLife);
        return  lifeComponent;
    }

    private ReproductionComponent createReproductionComponent(int numberOfOffsprings, int reproductionRate, float reproductionRadius){
        ReproductionComponent reproductionComponent = new ReproductionComponent();
        reproductionComponent.setLastReproductionTick(System.currentTimeMillis());
        reproductionComponent.setNumberOfOffsprings(numberOfOffsprings);
        int bound = reproductionRate - (reproductionRate / 4);
        reproductionComponent.setReproductionRate(rd.nextInt(reproductionRate - bound) + bound);
        reproductionComponent.setReproductionRadius(reproductionRadius);
        return reproductionComponent;
    }

    private EatingComponent createEatingComponent(String foodName, float eatingSpeed){
        EatingComponent eatingComponent = new EatingComponent();
        eatingComponent.setWhatDoIEat(foodName);
        eatingComponent.setEatingSpeed(eatingSpeed);
        return eatingComponent;
    }

    private GrowthComponent createGrowthComponent(float finalScale, float growthRate){
        GrowthComponent growthComponent = new GrowthComponent();
        growthComponent.setGrowthRate(growthRate);
        growthComponent.setFinalScale(finalScale);
        return growthComponent;
    }

}
