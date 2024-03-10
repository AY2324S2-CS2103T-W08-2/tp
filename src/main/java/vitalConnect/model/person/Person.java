package vitalConnect.model.person;

import static vitalConnect.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import vitalConnect.commons.util.ToStringBuilder;
import vitalConnect.model.person.contactInformation.Address;
import vitalConnect.model.person.contactInformation.ContactInformation;
import vitalConnect.model.person.contactInformation.Email;
import vitalConnect.model.person.contactInformation.Phone;
import vitalConnect.model.person.identificationInformation.IdentificationInformation;
import vitalConnect.model.person.identificationInformation.Name;
import vitalConnect.model.tag.Tag;

/**
 * Represents a Person in the clinic.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {
    // TODO: Can be refactored on a later version

    // Information fields
    private final IdentificationInformation identificationInformation;
    private final ContactInformation contactInformation;

    // Data fields`
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.identificationInformation = new IdentificationInformation(name);
        this.contactInformation = new ContactInformation(email, phone, address);
        
        this.tags.addAll(tags);
    }

    public IdentificationInformation getIdentificationInformation() {
        return this.identificationInformation;
    }

    public ContactInformation getContactInformation() {
        return this.contactInformation;
    }

    public Name getName() {
        return this.identificationInformation.getName();
    }

    public Phone getPhone() {
        return contactInformation.getPhone();
    }

    public Email getEmail() {
        return contactInformation.getEmail();
    }

    public Address getAddress() {
        return contactInformation.getAddress();
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return getName().equals(otherPerson.getName())
                && getPhone().equals(otherPerson.getPhone())
                && getEmail().equals(otherPerson.getEmail())
                && getAddress().equals(otherPerson.getAddress())
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(getName(), getPhone(), getEmail(), getAddress(), tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", getName())
                .add("phone", getPhone())
                .add("email", getEmail())
                .add("address", getAddress())
                .add("tags", tags)
                .toString();
    }

}
