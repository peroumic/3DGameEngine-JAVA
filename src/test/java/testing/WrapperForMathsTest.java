package testing;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.lwjgl.util.vector.Vector3f;

import static org.junit.Assert.*;

public class WrapperForMathsTest {

    private WrapperForMaths wrapperForMaths;

    @BeforeClass
    public static void classSetup() {
        System.out.println("I'm in class setup");
    }

    @AfterClass
    public static void classTeardown() {
        System.out.println("I'm in class teardown");
    }

    @Before
    public void setUp() throws Exception {
        System.out.println("I'm in setup");
        wrapperForMaths = new WrapperForMaths();
    }

    @Test
    public void clamp() {
        System.out.println("I'm testing clamp method");
        float expected = 10;
        float actual = wrapperForMaths.clamp(12, 3, 10);
        assertEquals(expected, actual, 0.01);
        expected = 3;
        actual = wrapperForMaths.clamp(0, 3, 10);
        assertEquals(expected, actual, 0.01);
    }

    @Test
    public void sumTwoVectors() {
        System.out.println("I'm testing sumTwoVectors method");
        Vector3f expected = new Vector3f(3, 6, -1);
        Vector3f actual = wrapperForMaths.sumTwoVectors(new Vector3f(6, 12, -6), new Vector3f(-3, -6, 5));
        assertEquals(expected.x, actual.x, 0.01);
        assertEquals(expected.y, actual.y, 0.01);
        assertEquals(expected.z, actual.z, 0.01);
    }

    @Test
    public void subtractTwoVectors() {
        System.out.println("I'm testing subtractTwoVectors method");
        Vector3f expected = new Vector3f(9, 18, -11);
        Vector3f actual = wrapperForMaths.subtractTwoVectors(new Vector3f(6, 12, -6), new Vector3f(-3, -6, 5));
        assertEquals(expected.x, actual.x, 0.01);
        assertEquals(expected.y, actual.y, 0.01);
        assertEquals(expected.z, actual.z, 0.01);
    }

    @Test
    public void multiplyVectorByScale() {
        System.out.println("I'm testing multiplyVectorByScale method");
        Vector3f expected = new Vector3f(24, 48, -24);
        Vector3f actual = wrapperForMaths.multiplyVectorByScale(new Vector3f(6, 12, -6), 4);
        assertEquals(expected.x, actual.x, 0.01);
        assertEquals(expected.y, actual.y, 0.01);
        assertEquals(expected.z, actual.z, 0.01);
    }

    @Test
    public void rotateVector() {
        System.out.println("I'm testing rotateVector method");
        Vector3f expected = new Vector3f(3.852237f, 12, -7.560441f);
        Vector3f actual = wrapperForMaths.rotateVector(new Vector3f(6, 12, -6), 18);
        assertEquals(expected.x, actual.x, 0.000001);
        assertEquals(expected.y, actual.y, 0.000001);
        assertEquals(expected.z, actual.z, 0.000001);
    }

    @Test
    public void normalizeVector() {
        System.out.println("I'm testing normalizeVector method");
        Vector3f expected = new Vector3f(0.408248f, 0.816497f, -0.408248f);
        Vector3f actual = wrapperForMaths.normalizeVector(new Vector3f(6, 12, -6));
        assertEquals(expected.x, actual.x, 0.000001);
        assertEquals(expected.y, actual.y, 0.000001);
        assertEquals(expected.z, actual.z, 0.000001);
    }
}