package vitalconnect.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import vitalconnect.commons.core.GuiSettings;
import vitalconnect.model.person.Person;
import vitalconnect.model.person.contactinformation.ContactInformation;
import vitalconnect.model.person.identificationinformation.Name;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' clinic file path.
     */
    Path getClinicFilePath();

    /**
     * Sets the user prefs' clinic file path.
     */
    void setClinicFilePath(Path clinicFilePath);

    /**
     * Replaces clinic data with the data in {@code clinic}.
     */
    void setClinic(ReadOnlyClinic clinic);

    /** Returns the Clinic */
    ReadOnlyClinic getClinic();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the clinic.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the clinic.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the clinic.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the clinic.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the clinic.
     */
    void setPerson(Person target, Person editedPerson);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    boolean doesPersonExist(String name);
    void addAppointment(Appointment appointment);

    ObservableList<Appointment> getFilteredAppointmentList();
    void deleteAppointment(Appointment appointment);

    Person findPersonByName(Name name);

    void updatePersonContactInformation(Name name, ContactInformation contactInformation);
}
