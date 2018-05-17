package toolbox;

import camera.Camera;
import org.lwjgl.util.vector.*;

/**
 * Class used for custom Math functions
 */
public class Maths {

    /**
     * Barycentric interpolation
     * @param p1 - parameter 1
     * @param p2 - parameter 2
     * @param p3 - parameter 3
     * @param pos - position of verticies
     * @return - returns the interpolated value
     */
    public static float barryCentric(Vector3f p1, Vector3f p2, Vector3f p3, Vector2f pos) {
        float det = (p2.z - p3.z) * (p1.x - p3.x) + (p3.x - p2.x) * (p1.z - p3.z);
        float l1 = ((p2.z - p3.z) * (pos.x - p3.x) + (p3.x - p2.x) * (pos.y - p3.z)) / det;
        float l2 = ((p3.z - p1.z) * (pos.x - p3.x) + (p1.x - p3.x) * (pos.y - p3.z)) / det;
        float l3 = 1.0f - l1 - l2;
        return l1 * p1.y + l2 * p2.y + l3 * p3.y;
    }

    /**
     * creates the transformation matrix
     * @param translation - how and where to move everything
     * @param scale - scale of the matrix
     * @return - returns matrix with moved values
     */
    public static Matrix4f createTransformationMatrix(Vector2f translation, Vector2f scale) {
        Matrix4f matrix = new Matrix4f();
        matrix.setIdentity();
        Matrix4f.translate(translation, matrix, matrix);
        Matrix4f.scale(new Vector3f(scale.x, scale.y, 1f), matrix, matrix);
        return matrix;
    }

    /**
     * creates the transformation matrix
     * @param translation - how and where to move everything
     * @param rx - rotation in the X axis
     * @param ry - rotation in the Y axis
     * @param rz- rotation in the Z axis
     * @param scale - scale of the matrix
     * @return - returns matrix with moved values
     */
    public static Matrix4f createTransformationMatrix(Vector3f translation, float rx, float ry, float rz, float scale){
        Matrix4f matrix = new Matrix4f();
        //resets matrix to default
        matrix.setIdentity();
        matrix.translate(translation, matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(rx), new Vector3f(1,0,0), matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(ry), new Vector3f(0,1,0), matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(rz), new Vector3f(0,0,1), matrix, matrix);
        Matrix4f.scale(new Vector3f(scale, scale, scale), matrix, matrix);
        return matrix;
    }

    /**
     * create view matrix based on camera view
     * @param camera - camera, from which point we see
     * @return - returns matrix with our view
     */
    public static Matrix4f createViewMatrix(Camera camera) {
        Matrix4f viewMatrix = new Matrix4f();
        viewMatrix.setIdentity();
        Matrix4f.rotate((float) Math.toRadians(camera.getPitch()), new Vector3f(1, 0, 0), viewMatrix, viewMatrix);
        Matrix4f.rotate((float) Math.toRadians(camera.getYaw()), new Vector3f(0, 1, 0), viewMatrix, viewMatrix);
        Vector3f cameraPos = camera.getPosition();
        //move the matrix(world) the opposite way so it slides before the camera
        Vector3f negativeCameraPos = new Vector3f(-cameraPos.x, -cameraPos.y, -cameraPos.z);
        Matrix4f.translate(negativeCameraPos, viewMatrix, viewMatrix);
        return viewMatrix;
    }

    /**
     * Make it so the value doesn't exceed values
     * @param value - value to be clamped
     * @param min - minimum
     * @param max - maximum
     * @return - clamped value
     */
    public static float clamp(float value, float min, float max){
        return Math.max(Math.min(value, max), min);
    }

    /**
     * Make a sum of two vectors
     * @param firstVector - first vector
     * @param secondVector - second vector
     * @return - returns summed vector
     */
    public static Vector3f sumTwoVectors(Vector3f firstVector, Vector3f secondVector){
        Vector3f sumVector = new Vector3f();
        sumVector.x = firstVector.x + secondVector.x;
        sumVector.y = firstVector.y + secondVector.y;
        sumVector.z = firstVector.z + secondVector.z;
        return sumVector;
    }

    /**
     * Make a difference of two vectors
     * @param subtracted - first vector
     * @param subtractor - - second vector
     * @return - returns the difference
     */
    public static Vector3f subtractTwoVectors(Vector3f subtracted, Vector3f subtractor){
        Vector3f sumVector = new Vector3f();
        sumVector.x = subtracted.x - subtractor.x;
        sumVector.y = subtracted.y - subtractor.y;
        sumVector.z = subtracted.z - subtractor.z;
        return sumVector;
    }

    /**
     * Multiply vector by scale
     * @param vector - vector
     * @param scale - scale
     * @return - returns final vector
     */
    public static Vector3f multiplyVectorByScale(Vector3f vector, float scale){
        Vector3f multiplyVector = new Vector3f();
        multiplyVector.x = vector.x * scale;
        multiplyVector.y = vector.y * scale;
        multiplyVector.z = vector.z * scale;
        return multiplyVector;
    }

    /**
     * rotate the vector in space by rotation
     * @param vector - vector to be rotated
     * @param rotation - rotation
     * @return - returns rotated vector
     */
    public static Vector3f rotateVector(Vector3f vector, float rotation){
        Vector4f toTransform = new Vector4f(vector.x, vector.y, vector.z, 1);
        Matrix4f matrix = new Matrix4f();
        float rx = 0;
        float ry = rotation;
        float rz = 0;
        Matrix4f.rotate((float) Math.toRadians(rx), new Vector3f(1, 0, 0), matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(ry), new Vector3f(0, 1, 0), matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(rz), new Vector3f(0, 0, 1), matrix, matrix);
        Vector4f rotated = matrix.transform(matrix, toTransform, toTransform);
        Vector3f rotatedVector = new Vector3f(rotated.x, rotated.y, rotated.z);
        return rotatedVector;
    }

    /**
     * Normalizes the vector, if we only need it's direction
     * @param vectorToBeNormalized - vector to be normalized
     * @return - return normalized vector
     */
    public static Vector3f normalizeVector(Vector3f vectorToBeNormalized){
        Vector3f vector = new Vector3f(vectorToBeNormalized.x, vectorToBeNormalized.y, vectorToBeNormalized.z);
        float length = (float) Math.sqrt(vector.x*vector.x + vector.y*vector.y + vector.z*vector.z);
        if(length > 0){
            vector.x /= length;
            vector.y /= length;
            vector.z /= length;
        }
        return vector;
    }
}
