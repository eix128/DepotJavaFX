package globals.interfaces;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Kadir on 3/26/2016.
 */
@Documented
@Retention (RUNTIME)
@Target(METHOD)
public @interface PostInit {
}
