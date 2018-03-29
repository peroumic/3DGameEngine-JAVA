package renderEngine;

import models.RawModel;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import shaders.terrainShader.TerrainShader;
import terrains.Terrain;
import toolbox.Maths;

import java.util.List;

public class TerrainRenderer {

    private TerrainShader shader;

    public TerrainRenderer(TerrainShader shader, Matrix4f projectionMatrix){
        this.shader = shader;
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
    }

    public void render(List<Terrain> terrains){
        for(Terrain terrain:terrains){
            prepareTerrain(terrain);
            loadModelMatrix(terrain);
            GL11.glDrawElements(GL11.GL_TRIANGLES, terrain.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
            unbindData();
        }
    }

    /**
     * Prepares and binds texture for a raw model
     * @param terrain - terrain, that should be prepared
     */
    public void prepareTerrain(Terrain terrain){
        //GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
        RawModel model = terrain.getModel();
        //load up the VAO
        GL30.glBindVertexArray(model.getVaoID());
        //get the data from the VBOs
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(3);
    }

    /**
     * Cleanup after we prepared the textured model
     */
    private void unbindData(){
        //clean up
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(3);
        GL30.glBindVertexArray(0);
    }

    /**
     * Checks where and how to render the entity
     * @param terrain - entity, which we want to prepare to render
     */
    private void loadModelMatrix(Terrain terrain){
        //getting transformation matrix, so we know, where and how big to render it
        Matrix4f transformationMatrix = Maths.createTransformationMatrix(new Vector3f(terrain.getX(),0,0),
                0,0,0,1);
        shader.loadTransformationMatrix(transformationMatrix);
    }

}
