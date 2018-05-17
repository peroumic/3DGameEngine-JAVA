package terrains;

import models.RawModel;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.Loader;
import terrains.terrainGeneration.ColorGenerator;
import terrains.terrainGeneration.HeightGenerator;
import textures.ModelTexture;
import toolbox.Color;
import toolbox.Maths;

public class Terrain {
    private static final float SIZE = 800;

    private float x;
    private float z;
    private RawModel model;
    private ModelTexture modelTexture;

    private final int VERTEX_COUNT = 128;

    private float[][] heights;

    /**
     * Contructor
     * @param gridX - which quarter of the cartesian coordinate system the terrain should be
     * @param gridZ - which quarter of the cartesian coordinate system the terrain should be
     * @param loader - for loading model
     * @param modelTexture - for loading model texture
     */
    public Terrain(int gridX, int gridZ, Loader loader, ModelTexture modelTexture){
        this.modelTexture = modelTexture;
        this.x = gridX * SIZE;
        this.z = gridZ * SIZE;
        this.model = generateTerrain(loader);
    }

    /**
     * Generator of a flat terrain
     * @param loader - for loading data into VAO
     * @return - returns the model of terrain
     */
    private RawModel generateTerrain(Loader loader){

        HeightGenerator generator = new HeightGenerator();

        int count = VERTEX_COUNT * VERTEX_COUNT;
        heights = new float[VERTEX_COUNT][VERTEX_COUNT];
        float[] vertices = new float[count * 3];
        float[] normals = new float[count * 3];
        float[] colors = new float[count*3];
        int[] indexes = new int[6*(VERTEX_COUNT-1)*(VERTEX_COUNT-1)];
        int vertexPointer = 0;
        for(int i=0;i<VERTEX_COUNT;i++){
            for(int j=0;j<VERTEX_COUNT;j++){
                vertices[vertexPointer*3] = (float)j/((float)VERTEX_COUNT - 1) * SIZE;
                float height = getHeight(j,i, generator);
                vertices[vertexPointer*3+1] = height;
                heights[j][i] = height;
                vertices[vertexPointer*3+2] = (float)i/((float)VERTEX_COUNT - 1) * SIZE;
                Vector3f normal = calculateNormal(j,i,generator);
                normals[vertexPointer*3] = normal.x;
                normals[vertexPointer*3+1] = normal.y;
                normals[vertexPointer*3+2] = normal.z;
                vertexPointer++;
            }
        }
        ColorGenerator colorGenerator = new ColorGenerator(0.45f);
        Color[][] generatedColors = colorGenerator.generateColors(heights, generator.getAMPLITUDE());

        int colorPointer = 0;
        for(int i=0;i<VERTEX_COUNT;i++){
            for(int j=0;j<VERTEX_COUNT;j++){
                colors[colorPointer*3] = generatedColors[j][i].getR();
                colors[colorPointer*3+1] = generatedColors[j][i].getG();
                colors[colorPointer*3+2] = generatedColors[j][i].getB();
                colorPointer++;
            }
        }


        int pointer = 0;
        for(int gz=0;gz<VERTEX_COUNT-1;gz++){
            for(int gx=0;gx<VERTEX_COUNT-1;gx++){
                int topLeft = (gz*VERTEX_COUNT)+gx;
                int topRight = topLeft + 1;
                int bottomLeft = ((gz+1)*VERTEX_COUNT)+gx;
                int bottomRight = bottomLeft + 1;
                indexes[pointer++] = topLeft;
                indexes[pointer++] = bottomLeft;
                indexes[pointer++] = topRight;
                indexes[pointer++] = topRight;
                indexes[pointer++] = bottomLeft;
                indexes[pointer++] = bottomRight;
            }
        }
        return loader.loadTerrainToVAO(vertices, indexes, colors, normals);
    }

    /**
     * Based on coordinates returns the height
     * @param worldX - x coordinate
     * @param worldZ - z coordinate
     * @return - returns height
     */
    public float getHeight(float worldX, float worldZ){
        float terrainX = worldX - this.x;
        float terrainZ = worldZ - this.z;
        float gridSquareSize = SIZE / ((float)heights.length - 1);
        int gridX = (int) Math.floor((terrainX/gridSquareSize));
        int gridZ = (int) Math.floor((terrainZ/gridSquareSize));

        if(gridX >= (heights.length -1) || gridZ >= (heights.length - 1)|| gridX < 0 || gridZ < 0){
            return 0;
        }

        float coordX = (terrainX % gridSquareSize) / gridSquareSize;
        float coordZ = (terrainZ % gridSquareSize) / gridSquareSize;

        float height;

        if (coordX <= (1-coordZ)) {
            height = Maths
                    .barryCentric(new Vector3f(0, heights[gridX][gridZ], 0), new Vector3f(1,
                            heights[gridX + 1][gridZ], 0), new Vector3f(0,
                            heights[gridX][gridZ + 1], 1), new Vector2f(coordX, coordZ));
        } else {
            height = Maths
                    .barryCentric(new Vector3f(1, heights[gridX + 1][gridZ], 0), new Vector3f(1,
                            heights[gridX + 1][gridZ + 1], 1), new Vector3f(0,
                            heights[gridX][gridZ + 1], 1), new Vector2f(coordX, coordZ));
        }

        return height;
    }

    private Vector3f calculateNormal(int x, int z, HeightGenerator generator){
        float heightL = getHeight(x-1, z , generator);
        float heightR = getHeight(x+1, z , generator);
        float heightD = getHeight(x, z-1 , generator);
        float heightU = getHeight(x, z+1 , generator);
        Vector3f normal = new Vector3f(heightL - heightR, 2f, heightD - heightU);
        normal.normalise();
        return normal;
    }

    private float getHeight(int x, int z, HeightGenerator generator){
        return generator.generateHeight(x,z);
    }

    public float getX() {
        return x;
    }

    public float getZ() {
        return z;
    }

    public RawModel getModel() {
        return model;
    }

    public int getVERTEX_COUNT() {
        return VERTEX_COUNT;
    }

    public static float getSIZE() {
        return SIZE;
    }

}
