package vitalconnect.model.person.medicalinformation;

import vitalconnect.model.allergytag.AllergyTag;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class MedicalInformation {
    private final Height height;
    private final Weight weight;
    private final Set<AllergyTag> allergyTag;

    public MedicalInformation(Height height, Weight weight, Set<AllergyTag> allergyTag) {
        this.height = height;
        this.weight = weight;
        this.allergyTag = allergyTag;
    }

    public MedicalInformation(Height height, Weight weight) {
        this.height = height;
        this.weight = weight;
        this.allergyTag = new HashSet<>();
    }

    public Height getHeight() {
        return height;
    }

    public Weight getWeight() {
        return weight;
    }

    public Set<AllergyTag> getAllergyTag() {
        return Collections.unmodifiableSet(allergyTag);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Height: ")
                .append(getHeight())
                .append(" Weight: ")
                .append(getWeight())
                .append(" Allergies: ");
        getAllergyTag().forEach(builder::append);
        return builder.toString();
    }

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

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
}
