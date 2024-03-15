package vitalconnect.model;

import static java.util.Objects.requireNonNull;
import static vitalconnect.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import vitalconnect.commons.core.GuiSettings;
import vitalconnect.commons.core.LogsCenter;
import vitalconnect.model.person.Person;
import vitalconnect.model.person.contactinformation.ContactInformation;
import vitalconnect.model.person.identificationinformation.Name;


/**
 * Represents the in-memory model of the clinic data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Clinic clinic;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private ObservableList<Appointment> appointments;


    /**
     * Initializes a ModelManager with the given clinic and userPrefs.
     */
    public ModelManager(ReadOnlyClinic clinic, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(clinic, userPrefs);

        logger.fine("Initializing with clinic: " + clinic + " and user prefs " + userPrefs);

        this.clinic = new Clinic(clinic);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.clinic.getPersonList());
        this.appointments = FXCollections.observableArrayList();
    }

    public ModelManager() {
        this(new Clinic(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getClinicFilePath() {
        return userPrefs.getClinicFilePath();
    }

    @Override
    public void setClinicFilePath(Path clinicFilePath) {
        requireNonNull(clinicFilePath);
        userPrefs.setClinicFilePath(clinicFilePath);
    }

    @Override
    public void addAppointment(Appointment appointment) {
        requireNonNull(appointment);
        appointments.add(appointment);
        FXCollections.sort(appointments, Comparator.comparing(Appointment::getDateTime));
    }

    @Override
    public ObservableList<Appointment> getFilteredAppointmentList() {
        return appointments;
    }

    @Override
    public void deleteAppointment(Appointment appointment) {
        appointments.remove(appointment);
    }

    @Override
    public void setClinic(ReadOnlyClinic clinic) {
        this.clinic.resetData(clinic);
    }

    @Override
    public ReadOnlyClinic getClinic() {
        return clinic;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return clinic.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        clinic.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        clinic.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public Person findPersonByName(Name name) {
        requireNonNull(name);
        return clinic.findPersonByName(name);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        clinic.setPerson(target, editedPerson);
    }

    @Override
    public boolean doesPersonExist(String name) {
        requireNonNull(name);
        return filteredPersons.stream()
                .anyMatch(person -> person.getName().fullName.equalsIgnoreCase(name));
    }

    /**
     * Updates the contact information of the person in the clinic.
     * @param name Name of the person to be updated
     * @param contactInformation New contact information of the person
     */
    public void updatePersonContactInformation(Name name, ContactInformation contactInformation) {
        Person person = clinic.findPersonByName(name);
        Person personToUpdate = person.copyPerson();
        personToUpdate.setContactInformation(contactInformation);
        setPerson(person, personToUpdate);
    }


    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedClinic}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return clinic.equals(otherModelManager.clinic)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons);
    }

}
