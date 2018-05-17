package renderEngine;

import models.RawModel;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import shaders.guiShaders.GuiShader;
import textures.GuiTexture;
import toolbox.Maths;

import java.util.List;

/**
 * Handles rendering of GUI
 */
public class GuiRenderer {
    private final RawModel quad;
    private GuiShader guiShader;

    /**
     * Contructor
     * @param loader - loader to load data for rendering
     */
    public GuiRenderer(Loader loader){
        float[] positions = {-1, 1, -1, -1, 1, 1, 1, -1};
        this.quad = loader.loadToVAO(positions);
        guiShader = new GuiShader();
    }

    /**
     * Renders all the guis
     * @param textures - takes in textures of the guis
     */
    public void render(List<GuiTexture> textures){
        guiShader.start();
        GL30.glBindVertexArray(quad.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        for(GuiTexture texture : textures){
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTexture());
            Matrix4f matrix = Maths.createTransformationMatrix(texture.getPosition(), texture.getScale());
            guiShader.loadTransformation(matrix);
            GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
        }
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
        guiShader.stop();
    }

    /**
     * cleanups the shader
     */
    public void cleanIp(){
        guiShader.cleanUp();
    }
}
