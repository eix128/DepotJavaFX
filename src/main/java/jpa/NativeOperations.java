package jpa;

import org.javatuples.Pair;

/**
 * Created by Kadir on 3/22/2016.
 */
public class NativeOperations {

    private final static NativeOperations nativeOperations = new NativeOperations();

    public static NativeOperations getInstance() {
        return nativeOperations;
    }
}
