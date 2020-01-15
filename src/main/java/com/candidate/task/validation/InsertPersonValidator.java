package com.candidate.task.validation;

import com.candidate.task.constants.PeopleValidateConstants;
import com.candidate.task.data.models.Person;
import com.candidate.task.data.repositories.MailRepository;
import com.candidate.task.data.repositories.PeopleRepository;
import com.candidate.task.validation.annotation.Validator;

import com.candidate.task.web.models.InsertPersonModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;


@Validator
public class InsertPersonValidator implements org.springframework.validation.Validator {

    private final PeopleRepository peopleRepository;
    private final MailRepository mailRepository;

    @Autowired
    public InsertPersonValidator(PeopleRepository peopleRepository, MailRepository mailRepository) {
        this.peopleRepository = peopleRepository;
        this.mailRepository = mailRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return InsertPersonModel.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        InsertPersonModel insertPersonModel = (InsertPersonModel) o;

        if (this.peopleRepository.findByPin(insertPersonModel.getPin()).isPresent()) {
            errors.rejectValue("pin",
                    String.format(PeopleValidateConstants.PIN_ALREADY_EXISTS, insertPersonModel.getPin()),
                    String.format(PeopleValidateConstants.PIN_ALREADY_EXISTS, insertPersonModel.getPin()));
        }

        if (insertPersonModel.getPin().length()!=10) {
            errors.rejectValue(
                    "pin",
                    PeopleValidateConstants.PIN_LENGTH,
                    PeopleValidateConstants.PIN_LENGTH);
        }

        if (this.mailRepository.findByEmail(insertPersonModel.getEmail()).isPresent()) {
            errors.rejectValue("email",
                    String.format(PeopleValidateConstants.EMAIL_ALREADY_EXISTS, insertPersonModel.getEmail()),
                    String.format(PeopleValidateConstants.EMAIL_ALREADY_EXISTS, insertPersonModel.getEmail()));
        }

        if (!Pattern.matches(PeopleValidateConstants.FULL_NAME_REGEX, insertPersonModel.getFullName())){
            errors.rejectValue(
                    "fullName",
                    PeopleValidateConstants.FULL_NAME_NOT_MATCH,
                    PeopleValidateConstants.FULL_NAME_NOT_MATCH);
        }

        if (!Pattern.matches(PeopleValidateConstants.EMAIL_REGEX, insertPersonModel.getEmail())){
            errors.rejectValue(
                    "email",
                    PeopleValidateConstants.EMAIL_NOT_MATCH,
                    PeopleValidateConstants.EMAIL_NOT_MATCH);
        }
    }
}
