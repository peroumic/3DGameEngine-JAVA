package renderEngine;

import models.RawModel;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The main class for loading and creating VAOs (Vertex Array Objects), which are used to hold
 * data about the vertices, normals and texture coordinates
 */
public class Loader {

    private final static Logger LOGGER = Logger.getLogger(Loader.class.getName());
    private List<Integer> vaos = new ArrayList<Integer>();
    private List<Integer> vbos = new ArrayList<Integer>();
    private List<Integer> textures = new ArrayList<Integer>();

    /**
     * Creates the appropriate VAO
     * @param positions - stores 3 values(x,y,z) for each vertex in the model
     * @param indexes - stores indexes for renderer to know, which vertex is connected to which
     * @param textureCoordinates - stores texture coordinates based in UV mapping system from Blender
     * @param normals - stores normals, which are vectors perpendicular to the vertices
     * @return - returns complete information about the model
     */
    public RawModel loadToVAO(float[] positions, int[] indexes, float[] textureCoordinates, float[] normals){
        //get the VAO_ID
        int vaoID = createVAO();
        bindIndexesVBO(indexes);
        //store the positional data
        storeDataInAttributeList(0, 3, positions);
        storeDataInAttributeList(1, 2, textureCoordinates);
        storeDataInAttributeList(2, 3, normals);

        // unbind the VAO after we finish using it
        unbindVAO();
        //divided by 3, cause it is a 3D space (xyz)
        return new RawModel(vaoID, indexes.length);
    }

    public RawModel loadToVAO(float[] positions){
        int vaoID = createVAO();
        storeDataInAttributeList(0, 2, positions);
        unbindVAO();
        return new RawModel(vaoID, positions.length / 2);
    }

    public RawModel loadTerrainToVAO(float[] positions, int[] indexes, float[] colors, float[] normals){
        //get the VAO_ID
        int vaoID = createVAO();
        bindIndexesVBO(indexes);
        //store the positional data
        storeDataInAttributeList(0, 3, positions);
        storeDataInAttributeList(3, 3, colors);
        storeDataInAttributeList(2, 3, normals);

        // unbind the VAO after we finish using it
        unbindVAO();
        //divided by 3, cause it is a 3D space (xyz)
        return new RawModel(vaoID, indexes.length);
    }

    /**
     * Creates a VAO
     * @return - returns ID for the VAO
     */
    private int createVAO(){
        int vaoID = GL30.glGenVertexArrays();
        vaos.add(vaoID);
        GL30.glBindVertexArray(vaoID);
        return vaoID;
    }

    /**
     * Method for storing data in VAO as VBO(VErtex Buffer Objects)
     * @param attributeNumber - defines the slot, in which the data is stored
     * @param coordinateSize - defines the size of individual points (3 for position, 3 for normalVector, 2 for texture)
     * @param data - data that we want to store as a buffer
     */
    private void storeDataInAttributeList(int attributeNumber, int coordinateSize, float[] data){
        //create empty VBO
        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);
        //bind the buffer
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
        FloatBuffer buffer = storeDataInFloatBuffer(data);
        //define, what is the data used for and that we will never edit it again
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        //define an array of generic vertex attribute data
        GL20.glVertexAttribPointer(attributeNumber, coordinateSize, GL11.GL_FLOAT,false,0,0);
        //unbind
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,0);

    }

    /**
     * Clears the VAO ID, so it can be used for another model (clears the memory)
     */
    private void unbindVAO(){
        GL30.glBindVertexArray(0);
    }

    /**
     * Allows us to store data as a buffer
     * @param data - data we want to store
     * @return - returns a float buffer
     */
    private FloatBuffer storeDataInFloatBuffer(float[] data){
        //create a buffer to store data
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        //store data
        buffer.put(data);
        //prepare data for reading
        buffer.flip();
        return buffer;
    }

    /**
     * General cleanup of not needed stuff
     */
    public void cleanUp(){
        for(int vao:vaos){
            GL30.glDeleteVertexArrays(vao);
        }

        for(int vbo:vbos){
            GL15.glDeleteBuffers(vbo);
        }

        for(int texture:textures){
            GL11.glDeleteTextures(texture);
        }
    }

    /**
     * Binds and puts data into buffer, which is then stored in VBO
     * @param indexes
     */
    private void bindIndexesVBO(int[] indexes){
        //create empty vbo buffer
        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);
        //bind the data as indexes
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
        //put data into buffer
        IntBuffer buffer = storeDataInIntBuffer(indexes);
        //store buffer in the VBO
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
    }

    /**
     *
     * @param data - data we want to store
     * @return returns a integer buffer
     */
    private IntBuffer storeDataInIntBuffer(int[] data){
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    /**
     * Loads texture from a PNG file
     * @param fileName - name of the file (assumed position of files is this directory: src/main/java/resources/
     * @return - returns an ID for the texture
     */
    public int loadTexture(String fileName){
       Texture texture = null;
       //find the file to make a texture
        try {
            texture = TextureLoader.getTexture("PNG", new FileInputStream("src/main/java/resources/"+fileName+".png"));
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            e.printStackTrace();
        }
        int textureID = texture.getTextureID();
        textures.add(textureID);
        return textureID;
    }

}
