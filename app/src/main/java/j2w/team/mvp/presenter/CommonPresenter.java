package j2w.team.mvp.presenter;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by sky on 15/3/21.
 */
@Documented
@Target(TYPE)
@Retention(RUNTIME)
public @interface CommonPresenter {

	Class value();
}
