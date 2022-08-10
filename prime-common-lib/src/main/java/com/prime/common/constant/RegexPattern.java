package com.prime.common.constant;

public final class RegexPattern {

    public static final String USERNAME = "^(?=.{8,32}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$";

    public static final String PASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>])(?!.*[ぁ-んァ-ン一-龥]).{8,32}$";

    public final static String DATE_FORMAT = "^\\d{4}/(0[1-9]|1[0-2])/(0[1-9]|[12][0-9]|3[01])$";

}
