package com.candidate.task.constants;

public class PeopleValidateConstants {
    public final static String PIN_ALREADY_EXISTS = "Person with PIN %s already exists!";
    public final static String PIN_LENGTH = "PIN must be exactly 10 characters long!";
    public final static String FULL_NAME_NOT_MATCH = "Full Name must contain only letters in Latin or Cyrillic, space or hyphen";
    public final static String EMAIL_NOT_MATCH = "Email does not match the mandatory requirements";
    public final static String EMAIL_ALREADY_EXISTS = "Email %s already exists";


    public final static String FULL_NAME_REGEX = "^[a-zA-Zа-яА-Я]+(([ -][a-zA-Zа-яА-Я])?[a-zA-Zа-яА-Я]*)*";
    public final static String EMAIL_REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

}
