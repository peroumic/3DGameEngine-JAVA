package shaders.terrainShader;

import camera.Camera;
import org.lwjgl.util.vector.Matrix4f;
import renderEngine.Light;
import shaders.ShaderProgram;
import toolbox.Maths;

/**
 * handles shaders for terains
 */
public class TerrainShader extends ShaderProgram {
    private static final String VERTEX_FILE = "src/main/java/shaders/terrainShader/terrainVertexShader.txt";
    private static final String FRAGMENT_FILE = "src/main/java/shaders/terrainShader/terrainFragmentShader.txt";

    private int locationTransformationMatrix;
    private int locationProjectionMatrix;
    private int locationViewMatrix;
    private int lightDirection;
    private int lightColor;
    private int lightBias;

    public TerrainShader() {
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
        super.bindAttribute(0, "in_position");
        super.bindAttribute(1, "in_normal");
        super.bindAttribute(3, "in_color");
    }

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
