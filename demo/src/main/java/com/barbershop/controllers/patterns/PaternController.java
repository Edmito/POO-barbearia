package com.barbershop.controllers.patterns;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import javafx.scene.control.TextFormatter.Change;

public class PaternController {
    public static String clearText(String input) {
        return input.replaceAll("\\D+", "");
    }

    @SuppressWarnings("exports")
    public static UnaryOperator<Change> createPatternFilter(String pattern) {
        Pattern digitsPattern = Pattern.compile("\\d*");
        final int maxDigits = pattern.replaceAll("[^#]*", "").length();
        return change -> {
            String text = change.getText();
            if (!digitsPattern.matcher(text).matches()) {
                return null; // prevent inputs other than digits
            }
            if (change.getControlText().equals(change.getControlNewText())) {
                return change; // allow all changes not modifying the text
            }

            String clearText = clearText(change.getControlNewText());
            String clearPrefix = clearText(change.getControlNewText().substring(0, change.getAnchor()));
            final int prefixLength = clearPrefix.length();
            if (clearText.length() > maxDigits) {
                if (prefixLength > maxDigits) {
                    return null; // cursor already positioned after the last digit placeholder
                }
                clearText = clearText.substring(0, maxDigits); // cut of excessive digits
            }
            StringBuilder resultText = new StringBuilder(pattern.length());
            int index = 0;
            int prefixIndex = 0;

            // copy parts digits before the cursor
            while (prefixIndex < prefixLength) {
                char c = pattern.charAt(index);
                if (c == '#') {
                    resultText.append(clearPrefix.charAt(prefixIndex));
                    prefixIndex++;
                } else {
                    resultText.append(c);
                }
                index++;
            }

            // deal with following non-digit placeholders
            char c;
            while (index < pattern.length() && (c = pattern.charAt(index)) != '#') {
                resultText.append(c);
                index++;
            }

            int newAnchor = resultText.length();
            String clearSuffix = clearText.substring(prefixLength);
            int suffixIndex = 0;

            // copy remaining digits
            while (index < pattern.length() && suffixIndex < clearSuffix.length()) {
                c = pattern.charAt(index);
                if (c == '#') {
                    resultText.append(clearSuffix.charAt(suffixIndex));
                    suffixIndex++;
                } else {
                    resultText.append(c);
                }
                index++;
            }

            resultText.append(pattern.substring(index));
            change.setRange(0, change.getControlText().length());
            change.setText(resultText.toString());
            change.selectRange(newAnchor, newAnchor);

            return change;
        };
    }

    // Validation methods
    public static boolean isValidName(String name) {
        // Validate that the name is not null or empty and does not contain digits, spaces, or special characters
        return name != null && !name.isEmpty() && !name.matches(".*[0-9 ].*") && !name.matches(".*[^a-zA-Z0-9 ].*");
    }

    public static boolean isValidPhoneNumber(String phone) {
        // Validate that the phone number is not null, not empty, and is a 10-digit numeric string
        return phone != null && !phone.isEmpty() && phone.matches("\\d{9}");
    }

    public static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }
}
