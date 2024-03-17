package vitalconnect.model.allergytag;

import static java.util.Objects.requireNonNull;
import static vitalconnect.commons.util.AppUtil.checkArgument;

/**
 * Represents a Tag in the clinic.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class AllergyTag {

    public static final String MESSAGE_CONSTRAINTS = "Tags names should be alphanumeric";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";

    public final String allergy;

    /**
     * Constructs a {@code Tag}.
     *
     * @param allergy A valid tag name.
     */
    public AllergyTag(String allergy) {
        requireNonNull(allergy);
        checkArgument(isValidTagName(allergy), MESSAGE_CONSTRAINTS);
        this.allergy = allergy;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AllergyTag)) {
            return false;
        }

        AllergyTag otherAllergyTag = (AllergyTag) other;
        return allergy.equals(otherAllergyTag.allergy);
    }

    @Override
    public int hashCode() {
        return allergy.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + allergy + ']';
    }

}
