package vitalconnect.model.person.medicalinformation;

import static java.util.Objects.requireNonNull;
import static vitalconnect.commons.util.AppUtil.checkArgument;
public class Allergy {
    public static final String MESSAGE_CONSTRAINTS =
            "Allergy should be string containing only letter and space, and and it should not be blank";
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";
    public final String allergy;

    public Allergy(String allergy) {
        requireNonNull(allergy);
        checkArgument(isValidAllergy(allergy), MESSAGE_CONSTRAINTS);
        this.allergy = allergy;
    }

    public static boolean isValidAllergy(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return allergy;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Allergy)) {
            return false;
        }

        Allergy otherAllergy = (Allergy) other;
        return allergy.equals(otherAllergy.allergy);
    }

    @Override
    public int hashCode() {
        return allergy.hashCode();
    }
}
