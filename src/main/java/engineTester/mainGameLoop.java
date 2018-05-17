package engineTester;

import camera.Camera;
import camera.Target;
import componentArchitecture.EntityManager;
import components.NameComponent;
import components.ReproductionComponent;
import factory.EntityFactory;
import gui.Button;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.*;
import systems.*;
import terrains.Terrain;
import textures.GuiTexture;
import toolbox.Color;
import toolbox.MyMouse;
import toolbox.Raycast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * This is the main game loop, where we instanciate our objects and do do main game logic and rendering
 */
public class mainGameLoop {

    public static void main(String[] args){
        DisplayManager.createDisplay();
        Loader loader = new Loader();

        EntityManager entityManager = new EntityManager();
        RenderManager renderer = new RenderManager(entityManager);

        Light light = new Light(new Vector3f(0.3f, 1000f, 0.5f), new Color(1f, 0.8f, 0.8f), new Vector2f(0.9f, 0.2f));
        Terrain terrain = new Terrain(0,0, loader, null);

        EntityFactory factory = new EntityFactory(entityManager, loader);

        WanderSystem wanderSystem = new WanderSystem(entityManager, terrain);
        ReproductionSystem reproductionSystem = new ReproductionSystem(entityManager, factory, terrain);
        RenderSystem rendSys = new RenderSystem(entityManager, renderer);
        LifeSystem lifeSystem = new LifeSystem(entityManager);
        EatingSystem eatingSystem = new EatingSystem(entityManager);
        GrowthSystem growthSystem = new GrowthSystem(entityManager);

        Camera camera = new Camera(new Target());

        Raycast raycast = new Raycast(camera, renderer.getProjectionMatrix(), terrain);
        MyMouse mouse = MyMouse.getActiveMouse();

        GuiRenderer guiRenderer = new GuiRenderer(loader);
        float ratioY = (float) 512 / Display.getHeight();
        float ratioX = (float) 512 / Display.getWidth();
        float scaleX = ratioX / 5;
        float scaleY = ratioY / 5;
        float padding = 0.01f;

        List<Button> buttonTextureList = new ArrayList<Button>();
        List<GuiTexture> guiTextures = new ArrayList<GuiTexture>();
        String[] guiNames = {"deer-gui", "bear-gui", "boar-gui", "rabbit-gui", "tree-gui"};
        String[] entityNames = {"DEER", "BEAR", "BOAR", "RABBIT", "TREE"};

        for(int i = 0; i < 5; i++){
            GuiTexture gui = new GuiTexture(loader.loadTexture(guiNames[i]), new Vector2f(1 - scaleX - padding, 1 - scaleY - padding - 2 * i * scaleY - i * padding), new Vector2f(scaleX, scaleY));
            Button button = new Button(gui, padding, mouse, entityNames[i]);
            buttonTextureList.add(button);
            guiTextures.add(gui);
        }

        long lastUpdate = 0;
        long lastPrint = 0;
        String selectedEntity = "DEER";
        String lastPosition = "";
        boolean canSpawn;

        while(!Display.isCloseRequested()){
            canSpawn = true;
            for(Button button : buttonTextureList){
                button.update();
                if(button.isOnButton()){
                    canSpawn = false;
                    lastPosition = button.getName();
                }
            }
            if(System.currentTimeMillis() - lastUpdate >= 50){
                wanderSystem.update();
                lastUpdate = System.currentTimeMillis();
            }
            if(System.currentTimeMillis() - lastPrint >= 2000){
                howManyAnimals(entityManager);
                lastPrint = System.currentTimeMillis();
            }
            eatingSystem.update();
            lifeSystem.update();
            growthSystem.update();
            reproductionSystem.update();
            rendSys.update();
            camera.move();
            raycast.update();
            mouse.update();
            if(mouse.isLeftClick()){
                if (canSpawn){
                    Vector3f position = raycast.getCurrentTerrainPoint();
                    if(position != null){
                        factory.createEntitySelectedEntity(selectedEntity, position, terrain);
                    }
                }else{
                    selectedEntity = lastPosition;
                }
            }
            renderer.proccessTerrain(terrain);
            renderer.render(light, camera);
            guiRenderer.render(guiTextures);
            DisplayManager.updateDisplay();
        }
        guiRenderer.cleanIp();
        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }

    private static void howManyAnimals(EntityManager entityManager){
        int rabbits = 0;
        int bears = 0;
        int deers = 0;
        int boars = 0;
        int trees = 0;
        Set keySet = entityManager.getAllEntitiesPossessingComponent(NameComponent.class);
        Object[] entities = keySet.toArray();
        for (Object object : entities) {
            String entity = entityManager.getComponent((UUID) object, NameComponent.class).getName();
            if (entity == "DEER") {
                deers += 1;
            }
            else if (entity == "BEAR") {
                bears += 1;
            }
            else if (entity == "BOAR") {
                boars += 1;
            }
            else if (entity == "RABBIT") {
                rabbits += 1;
            }
            else if (entity == "TREE") {
                trees += 1;
            }
        }

        System.out.println("Rabbits: " + rabbits);
        System.out.println("Trees: " + trees);
        System.out.println("Deers: " + deers);
        System.out.println("Boars: " + boars);
        System.out.println("Bears: " + bears);
    }
}
