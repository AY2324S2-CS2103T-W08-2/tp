package vitalconnect.logic.commands;

import static vitalconnect.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

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


public class CreateAptCommandTest {

    @Test
    public void execute_patientNotExist_throwsCommandException() {
        ModelStub modelStub = new ModelStubWithoutPerson();
        String patientName = "NonExisting";
        String dateTimeStr = "02/02/2024 1330";
        CreateAptCommand createAptCommand = new CreateAptCommand(patientName, dateTimeStr);

        assertThrows(CommandException.class, "OOPS! The appointment cannot be created as the patient "
                + "does not exist.", () -> createAptCommand.execute(modelStub));
    }

    @Test
    public void execute_invalidDateTimeFormat_throwsCommandException() {
        ModelStub modelStub = new ModelStubAcceptingPersonAdded();
        String patientName = "John Doe";
        String dateTimeStr = "invalidDateTime";
        CreateAptCommand createAptCommand = new CreateAptCommand(patientName, dateTimeStr);

        assertThrows(CommandException.class, "OOPS! The appointment cannot be created as the time is "
                + "empty or not in the correct format.", () -> createAptCommand.execute(modelStub));
    }

    /*@Test
    public void execute_appointmentCreatedSuccessfully() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        String patientName = "John Doe";
        String dateTimeStr = "02/02/2024 1330";
        CreateAptCommand createAptCommand = new CreateAptCommand(patientName, dateTimeStr);

        CommandResult commandResult = createAptCommand.execute(modelStub);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, formatter);

        assertEquals("Created an appointment successfully!\nName: John Doe\nTime: 2 Feb. 2024 13:30",
                commandResult.getFeedbackToUser());
        assertTrue(modelStub.appointmentsAdded.stream().anyMatch(appointment ->
                appointment.getPatientName().equals(patientName)
                        && appointment.getDateTime().equals(dateTime)));
    }*/


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


    private class ModelStubWithoutPerson extends ModelStub {
        @Override
        public boolean doesPersonExist(String name) {
            return false;
        }
    }

    /**
     * A Model stub that always accept the appointment being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Appointment> appointmentsAdded = new ArrayList<>();

        @Override
        public boolean doesPersonExist(String name) {
            return true;
        }

        @Override
        public void addAppointment(Appointment appointment) {
            appointmentsAdded.add(appointment);
        }
    }
}



