package utils.scopes;

import com.google.inject.Key;

import java.util.HashMap;
import java.util.Map;

public class ObjectScope {

    private Map<Key<?>,Object> store = new HashMap<Key<?>,Object>();

    @SuppressWarnings("unchecked")
    public <T> T get(Key<T> key) {
        return (T)store.get(key);
    }

    public <T> void set(Key<T> key, T instance) {
        store.put(key, instance);
    }

}