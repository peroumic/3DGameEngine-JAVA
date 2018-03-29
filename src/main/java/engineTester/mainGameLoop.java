package engineTester;

import camera.Camera;
import camera.Target;
import componentArchitecture.EntityManager;
import components.ModelComponent;
import components.TransformationComponent;
import factory.EntityFactory;
import models.RawModel;
import models.TexturedModel;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.*;
import systems.RenderSystem;
import terrains.Terrain;
import textures.ModelTexture;
import toolbox.Color;

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
        RenderSystem rendSys = new RenderSystem(entityManager, renderer);

        Light light = new Light(new Vector3f(0.3f, 1000f, 0.5f), new Color(1f, 0.8f, 0.8f), new Vector2f(0.9f, 0.2f));
        Terrain terrain = new Terrain(0,0, loader, null);

        EntityFactory factory = new EntityFactory(entityManager, loader, terrain);
        factory.createAllCreatures(15, 50,63,100);

        Target target = new Target(new Vector3f(400, 0, 400),0,0,0,1, terrain);
        Camera camera = new Camera(target);

        while(!Display.isCloseRequested()){
            rendSys.update();
            camera.move();
            renderer.proccessTerrain(terrain);
            renderer.render(light, camera);
            DisplayManager.updateDisplay();
        }
        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();

    }
}
