package by.vironit.training.basumatarau.messenger.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmailValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEmail {
    String message() default "invalid email";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
