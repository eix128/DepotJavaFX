package objectscope;

import com.google.inject.Inject;
import utils.scopes.ObjectScoped;

/**
 * Created by kadir.basol on 16.3.2016.
 */
@ObjectScoped
public class A {

    private String math;

    public A() {
        math = String.valueOf(5000.0*Math.random());
    }

    public String getMath() {
        return math;
    }
}
