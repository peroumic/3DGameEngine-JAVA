package renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.*;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class opens a display for us to draw on
 */
public class DisplayManager {

    private final static Logger LOGGER = Logger.getLogger(DisplayManager.class.getName());

    private static final int WIDTH = 1024;
    private static final int HEIGHT = 576;
    private static final int FPS_CAP = 120;

    private static long lastFrameTime;
    private static float delta;

    /**
     * This is the only public method that creates the window we use for rendering
     */
    public static void createDisplay(){
        //settings
        ContextAttribs attribs = new ContextAttribs(3,2).withForwardCompatible(true).withProfileCore(true);

        try {
            //basic setup for the displaymode
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.create(new PixelFormat(), attribs);
            Display.setTitle("The Game Engine v1.0");
        } catch (LWJGLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }

        //tells GL to use whole width and height to render
        GL11.glViewport(0,0,WIDTH,HEIGHT);

        lastFrameTime = getCurrentTime();
    }

    /**
     * updates the display
     */
    public static void updateDisplay(){

        Display.sync(FPS_CAP);
        Display.update();
        long currentFrameTime = getCurrentTime();
        delta = (currentFrameTime - lastFrameTime)/1000f;
        lastFrameTime = currentFrameTime;
    }

    /**
     *
     * @return returns the delta of last frame time
     */
    public static float getFrameTimeSeconds(){
        return delta;
    }

    /**
     * closes the display
     */
    public static void closeDisplay(){
        Display.destroy();
    }

    private static long getCurrentTime(){
        return (Sys.getTime()*1000)/Sys.getTimerResolution();
    }
}
