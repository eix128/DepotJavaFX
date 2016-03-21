package objectscope;

import com.google.inject.AbstractModule;
import utils.scopes.GuiceObjectScope;
import utils.scopes.ObjectScoped;

/**
 * Created by kadir.basol on 16.3.2016.
 */
public class TestObject extends AbstractModule {

    private GuiceObjectScope objectScope = new GuiceObjectScope();

    @Override
    protected void configure() {
        bindScope(ObjectScoped.class, objectScope);
        // your bindings
    }

    public GuiceObjectScope getObjectScope() {
        return objectScope;
    }

}