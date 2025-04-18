package ra.edu.validate.regexValidator;

import java.util.regex.Pattern;

public class PhoneValidator {
    public static final String PHONE_VIETTEL_PREFIXES = "086|096|097|098|039|038|037|036|035|034|033|032";
    private static final String PHONE_VINAPHONE_PREFIXES = "091|094|088|083|084|085|081|082";
    private static final String PHONE_MOBIFONE_PREFIXES = "070|079|077|076|078|089|090|093";

    public static boolean isValidPhoneNumberVN(String value) {
        String phoneRegex = "(" + PHONE_VIETTEL_PREFIXES + "|" + PHONE_VINAPHONE_PREFIXES +  "|" + PHONE_MOBIFONE_PREFIXES + ")";
        return value != null && Pattern.matches(phoneRegex, value.trim());
    }
}
