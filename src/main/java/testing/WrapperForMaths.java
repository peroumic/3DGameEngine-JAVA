package testing;

import org.lwjgl.util.vector.Vector3f;
import toolbox.Maths;

public class WrapperForMaths{

    public float clamp(float value, float min, float max) {
        return Maths.clamp(value, min, max);
    }

    public Vector3f sumTwoVectors(Vector3f firstVector, Vector3f secondVector) {
        return Maths.sumTwoVectors(firstVector, secondVector);
    }

    public Vector3f subtractTwoVectors(Vector3f subtracted, Vector3f subtractor) {
        return Maths.subtractTwoVectors(subtracted, subtractor);
    }

    public Vector3f multiplyVectorByScale(Vector3f vector, float scale) {
        return Maths.multiplyVectorByScale(vector, scale);
    }

    public Vector3f rotateVector(Vector3f vector, float rotation) {
        return Maths.rotateVector(vector, rotation);
    }

    public Vector3f normalizeVector(Vector3f vectorToBeNormalized) {
        return Maths.normalizeVector(vectorToBeNormalized);
    }
}
