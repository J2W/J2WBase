package j2w.team.modules.threadpool;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @创建人 sky
 * @创建时间 15/4/15 下午9:31
 * @类描述 不使用堆栈
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface J2WStack {

	boolean value() default true;
}
