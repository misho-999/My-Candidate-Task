package com.candidate.task.validation;

import com.candidate.task.constants.ValidationConstrains;
import com.candidate.task.constants.ValidationMessages;
import com.candidate.task.data.repositories.MailRepository;
import com.candidate.task.data.repositories.PeopleRepository;
import com.candidate.task.validation.annotation.Validator;

import com.candidate.task.web.models.BasePersonModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

import java.util.regex.Pattern;


@Validator
public class PersonValidator implements org.springframework.validation.Validator {

    private final PeopleRepository peopleRepository;
    private final MailRepository mailRepository;

    @Autowired
    public PersonValidator(PeopleRepository peopleRepository, MailRepository mailRepository) {
        this.peopleRepository = peopleRepository;
        this.mailRepository = mailRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return BasePersonModel.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        BasePersonModel personModel = (BasePersonModel) o;

        if (personModel.getFullName().length() > ValidationConstrains.FULL_NAME_MAX_LENGTH) {
            errors.rejectValue(
                    "fullName",
                    ValidationMessages.FULL_NAME_TOO_LONG,
                    ValidationMessages.FULL_NAME_TOO_LONG);
        }

        if (!Pattern.matches(ValidationConstrains.FULL_NAME_REGEX, personModel.getFullName())) {
            errors.rejectValue(
                    "fullName",
                    ValidationMessages.FULL_NAME_NOT_MATCH,
                    ValidationMessages.FULL_NAME_NOT_MATCH);
        }

        if (personModel.getPin().length() != ValidationConstrains.PIN_LENGTH
                && personModel.getPin().length() != ValidationConstrains.ZERO) {
            errors.rejectValue(
                    "pin",
                    ValidationMessages.PIN_LENGTH,
                    ValidationMessages.PIN_LENGTH);
        }

        if (personModel.getEmailType().length() > ValidationConstrains.EMAIL_TYPE_MAX_LENGTH
                || personModel.getEmailType().length() == 0) {
            errors.rejectValue(
                    "emailType",
                    ValidationMessages.EMAIL_TYPE_TOO_LONG,
                    ValidationMessages.EMAIL_TYPE_TOO_LONG);
        }

        if (personModel.getEmail().length() > ValidationConstrains.EMAIL_MAX_LENGTH) {
            errors.rejectValue(
                    "email",
                    ValidationMessages.EMAIL_TOO_LONG,
                    ValidationMessages.EMAIL_TOO_LONG);
        }

        if (!Pattern.matches(ValidationConstrains.EMAIL_REGEX, personModel.getEmail())
                && personModel.getEmail().length() > 0) {
            errors.rejectValue(
                    "email",
                    ValidationMessages.EMAIL_NOT_MATCH,
                    ValidationMessages.EMAIL_NOT_MATCH);
        }

        if (personModel.getAddressType().length() > ValidationConstrains.ADDRESS_TYPE_MAX_LENGTH
                || personModel.getAddressType().length() == 0) {
            errors.rejectValue(
                    "addressType",
                    ValidationMessages.ADDRESS_TYPE_TOO_LONG,
                    ValidationMessages.ADDRESS_TYPE_TOO_LONG);
        }

        if (personModel.getAddressInfo().length() > ValidationConstrains.ADDRESS_INFO_MAX_LENGTH
                && personModel.getAddressInfo().length() > 0) {
            errors.rejectValue(
                    "addressInfo",
                    ValidationMessages.ADDRESS_INFO_TOO_LONG,
                    ValidationMessages.ADDRESS_INFO_TOO_LONG);
        }
    }
}
