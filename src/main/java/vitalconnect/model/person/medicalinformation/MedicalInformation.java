package vitalconnect.model.person.medicalinformation;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import vitalconnect.model.allergytag.AllergyTag;

/**
 * Represents the medical information of a person, including height, weight, and allergies.
 */
public class MedicalInformation {
    private final Height height;
    private final Weight weight;
    private final Set<AllergyTag> allergyTag;

    /**
     * Constructs a MedicalInformation object with the specified height, weight, and set of allergy tags.
     *
     * @param height      the height of the person
     * @param weight      the weight of the person
     * @param allergyTag  the set of allergy tags associated with the person
     */
    public MedicalInformation(Height height, Weight weight, Set<AllergyTag> allergyTag) {
        this.height = height;
        this.weight = weight;
        this.allergyTag = allergyTag;
    }

    /**
     * Constructs a MedicalInformation object with the specified height and weight, initializing the allergy tags to an empty set.
     *
     * @param height  the height of the person
     * @param weight  the weight of the person
     */
    public MedicalInformation(Height height, Weight weight) {
        this.height = height;
        this.weight = weight;
        this.allergyTag = new HashSet<>();
    }

    /**
     * Gets the height of the person.
     *
     * @return the height of the person
     */
    public Height getHeight() {
        return height;
    }

    /**
     * Gets the weight of the person.
     *
     * @return the weight of the person
     */
    public Weight getWeight() {
        return weight;
    }

    /**
     * Gets an unmodifiable set of allergy tags associated with the person.
     *
     * @return an unmodifiable set of allergy tags
     */
    public Set<AllergyTag> getAllergyTag() {
        return Collections.unmodifiableSet(allergyTag);
    }

    /**
     * Returns a string representation of the MedicalInformation object.
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Height: ").append(getHeight()).append(" Weight: ").append(getWeight()).append(" Allergies: ");
        getAllergyTag().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Checks if this MedicalInformation object is equal to another object.
     *
     * @param other the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof MedicalInformation)) {
            return false;
        }

        MedicalInformation otherInfo = (MedicalInformation) other;
        return height.equals(otherInfo.height) && weight.equals(otherInfo.weight)
                && allergyTag.equals(otherInfo.allergyTag);
    }

    /**
     * Generates a hash code for the MedicalInformation object.
     *
     * @return the hash code value for this object
     */
    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
}

