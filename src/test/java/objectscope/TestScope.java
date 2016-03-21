package objectscope;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Test;
import utils.scopes.GuiceObjectScope;
import utils.scopes.ObjectScope;

/**
 * Created by kadir.basol on 16.3.2016.
 */
public class TestScope {
    @Test
    public void testObjectScope() {
        TestObject module = new TestObject();
        Injector injector = Guice.createInjector(module);
        GuiceObjectScope objectScope = module.getObjectScope();

        ObjectScope obj1 = new ObjectScope();
        ObjectScope obj2 = new ObjectScope();
        objectScope.enter(obj1);
        RootObject rootObject1 = injector.getInstance(RootObject.class);
        final A a = rootObject1.getA();
        System.out.println(a.getMath());
        objectScope.leave();

        objectScope.enter(obj2);
        RootObject rootObject2 = injector.getInstance(RootObject.class);
        final A a2 = rootObject2.getA();
        System.out.println(a2.getMath());
        objectScope.leave();

        objectScope.enter(obj1);
        RootObject rootObject3 = injector.getInstance(RootObject.class);
        final A a3 = rootObject3.getA();
        System.out.println(a3.getMath());
        objectScope.leave();
    }
}
