package by.vironit.training.basumatarau.messenger.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator
        implements ConstraintValidator<ValidEmail, String> {
    @Override
    public void initialize(ValidEmail constraintAnnotation) {
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return email != null
                && email.matches("([\\w\\d-_.]+)@([\\w\\d-_.]+)[.](\\w{1,5})");
    }
}
