package vitalconnect.logic.parser;

import java.util.Map;

/**
 * Represents an option in the CLI syntax.
 */
public class Option {
    private static Map<String, Option> allowedOptionsMap = Map.of(
            "-p", new Option("-p"),
            "-e", new Option("-e"),
            "-a", new Option("-a")
    );
    private final String value;

    private Option(String value) {
        this.value = value;
    }

    /**
     * Returns the option if a given string is a valid option.
     */
    public static Option of(String value) {
        return allowedOptionsMap.get(value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Option // instanceof handles nulls
                && value.equals(((Option) other).value)); // state check
    }
}
