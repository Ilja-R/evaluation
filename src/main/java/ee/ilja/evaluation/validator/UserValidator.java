package ee.ilja.evaluation.validator;

import ee.ilja.evaluation.model.User;
import ee.ilja.evaluation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserValidator implements Validator {

    @Autowired
    private UserService userService;

    private final String EMAIL_REGEX = "^(.+)@(.+)$";

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        Pattern pattern = Pattern.compile(EMAIL_REGEX);

        String email = user.getEmail();

        Matcher matcher = pattern.matcher(email);

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "Not Empty");
        if (email.length() < 3 || email.length() > 64) {
            errors.rejectValue("email", "Size");
        }
        if (userService.getUserByEmail(user.getEmail()) != null && userService.getUserByEmail(user.getEmail()).getIsActive()) {
            errors.rejectValue("email", "Duplicate");
        }
        if(!matcher.matches()){
            errors.rejectValue("email", "NonMatch");
        }

    }
}
