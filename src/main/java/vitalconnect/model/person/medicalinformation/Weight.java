package vitalconnect.model.person.medicalinformation;

import static java.util.Objects.requireNonNull;
import static vitalconnect.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's name in the clinic.
 * Guarantees: immutable; is valid as declared in {@link #isValidWeight(String)}
 */
public class Weight {

    public static final String MESSAGE_CONSTRAINTS =
            "Weight should only contain alphanumerical measured in kg, and should be bigger than 0";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[0-9]*\\.?[0-9]+\n";

    public final String stringWeight;
    public final float floatWeight;
    /**
     * Constructs a {@code Height}.
     *
     * @param weight A valid weight.
     */
    public Weight(String weight) {
        requireNonNull(weight);
        checkArgument(isValidWeight(weight), MESSAGE_CONSTRAINTS);
        this.stringWeight = weight;
        this.floatWeight = Float.parseFloat(weight);
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidWeight(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return stringWeight;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Weight)) {
            return false;
        }

        Weight otherWeight = (Weight) other;
        return stringWeight.equals(otherWeight.stringWeight);
    }

    @Override
    public int hashCode() {
        return stringWeight.hashCode();
    }
}
