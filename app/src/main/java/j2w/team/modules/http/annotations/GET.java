package j2w.team.modules.http.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by sky on 15/2/24.
 */
@Documented
@Target(METHOD)
@Retention(RUNTIME)
public @interface GET {
    String value();
}