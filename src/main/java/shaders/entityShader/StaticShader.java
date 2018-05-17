package shaders.entityShader;

import camera.Camera;
import org.lwjgl.util.vector.Matrix4f;
import renderEngine.Light;
import shaders.ShaderProgram;
import toolbox.Maths;

/**
 * handles the shader stuff for entities
 */
public class StaticShader extends ShaderProgram {

    private static final String VERTEX_FILE = "src/main/java/shaders/entityShader/vertexShader.txt";
    private static final String FRAGMENT_FILE = "src/main/java/shaders/entityShader/fragmentShader.txt";

    private int locationTransformationMatrix;
    private int locationProjectionMatrix;
    private int locationViewMatrix;
    private int lightDirection;
    private int lightColor;
    private int lightBias;

    public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void getAllUniformLocations() {
        locationTransformationMatrix = super.getUniformLocation("transformationMatrix");
        locationProjectionMatrix = super.getUniformLocation("projectionMatrix");
        locationViewMatrix = super.getUniformLocation("viewMatrix");
        lightDirection = super.getUniformLocation("lightDirection");
        lightColor = super.getUniformLocation("lightColor");
        lightBias = super.getUniformLocation("lightBias");
    }

    @Override
    protected void bindAttributes() {
        //we bind the position/texuteCoords from a VAO to be used by the shader
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
        super.bindAttribute(2, "normal");
    }

    /**
     * loads the light for lightning purposes
     * @param light - light to be used
     */
    public void loadLight(Light light){
        super.loadVector3f(lightDirection, light.getDirection());
        super.loadVector3f(lightColor, light.getColor());
        super.loadVector2f(lightBias, light.getLightBias());
    }

    public void loadTransformationMatrix(Matrix4f matrix){
        super.loadMatrix(locationTransformationMatrix, matrix);
    }

    public void loadProjectionMatrix(Matrix4f matrix){
        super.loadMatrix(locationProjectionMatrix, matrix);
    }

    public void loadViewMatrix(Camera camera){
        super.loadMatrix(locationViewMatrix, Maths.createViewMatrix(camera));
    }
}
