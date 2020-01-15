package com.candidate.task.validation.annotation;

import com.candidate.task.constants.PeopleValidateConstants;
import com.candidate.task.data.repositories.MailRepository;
import com.candidate.task.data.repositories.PeopleRepository;
import com.candidate.task.web.models.EditPersonModel;
import com.candidate.task.web.models.InsertPersonModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

import java.util.regex.Pattern;


@Validator
public class EditPersonValidator implements org.springframework.validation.Validator {

    private final PeopleRepository peopleRepository;
    private final MailRepository mailRepository;

    @Autowired
    public EditPersonValidator(PeopleRepository peopleRepository, MailRepository mailRepository) {
        this.peopleRepository = peopleRepository;
        this.mailRepository = mailRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return InsertPersonModel.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        EditPersonModel editPersonModel = (EditPersonModel) o;

        if (editPersonModel.getPin().length()!=10) {
            errors.rejectValue(
                    "pin",
                    PeopleValidateConstants.PIN_LENGTH,
                    PeopleValidateConstants.PIN_LENGTH);
        }

        if (!Pattern.matches(PeopleValidateConstants.FULL_NAME_REGEX, editPersonModel.getFullName())){
            errors.rejectValue(
                    "fullName",
                    PeopleValidateConstants.FULL_NAME_NOT_MATCH,
                    PeopleValidateConstants.FULL_NAME_NOT_MATCH);
        }

        if (!Pattern.matches(PeopleValidateConstants.EMAIL_REGEX, editPersonModel.getEmail())){
            errors.rejectValue(
                    "email",
                    PeopleValidateConstants.EMAIL_NOT_MATCH,
                    PeopleValidateConstants.EMAIL_NOT_MATCH);
        }
    }
}
