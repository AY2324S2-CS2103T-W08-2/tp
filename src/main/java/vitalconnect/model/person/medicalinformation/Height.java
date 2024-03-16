package vitalconnect.model.person.medicalinformation;

import static java.util.Objects.requireNonNull;
import static vitalconnect.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's name in the clinic.
 * Guarantees: immutable; is valid as declared in {@link #isValidHeight(String)}
 */
public class Height {

    public static final String MESSAGE_CONSTRAINTS =
            "Height should only contain alphanumerical measured in cm, and should be bigger than 0";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[0-9]*\\.?[0-9]+\n";

    public final String stringHeight;
    public final float floatHeight;
    /**
     * Constructs a {@code Height}.
     *
     * @param height A valid height.
     */
    public Height(String height) {
        requireNonNull(height);
        checkArgument(isValidHeight(height), MESSAGE_CONSTRAINTS);
        this.stringHeight = height;
        this.floatHeight = Float.parseFloat(height);
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidHeight(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return stringHeight;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Height)) {
            return false;
        }

        Height otherHeight = (Height) other;
        return stringHeight.equals(otherHeight.stringHeight);
    }

    @Override
    public int hashCode() {
        return stringHeight.hashCode();
    }
}
