package utils;

import com.sun.javafx.scene.traversal.Direction;
import javafx.scene.Node;
import javafx.scene.Scene;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Kadir on 4/10/2016.
 */
public class FXUtils {
    /**
     * Utility method to transfer focus from the given node into the
     * direction. Implemented to reflectively (!) invoke Scene's
     * package-private method traverse.
     *
     * @param node
     * @param dir Direction.NEXT default
     */
    public static void traverse(Node node, Direction dir) {
        Scene scene = node.getScene();
        if (scene == null) return;
        try {
            Method method = Scene.class.getDeclaredMethod("traverse",
                    Node.class, Direction.class);
            method.setAccessible(true);
            method.invoke(scene, node, dir);
        } catch (NoSuchMethodException | SecurityException
                | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
