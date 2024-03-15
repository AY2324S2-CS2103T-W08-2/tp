package vitalconnect.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import vitalconnect.commons.util.ToStringBuilder;
import vitalconnect.model.person.Person;
import vitalconnect.model.person.UniquePersonList;
import vitalconnect.model.person.identificationinformation.Name;

/**
 * Wraps all data at the clinic level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class Clinic implements ReadOnlyClinic {

    private final UniquePersonList persons;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
    }

    public Clinic() {}

    /**
     * Creates an Clinic using the Persons in the {@code toBeCopied}
     */
    public Clinic(ReadOnlyClinic toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Resets the existing data of this {@code Clinic} with {@code newData}.
     */
    public void resetData(ReadOnlyClinic newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the clinic.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Returns true if a person with the same name as {@code name} exists in the clinic.
     */
    public Person findPersonByName(Name name) {
        requireNonNull(name);
        for (Person p : persons) {
            if (p.getName().fullName.equalsIgnoreCase(name.toString())) {
                return p;
            }
        }
        return null;
    }

    /**
     * Adds a person to the clinic.
     * The person must not already exist in the clinic.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the clinic.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the clinic.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code Clinic}.
     * {@code key} must exist in the clinic.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("persons", persons)
                .toString();
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Clinic)) {
            return false;
        }

        Clinic otherClinic = (Clinic) other;
        return persons.equals(otherClinic.persons);
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
