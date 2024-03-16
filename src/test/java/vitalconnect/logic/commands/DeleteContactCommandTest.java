package vitalconnect.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static vitalconnect.logic.parser.CliSyntax.OPTION_ADDRESS;
import static vitalconnect.logic.parser.CliSyntax.OPTION_EMAIL;
import static vitalconnect.logic.parser.CliSyntax.OPTION_PHONE;
import static vitalconnect.testutil.Assert.assertThrows;
import static vitalconnect.testutil.TypicalPersons.getTypicalClinic;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import vitalconnect.logic.Messages;
import vitalconnect.logic.commands.exceptions.CommandException;
import vitalconnect.logic.parser.Option;
import vitalconnect.model.Model;
import vitalconnect.model.ModelManager;
import vitalconnect.model.UserPrefs;
import vitalconnect.model.person.Person;
import vitalconnect.model.person.contactinformation.Address;
import vitalconnect.model.person.contactinformation.ContactInformation;
import vitalconnect.model.person.contactinformation.Email;
import vitalconnect.model.person.contactinformation.Phone;
import vitalconnect.model.person.identificationinformation.Name;

public class DeleteContactCommandTest {

    private Model model = new ModelManager(getTypicalClinic(), new UserPrefs());

    @Test
    public void constructor_nullContactInformation_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteContactCommand(null, null));
    }



    @Test
    public void execute_personNotFind_failure() {
        ArrayList<Option> options = new ArrayList<>();
        options.add(OPTION_PHONE);
        options.add(OPTION_EMAIL);
        options.add(OPTION_ADDRESS);
        assertThrows(CommandException.class, Messages.MESSAGE_PERSON_NOT_FOUND, () ->
          new DeleteContactCommand(new Name("Amy"), options).execute(model));
    }

    @Test
    public void execute_allPrefixesNotProvided_failure() {
        ArrayList<Option> options = new ArrayList<>();
        Person personInList = model.getClinic().getPersonList().get(0);
        assertThrows(CommandException.class, Messages.MESSAGE_NO_PREFIX_PROVIDED, () ->
          new DeleteContactCommand(personInList.getName(), options).execute(model));
    }

    @Test
    public void execute_deleteNullEmail_failure() {
        ArrayList<Option> options = new ArrayList<>();
        options.add(OPTION_EMAIL);
        Person personInList = model.getClinic().getPersonList().get(0);
        personInList.getContactInformation().setEmail(new Email(""));
        assertThrows(CommandException.class, Messages.MESSAGE_EMAIL_NOT_FOUND, () ->
          new DeleteContactCommand(personInList.getName(), options).execute(model));
    }

    @Test
    public void execute_deleteNullPhone_failure() {
        ArrayList<Option> options = new ArrayList<>();
        options.add(OPTION_PHONE);
        Person personInList = model.getClinic().getPersonList().get(0);
        personInList.getContactInformation().setPhone(new vitalconnect.model.person.contactinformation.Phone(""));
        assertThrows(CommandException.class, Messages.MESSAGE_PHONE_NOT_FOUND, () ->
          new DeleteContactCommand(personInList.getName(), options).execute(model));
    }

    @Test
    public void execute_deleteNullAddress_failure() {
        ArrayList<Option> options = new ArrayList<>();
        options.add(OPTION_ADDRESS);
        Person personInList = model.getClinic().getPersonList().get(0);
        personInList.getContactInformation().setAddress(new vitalconnect.model.person.contactinformation.Address(""));
        assertThrows(CommandException.class, Messages.MESSAGE_ADDRESS_NOT_FOUND, () ->
          new DeleteContactCommand(personInList.getName(), options).execute(model));
    }

    @Test
    public void execute_deleteEmail_success() throws CommandException {
        ArrayList<Option> options = new ArrayList<>();
        options.add(OPTION_EMAIL);
        Person personInList = model.getClinic().getPersonList().get(0);
        personInList.setContactInformation(new ContactInformation(new Email("111@email.com"),
            new Phone("111"), new Address("111")));
        DeleteContactCommand deleteContactCommand = new DeleteContactCommand(personInList.getName(), options);
        deleteContactCommand.execute(model);
        assertEquals(new Email(""), personInList.getContactInformation().getEmail());
        model = new ModelManager(getTypicalClinic(), new UserPrefs());
    }

    @Test
    public void execute_deletePhone_success() throws CommandException {
        ArrayList<Option> options = new ArrayList<>();
        options.add(OPTION_PHONE);
        Person personInList = model.getClinic().getPersonList().get(0);
        personInList.setContactInformation(new ContactInformation(new Email("111@email.com"),
            new Phone("111"), new Address("111")));
        DeleteContactCommand deleteContactCommand = new DeleteContactCommand(personInList.getName(), options);
        deleteContactCommand.execute(model);
        assertEquals(new Phone(""), personInList.getContactInformation().getPhone());
        model = new ModelManager(getTypicalClinic(), new UserPrefs());
    }

    @Test
    public void execute_deleteAddress_success() throws CommandException {
        ArrayList<Option> options = new ArrayList<>();
        options.add(OPTION_ADDRESS);
        Person personInList = model.getClinic().getPersonList().get(0);
        personInList.setContactInformation(new ContactInformation(new Email("111@email.com"),
            new Phone("111"), new Address("111")));
        DeleteContactCommand deleteContactCommand = new DeleteContactCommand(personInList.getName(), options);
        deleteContactCommand.execute(model);
        assertEquals(new Address(""), personInList.getContactInformation().getAddress());
        model = new ModelManager(getTypicalClinic(), new UserPrefs());
    }

    @Test
    public void execute_deleteTwoFields_success() throws CommandException {
        ArrayList<Option> options = new ArrayList<>();
        options.add(OPTION_PHONE);
        options.add(OPTION_EMAIL);
        Person personInList = model.getClinic().getPersonList().get(0);
        personInList.setContactInformation(new ContactInformation(new Email("111@email.com"),
            new Phone("111"), new Address("111")));
        DeleteContactCommand deleteContactCommand = new DeleteContactCommand(personInList.getName(), options);
        deleteContactCommand.execute(model);
        assertEquals(new Email(""), personInList.getContactInformation().getEmail());
        assertEquals(new Phone(""), personInList.getContactInformation().getPhone());
        model = new ModelManager(getTypicalClinic(), new UserPrefs());
    }

    @Test
    public void execute_deleteThreeFields_success() throws CommandException {
        ArrayList<Option> options = new ArrayList<>();
        options.add(OPTION_PHONE);
        options.add(OPTION_EMAIL);
        options.add(OPTION_ADDRESS);
        Person personInList = model.getClinic().getPersonList().get(0);
        personInList.setContactInformation(new ContactInformation(new Email("111@email.com"),
            new Phone("111"), new Address("111")));
        DeleteContactCommand deleteContactCommand = new DeleteContactCommand(personInList.getName(), options);
        deleteContactCommand.execute(model);
        assertEquals(new Email(""), personInList.getContactInformation().getEmail());
        assertEquals(new Phone(""), personInList.getContactInformation().getPhone());
        assertEquals(new Address(""), personInList.getContactInformation().getAddress());
        model = new ModelManager(getTypicalClinic(), new UserPrefs());
    }
}
