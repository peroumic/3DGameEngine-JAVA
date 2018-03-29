package renderEngine;

import camera.Camera;
import componentArchitecture.EntityManager;
import models.TexturedModel;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL32;
import org.lwjgl.util.vector.Matrix4f;
import shaders.entityShader.StaticShader;
import shaders.terrainShader.TerrainShader;
import terrains.Terrain;

import java.util.*;

/**
 * REnder Manager is used for optimization, so we render in batches and not one by one
 */
public class RenderManager {

    private static final float FOV = 70;
    private static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PLANE = 1000;

    private Matrix4f projectionMatrix;
    private StaticShader shader = new StaticShader();
    private EntityRenderer entityRenderer;

    private EntityManager entityManager;

    private TerrainRenderer terrainRenderer;
    private TerrainShader terrainShader = new TerrainShader();

    List<Terrain> terrains = new ArrayList<Terrain>();
    private Map<TexturedModel, List<UUID>> entities = new HashMap<TexturedModel, List<UUID>>();

    public RenderManager(EntityManager entityManager){
        this.entityManager = entityManager;
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
        createProjectionMatrix();
        entityRenderer = new EntityRenderer(shader, projectionMatrix, entityManager);
        terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);
    }

    /**
     * Render method
     * @param sun - main light source
     * @param camera - min camera
     */
    public void render(Light sun, Camera camera){
        prepare();
        shader.start();
        shader.loadLight(sun);
        shader.loadViewMatrix(camera);
        entityRenderer.render(entities);
        shader.stop();
        terrainShader.start();
        terrainShader.loadLight(sun);
        terrainShader.loadViewMatrix(camera);
        terrainRenderer.render(terrains);
        terrainShader.stop();
        terrains.clear();
        entities.clear();
    }

    public void proccessEntity(UUID entity, TexturedModel texturedModel){
        List<UUID> batch = entities.get(texturedModel);
        if(batch!=null){
            batch.add(entity);
        }else{
            List<UUID> newBatch = new ArrayList<UUID>();
            newBatch.add(entity);
            entities.put(texturedModel, newBatch);
        }
    }


    public void proccessTerrain(Terrain terrain){
        terrains.add(terrain);
    }

    /**
     * Prepares the engine for rendering
     */
    public void prepare(){
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL32.glProvokingVertex(GL32.GL_FIRST_VERTEX_CONVENTION);
        if (Keyboard.isKeyDown(Keyboard.KEY_G)) {
            GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
        } else {
            GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
        }
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(1,1,1,0.5f);
    }

    /**
     * Method that calculates where should everything stand and be
     */
    private void createProjectionMatrix() {
        //calculation of the projection matrix
        float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = FAR_PLANE - NEAR_PLANE;

        projectionMatrix = new Matrix4f();
        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
        projectionMatrix.m33 = 0;
    }

    /**
     * General clean up
     */
    public void cleanUp(){
        shader.cleanUp();
        terrainShader.cleanUp();
    }

}
