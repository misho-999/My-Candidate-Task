package com.candidate.task.constants;

public final class ValidationConstrains {
    public static final int FULL_NAME_MAX_LENGTH = 90;
    public static final String FULL_NAME_REGEX = "^[a-zA-Zа-яА-Я]+(([ -][a-zA-Zа-яА-Я])?[a-zA-Zа-яА-Я]*)*";
    public static final int PIN_LENGTH = 10;
    public static final int ZERO = 0;

    public static final int EMAIL_TYPE_MAX_LENGTH = 5;
    public static final int EMAIL_MAX_LENGTH = 40;
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9_!#$%&'*+\\/=?`{|}~^.-]{3,}@[a-zA-Z0-9]{2,}\\.[a-zA-Z]{2,}";

    public static final int ADDRESS_TYPE_MAX_LENGTH = 5;
    public static final int ADDRESS_INFO_MAX_LENGTH = 300;

    public static final int SEED_MAX_COUNT = 7;


}
