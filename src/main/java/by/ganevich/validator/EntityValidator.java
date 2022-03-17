package by.ganevich.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

@Component
@AllArgsConstructor
public class EntityValidator<T> {

    public boolean validateEntity (T entity) {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        Set<ConstraintViolation<T>> validate = validator.validate(entity);

        if (validate.size() > 0) {
            for (ConstraintViolation<T> violation : validate) {
                System.out.println(violation.getMessage());
            }
            validatorFactory.close();
            return false;
        } else {
            return true;
        }
    }
}
