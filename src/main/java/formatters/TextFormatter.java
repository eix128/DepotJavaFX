package formatters;

import com.google.inject.Inject;

import java.util.HashMap;

/**
 * Created by kadir.basol on 6.4.2016.
 */
public interface TextFormatter {
    public String process(HashMap<String,String> hashMap);
}
