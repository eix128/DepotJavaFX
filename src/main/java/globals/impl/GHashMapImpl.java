package globals.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import globals.interfaces.GHashMap;

import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Created by Kadir on 4/15/2016.
 */
@Singleton
public class GHashMapImpl implements GHashMap {


    private HashMap<String,Object>  hashMap;


    @Inject
    private ResourceBundle resourceBundle;


    @Inject
    public void init() {
        hashMap.put("O",resourceBundle.getString("mainPanel.outputType"));
        hashMap.put("I",resourceBundle.getString("mainPanel.inputType"));
        hashMap.put("U",resourceBundle.getString("mainPanel.unknown"));
    }


    public GHashMapImpl() {
        hashMap = new HashMap<String,Object>(   );
    }


    public void put(String key,Object value) {
        hashMap.put(key, value);
    }


    @Override
    public Object get(String key) {
        return hashMap.get(key);
    }
}
