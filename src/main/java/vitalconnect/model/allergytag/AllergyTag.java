package vitalconnect.model.allergytag;

import static java.util.Objects.requireNonNull;
import static vitalconnect.commons.util.AppUtil.checkArgument;

/**
 * Represents a tag for allergies in a medical context.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class AllergyTag {

    public static final String MESSAGE_CONSTRAINTS = "Tag names should be alphanumeric";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";

    public final String allergy;

    /**
     * Constructs an {@code AllergyTag} with a specified name.
     *
     * @param allergy A valid tag name.
     */
    public AllergyTag(String allergy) {
        requireNonNull(allergy);
        checkArgument(isValidTagName(allergy), MESSAGE_CONSTRAINTS);
        this.allergy = allergy;
    }

    /**
     * Checks whether a given string is a valid tag name.
     *
     * @param test The string to be tested.
     * @return True if the string is a valid tag name, false otherwise.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Checks if this {@code AllergyTag} object is equal to another object.
     *
     * @param other The object to compare.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AllergyTag)) {
            return false;
        }

        AllergyTag otherAllergyTag = (AllergyTag) other;
        return allergy.equals(otherAllergyTag.allergy);
    }

    /**
     * Generates a hash code for the {@code AllergyTag} object.
     *
     * @return The hash code value for this object.
     */
    @Override
    public int hashCode() {
        return allergy.hashCode();
    }

    /**
     * Formats the state of the {@code AllergyTag} object as text for viewing.
     *
     * @return A string representing the object.
     */
    @Override
    public String toString() {
        return '[' + allergy + ']';
    }
}

