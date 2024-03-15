package vitalconnect.model.person.contactinformation;


/**
 * Represents a Person's contact information
 * Guarantees: immutable
 */
public class ContactInformation {

    // Identity fields
    private Email email;
    private Phone phone;
    private Address address;

    /**
     * Constructs a {@code ContactInformation}.
     *
     * @param email A valid email.
     * @param phone A valid phone.
     * @param address A valid address.
     */
    public ContactInformation(Email email, Phone phone, Address address) {
        this.email = email;
        this.phone = phone;
        this.address = address;
        parseNullContact();
    }

    /**
     * Constructs a {@code ContactInformation}.
     */
    public ContactInformation() {
        this.email = new Email("");
        this.phone = new Phone("");
        this.address = new Address("");
    }

    /**
     * Parses the fields of the contact information and set them to empty if they are null.
     */
    private void parseNullContact() {
        if (email == null) {
            email = new Email("");
        }
        if (phone == null) {
            phone = new Phone("");
        }
        if (address == null) {
            address = new Address("");
        }

    }

    public Email getEmail() {
        return email;
    }

    public String getEmailValue() {
        if (email == null) {
            return "";
        }
        return email.value;
    }

    public Phone getPhone() {
        return phone;
    }

    public String getPhoneValue() {
        if (phone == null) {
            return "";
        }
        return phone.value;
    }

    public Address getAddress() {
        return address;
    }

    public String getAddressValue() {
        if (address == null) {
            return "";
        }
        return address.value;
    }

    public Email setEmail(Email email) {
        return this.email = email;
    }

    public Phone setPhone(Phone phone) {
        return this.phone = phone;
    }

    public Address setAddress(Address address) {
        return this.address = address;
    }

    public String toString() {
        return "Email: " + email + " Phone: " + phone + " Address: " + address;
    }
}
