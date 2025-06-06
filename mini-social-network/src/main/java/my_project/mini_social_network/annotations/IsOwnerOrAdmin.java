package my_project.mini_social_network.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface IsOwnerOrAdmin {
    int indexOfParameterContainsResourceId();
    Class<?> classOfRequiredMethod();
    String methodName();
}
