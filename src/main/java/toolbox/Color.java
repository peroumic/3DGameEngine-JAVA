package toolbox;

import org.lwjgl.util.vector.Vector3f;

/**
 * A color construct to be able to play with colors and color schemes
 */
public class Color {

    private Vector3f col = new Vector3f();
    private float a = 1;

    public Color(float r, float g, float b) {
        col.set(r, g, b);
    }

    public Color(Vector3f color) {
        col.set(color);
    }

    public Color(float r, float g, float b, boolean convert) {
        if (convert) {
            col.set(r / 255f, g / 255f, b / 255f);
        } else {
            col.set(r, g, b);
        }
    }

    public Vector3f getVector() {
        return col;
    }

    public float getR() {
        return col.x;
    }

    public float getG() {
        return col.y;
    }

    public float getB() {
        return col.z;
    }

    public String toString() {
        return ("(" + col.x + ", " + col.y + ", " + col.z + ")");
    }

    public static Color sub(Color colLeft, Color colRight, Color dest) {
        if (dest == null) {
            return new Color(Vector3f.sub(colLeft.col, colRight.col, null));
        } else {
            Vector3f.sub(colLeft.col, colRight.col, dest.col);
            return dest;
        }
    }

    public static Color interpolateColors(Color color1, Color color2, float blend, Color dest) {
        float color1Weight = 1 - blend;
        float r = (color1Weight * color1.col.x) + (blend * color2.col.x);
        float g = (color1Weight * color1.col.y) + (blend * color2.col.y);
        float b = (color1Weight * color1.col.z) + (blend * color2.col.z);
        if (dest == null) {
            return new Color(r, g, b);
        } else {
            return dest;
        }
    }

}