package vitalconnect.model.person;

import static vitalconnect.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
import java.util.Set;

import vitalconnect.commons.util.ToStringBuilder;
import vitalconnect.model.person.contactinformation.Address;
import vitalconnect.model.person.contactinformation.ContactInformation;
import vitalconnect.model.person.contactinformation.Email;
import vitalconnect.model.person.contactinformation.Phone;
import vitalconnect.model.person.identificationinformation.IdentificationInformation;
import vitalconnect.model.person.identificationinformation.Name;
import vitalconnect.model.person.medicalinformation.Height;
import vitalconnect.model.person.medicalinformation.MedicalInformation;
import vitalconnect.model.person.medicalinformation.Weight;
import vitalconnect.model.allergytag.AllergyTag;

/**
 * Represents a person in the clinic.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {
    // Information fields
    private final IdentificationInformation identificationInformation;
    private final ContactInformation contactInformation;
    private final MedicalInformation medicalInformation;

    /**
     * Constructs a Person object with all details provided, including allergies.
     *
     * @param name             The name of the person.
     * @param phone            The phone number of the person.
     * @param email            The email address of the person.
     * @param address          The address of the person.
     * @param allergyTags      The set of allergy tags associated with the person.
     * @param height           The height of the person.
     * @param weight           The weight of the person.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<AllergyTag> allergyTags, Height height, Weight weight) {
        requireAllNonNull(name, phone, email, address, allergyTags, height, weight);
        this.identificationInformation = new IdentificationInformation(name);
        this.contactInformation = new ContactInformation(email, phone, address);
        this.medicalInformation = new MedicalInformation(height, weight, allergyTags);
    }

    /**
     * Constructs a Person object without allergy information.
     *
     * @param name     The name of the person.
     * @param phone    The phone number of the person.
     * @param email    The email address of the person.
     * @param address  The address of the person.
     * @param height   The height of the person.
     * @param weight   The weight of the person.
     */
    public Person(Name name, Phone phone, Email email, Address address, Height height, Weight weight) {
        requireAllNonNull(name, phone, email, address, height, weight);
        this.identificationInformation = new IdentificationInformation(name);
        this.contactInformation = new ContactInformation(email, phone, address);
        this.medicalInformation = new MedicalInformation(height, weight);
    }

    /**
     * Gets the identification information of the person.
     *
     * @return The identification information of the person.
     */
    public IdentificationInformation getIdentificationInformation() {
        return this.identificationInformation;
    }

    /**
     * Gets the contact information of the person.
     *
     * @return The contact information of the person.
     */
    public ContactInformation getContactInformation() {
        return this.contactInformation;
    }

    /**
     * Gets the medical information of the person.
     *
     * @return The medical information of the person.
     */
    public MedicalInformation getMedicalInformation() {
        return this.medicalInformation;
    }

    /**
     * Gets the name of the person.
     *
     * @return The name of the person.
     */
    public Name getName() {
        return this.identificationInformation.getName();
    }

    /**
     * Gets the phone number of the person.
     *
     * @return The phone number of the person.
     */
    public Phone getPhone() {
        return contactInformation.getPhone();
    }

    /**
     * Gets the email address of the person.
     *
     * @return The email address of the person.
     */
    public Email getEmail() {
        return contactInformation.getEmail();
    }

    /**
     * Gets the address of the person.
     *
     * @return The address of the person.
     */
    public Address getAddress() {
        return contactInformation.getAddress();
    }

    /**
     * Gets the height of the person.
     *
     * @return The height of the person.
     */
    public Height getHeight() {
        return medicalInformation.getHeight();
    }

    /**
     * Gets the weight of the person.
     *
     * @return The weight of the person.
     */
    public Weight getWeight() {
        return medicalInformation.getWeight();
    }

    /**
     * Gets the set of allergy tags associated with the person.
     *
     * @return The set of allergy tags associated with the person.
     */
    public Set<AllergyTag> getAllergyTag() {
        return medicalInformation.getAllergyTag();
    }

    /**
     * Checks if this person is the same as another person.
     *
     * @param otherPerson The other person to compare.
     * @return True if both persons have the same name, false otherwise.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }
        return otherPerson != null && otherPerson.getName().equals(getName());
    }

    /**
     * Checks if this person is equal to another object.
     *
     * @param other The object to compare.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Person)) {
            return false;
        }
        Person otherPerson = (Person) other;
        return getName().equals(otherPerson.getName()) &&
                getPhone().equals(otherPerson.getPhone()) &&
                getEmail().equals(otherPerson.getEmail()) &&
                getAddress().equals(otherPerson.getAddress()) &&
                getAllergyTag().equals(otherPerson.getAllergyTag());
    }

    /**
     * Generates a hash code for the person.
     *
     * @return The hash code value for this person.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getName(), getPhone(), getEmail(), getAddress(), getAllergyTag());
    }

    /**
     * Generates a string representation of the person.
     *
     * @return The string representation of the person.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", getName())
                .add("phone", getPhone())
                .add("email", getEmail())
                .add("address", getAddress())
                .add("allergies", getAllergyTag())
                .add("height", getHeight())
                .add("weight", getWeight())
                .toString();
    }
}
