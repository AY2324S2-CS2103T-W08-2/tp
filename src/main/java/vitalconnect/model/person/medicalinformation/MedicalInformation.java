package vitalconnect.model.person.medicalinformation;

import java.util.List;

public class MedicalInformation {
    private final Height height;
    private final Weight weight;
    private final List<Allergy> allergyList;

    public MedicalInformation(Height height, Weight weight, List<Allergy> allergyList) {
        this.height = height;
        this.weight = weight;
        this.allergyList = allergyList;
    }

    public MedicalInformation(Height height, Weight weight) {
        this.height = height;
        this.weight = weight;
        this.allergyList = List.of();
    }

    public Height getHeight() {
        return height;
    }

    public Weight getWeight() {
        return weight;
    }

    public List<Allergy> getAllergyList() {
        return allergyList;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Height: ")
                .append(getHeight())
                .append(" Weight: ")
                .append(getWeight())
                .append(" Allergies: ");
        getAllergyList().forEach(builder::append);
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
        return height.equals(otherInfo.height) && weight.equals(otherInfo.weight) && allergyList.equals(otherInfo.allergyList);
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
}
