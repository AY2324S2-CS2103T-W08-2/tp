package vitalconnect.model.person.medicalinformation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MedicalInformation {
    private final Height height;
    private final Weight weight;
    private final Set<Allergy> allergySet;

    public MedicalInformation(Height height, Weight weight, Set<Allergy> allergySet) {
        this.height = height;
        this.weight = weight;
        this.allergySet = allergySet;
    }

    public MedicalInformation(Height height, Weight weight) {
        this.height = height;
        this.weight = weight;
        this.allergySet = new HashSet<>();
    }

    public Height getHeight() {
        return height;
    }

    public Weight getWeight() {
        return weight;
    }

    public Set<Allergy> getAllergySet() {
        return allergySet;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Height: ")
                .append(getHeight())
                .append(" Weight: ")
                .append(getWeight())
                .append(" Allergies: ");
        getAllergySet().forEach(builder::append);
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
        return height.equals(otherInfo.height) && weight.equals(otherInfo.weight) && allergySet.equals(otherInfo.allergySet);
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
}
