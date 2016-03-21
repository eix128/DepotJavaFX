package objectscope;

import com.google.inject.Inject;
import utils.scopes.ObjectScoped;

/**
 * Created by kadir.basol on 16.3.2016.
 */
@ObjectScoped
public class RootObject {

    private A a;

    @Inject
    public RootObject(A a) {
        this.a = a;
    }

    public A getA() {
        return a;
    }
}
