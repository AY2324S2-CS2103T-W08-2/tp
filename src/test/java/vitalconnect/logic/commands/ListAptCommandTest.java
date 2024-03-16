package vitalconnect.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import vitalconnect.commons.core.GuiSettings;
import vitalconnect.model.Appointment;
import vitalconnect.model.Model;
import vitalconnect.model.ReadOnlyClinic;
import vitalconnect.model.ReadOnlyUserPrefs;
import vitalconnect.model.person.Person;
import vitalconnect.model.person.contactinformation.ContactInformation;
import vitalconnect.model.person.identificationinformation.Name;


public class ListAptCommandTest {
    @Test
    public void execute_noAppointmentsInList_showsNoAppointmentsMessage() {
        ModelStubEmpty modelStub = new ModelStubEmpty();
        ListAptCommand listAptCommand = new ListAptCommand();

        CommandResult commandResult = listAptCommand.execute(modelStub);

        assertEquals("No appointment is in the list.", commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_appointmentsInList_showsAppointments() {
        ModelStubWithAppointments modelStub = new ModelStubWithAppointments();
        modelStub.addAppointment(new Appointment("John Doe", LocalDateTime.now()));
        modelStub.addAppointment(new Appointment("Jane Doe", LocalDateTime.now().plusDays(1)));
        ListAptCommand listAptCommand = new ListAptCommand();

        CommandResult commandResult = listAptCommand.execute(modelStub);

        // Expected result should contain the appointments added above
        String expectedMessage = ListAptCommand.MESSAGE_SUCCESS + ":\n"
                + "1. " + modelStub.appointments.get(0).toString() + "\n"
                + "2. " + modelStub.appointments.get(1).toString();
        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
    }

    /**
     * A Model stub that has no appointments.
     */
    private class ModelStubEmpty extends ModelStub {
        @Override
        public ObservableList<Appointment> getFilteredAppointmentList() {
            return FXCollections.observableArrayList();
        }

    }

    /**
     * A Model stub that contains and allows manipulation of appointments.
     */
    private class ModelStubWithAppointments extends ModelStub {
        final List<Appointment> appointments = new ArrayList<>();

        @Override
        public void addAppointment(Appointment appointment) {
            appointments.add(appointment);
        }

        @Override
        public ObservableList<Appointment> getFilteredAppointmentList() {
            return FXCollections.observableArrayList(appointments);
        }

    }

    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getClinicFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setClinicFilePath(Path clinicFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setClinic(ReadOnlyClinic newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyClinic getClinic() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean doesPersonExist(String name) {
            throw new AssertionError("This method should not be called.");
        }

        @Test
        public void addAppointment(Appointment appointment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Appointment> getFilteredAppointmentList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteAppointment(Appointment appointment) {
            throw new AssertionError("This method should not be called.");

        }

        @Override
        public Person findPersonByName(Name name) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updatePersonContactInformation(Name name, ContactInformation contactInformation) {
            throw new AssertionError("This method should not be called.");
        }

    }


}
