package vitalconnect.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static vitalconnect.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import vitalconnect.commons.core.GuiSettings;
import vitalconnect.logic.commands.exceptions.CommandException;
import vitalconnect.model.Appointment;
import vitalconnect.model.Model;
import vitalconnect.model.ReadOnlyClinic;
import vitalconnect.model.ReadOnlyUserPrefs;
import vitalconnect.model.person.Person;
import vitalconnect.model.person.contactinformation.ContactInformation;
import vitalconnect.model.person.identificationinformation.Name;

public class DeleteAptCommandTest {

    @Test
    public void execute_validIndexAppointmentDeletedSuccessfully() throws Exception {
        ModelStubWithAppointments modelStub = new ModelStubWithAppointments();
        LocalDateTime dateTime = LocalDateTime.now();
        Appointment appointment = new Appointment("John Doe", dateTime);
        modelStub.addAppointment(appointment);

        int appointmentIndex = 1; // Assuming this is the index of the appointment to be deleted
        DeleteAptCommand deleteAptCommand = new DeleteAptCommand(appointmentIndex, "John Doe");

        CommandResult commandResult = deleteAptCommand.execute(modelStub);

        assertEquals("Deleted the appointment successfully:\nName: John Doe\nTime: "
                + dateTime.format(DateTimeFormatter.ofPattern("d MMM uuuu HH:mm")), commandResult.getFeedbackToUser());
        assertFalse(modelStub.appointments.contains(appointment)); // Verify the appointment was removed
    }

    @Test
    public void execute_emptyList_throwsCommandException() {
        ModelStubWithAppointments modelStub = new ModelStubWithAppointments();
        LocalDateTime dateTime = LocalDateTime.now();
        Appointment existingAppointment = new Appointment("John Doe", dateTime);
        modelStub.addAppointment(existingAppointment);

        int invalidIndex = 999;
        DeleteAptCommand deleteAptCommand = new DeleteAptCommand(invalidIndex, "John Doe");

        assertThrows(CommandException.class, "OOPS! The deletion of the appointment failed "
                + "as the index of appointment is out of range.", () -> deleteAptCommand.execute(modelStub));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        ModelStubWithAppointments modelStub = new ModelStubWithAppointments();
        int invalidIndex = 999;
        DeleteAptCommand deleteAptCommand = new DeleteAptCommand(invalidIndex, "John Doe");

        assertThrows(CommandException.class, "OOPS! The appointment list is empty.", (
        ) -> deleteAptCommand.execute(modelStub));
    }

    @Test
    public void execute_nonMatchingName_throwsCommandException() {
        ModelStubWithAppointments modelStub = new ModelStubWithAppointments();
        LocalDateTime dateTime = LocalDateTime.now();
        Appointment existingAppointment = new Appointment("John Doe", dateTime);
        modelStub.addAppointment(existingAppointment);

        // Trying to delete an appointment for a patient name that doesn't match
        // the patient name of the appointment at the specified index
        int appointmentIndex = 1; // Correct index but incorrect patient name for this index
        DeleteAptCommand deleteAptCommand = new DeleteAptCommand(appointmentIndex, "Jane Doe");

        assertThrows(CommandException.class, "OOPS! The deletion of the "
                + "appointment failed as the appointment of Jane Doe does not exist in the "
                + "appointment list.", () -> deleteAptCommand.execute(modelStub));
    }


    /**
     * A Model stub that contains and allows manipulation of appointments.
     */
    private class ModelStubWithAppointments extends ModelStub {
        final ArrayList<Appointment> appointments = new ArrayList<>();

        @Override
        public void addAppointment(Appointment appointment) {
            appointments.add(appointment);
        }

        @Override
        public boolean doesPersonExist(String name) {
            return appointments.stream().anyMatch(appointment -> appointment.getPatientName().equals(name));
        }

        @Override
        public void deleteAppointment(Appointment appointment) {
            appointments.remove(appointment);
        }

        @Override
        public ObservableList<Appointment> getFilteredAppointmentList() {
            // Assuming you have a method to convert your ArrayList to an ObservableList
            // This conversion is necessary because your command likely operates on ObservableList
            return FXCollections.observableArrayList(appointments);
        }

        // Implement other abstract methods as necessary, throwing AssertionError for unimplemented methods
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
