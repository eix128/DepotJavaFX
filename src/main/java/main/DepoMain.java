package main;

import com.google.inject.Guice;
import com.google.inject.Injector;
import ru.concerteza.util.jni.CtzJniUtils;

/**
 * Created by kadir.basol on 19.2.2016.
 */
public class DepoMain {

    public static void main(String[] args) {
        final Injector injector = Guice.createInjector(new AgentFinderModule());
    }
}
