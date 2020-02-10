package hu.idomsoft.common.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = JpegValidator.class)
public @interface Jpeg {
    int width();

    int height();

    String message() default "";

    String fieldName();

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}

