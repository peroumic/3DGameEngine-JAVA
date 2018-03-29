package factory;

import componentArchitecture.EntityManager;
import components.ModelComponent;
import components.TransformationComponent;
import models.RawModel;
import models.TexturedModel;
import renderEngine.Loader;
import renderEngine.OBJLoader;
import terrains.Terrain;
import textures.ModelTexture;

import java.util.UUID;

public class EntityFactory {
    private EntityManager entityManager;
    private Loader loader;
    private Terrain terrain;

    public EntityFactory(EntityManager entityManager, Loader loader, Terrain terrain){
        this.entityManager = entityManager;
        this.loader = loader;
        this.terrain = terrain;
    }

    public void createAllCreatures(int numberOfBears, int numberOfDeers, int numberOfBoars, int numberOfRabbits){
        createBears(numberOfBears);
        createDeers(numberOfDeers);
        createBoars(numberOfBoars);
        createRabbits(numberOfRabbits);
    }

    private void createDeers(int numberOfDeers) {
        RawModel deer = OBJLoader.loadObjModel("deer", loader);
        ModelTexture deerTexture = new ModelTexture(loader.loadTexture("deer-skin"));
        TexturedModel texturedDeer = new TexturedModel(deer, deerTexture);
        ModelComponent modComp = new ModelComponent();
        modComp.texturedModel = texturedDeer;
        for(int i=0; i < numberOfDeers; i++){
            UUID entity = entityManager.createEntity();
            TransformationComponent transComp = new TransformationComponent();
            transComp.posX = generateRandom(0,800);
            transComp.posZ = generateRandom(0,800);
            transComp.posY = terrain.getHeight(transComp.posX, transComp.posZ);
            transComp.scale = 2f;
            entityManager.addComponent(entity, transComp);
            entityManager.addComponent(entity, modComp);
        }
    }

    private void createBears(int numberOfBears) {
        RawModel bear = OBJLoader.loadObjModel("bear", loader);
        ModelTexture bearTexture = new ModelTexture(loader.loadTexture("bear-skin"));
        TexturedModel texturedBear = new TexturedModel(bear, bearTexture);
        ModelComponent modComp = new ModelComponent();
        modComp.texturedModel = texturedBear;
        for(int i=0; i < numberOfBears; i++){
            UUID entity = entityManager.createEntity();
            TransformationComponent transComp = new TransformationComponent();
            transComp.posX = generateRandom(0,800);
            transComp.posZ = generateRandom(0,800);
            transComp.posY = terrain.getHeight(transComp.posX, transComp.posZ);
            transComp.scale = 1.8f;
            entityManager.addComponent(entity, transComp);
            entityManager.addComponent(entity, modComp);
        }
    }

    private void createBoars(int numberOfBoars) {
        RawModel boar = OBJLoader.loadObjModel("boar", loader);
        ModelTexture boarTexture = new ModelTexture(loader.loadTexture("boar-skin"));
        TexturedModel texturedBoar = new TexturedModel(boar, boarTexture);
        ModelComponent modComp = new ModelComponent();
        modComp.texturedModel = texturedBoar;
        for(int i=0; i < numberOfBoars; i++){
            UUID entity = entityManager.createEntity();
            TransformationComponent transComp = new TransformationComponent();
            transComp.posX = generateRandom(0,800);
            transComp.posZ = generateRandom(0,800);
            transComp.posY = terrain.getHeight(transComp.posX, transComp.posZ);
            transComp.scale = 1f;
            entityManager.addComponent(entity, transComp);
            entityManager.addComponent(entity, modComp);
        }
    }

    private void createRabbits(int numberOfRabbits){
        RawModel rabbit = OBJLoader.loadObjModel("rabbit", loader);
        ModelTexture rabbitTexture = new ModelTexture(loader.loadTexture("rabbit-skin"));
        TexturedModel texturedRabbit = new TexturedModel(rabbit, rabbitTexture);
        ModelComponent modComp = new ModelComponent();
        modComp.texturedModel = texturedRabbit;
        for(int i=0; i < numberOfRabbits; i++){
            UUID entity = entityManager.createEntity();
            TransformationComponent transComp = new TransformationComponent();
            transComp.posX = generateRandom(0,800);
            transComp.posZ = generateRandom(0,800);
            transComp.posY = terrain.getHeight(transComp.posX, transComp.posZ);
            transComp.scale = 0.3f;
            entityManager.addComponent(entity, transComp);
            entityManager.addComponent(entity, modComp);
        }

    }

    private int generateRandom(int min,int max) {
        return min + (int)(Math.random() * ((max - min) + 1));
    }

}
