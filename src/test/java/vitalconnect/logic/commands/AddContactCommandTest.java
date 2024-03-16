package vitalconnect.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static vitalconnect.testutil.Assert.assertThrows;
import static vitalconnect.testutil.TypicalPersons.getTypicalClinic;

import org.junit.jupiter.api.Test;

import vitalconnect.logic.Messages;
import vitalconnect.logic.commands.exceptions.CommandException;
import vitalconnect.model.Model;
import vitalconnect.model.ModelManager;
import vitalconnect.model.UserPrefs;
import vitalconnect.model.person.Person;
import vitalconnect.model.person.contactinformation.Address;
import vitalconnect.model.person.contactinformation.ContactInformation;
import vitalconnect.model.person.contactinformation.Email;
import vitalconnect.model.person.contactinformation.Phone;
import vitalconnect.model.person.identificationinformation.Name;

public class AddContactCommandTest {

    private Model model = new ModelManager(getTypicalClinic(), new UserPrefs());

    @Test
    public void constructor_nullContactInformation_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddContactCommand(null, null));
    }

    @Test
    public void execute_personNotFind_failure() {
        assertThrows(CommandException.class,
            Messages.MESSAGE_PERSON_NOT_FOUND, () -> new AddContactCommand(new Name("Amy"),
                new ContactInformation()).execute(model));
    }

    @Test
    public void execute_duplicateEmail_failure() {
        Person personInList = model.getClinic().getPersonList().get(0);
        personInList.setContactInformation(new ContactInformation(new Email("123@qq.com"),
            new Phone("123123"), new Address("123123")));
        ContactInformation contactInformationInList = personInList.getContactInformation();
        AddContactCommand addContactCommand = new AddContactCommand(personInList
            .getIdentificationInformation().getName(), contactInformationInList);

        assertThrows(CommandException.class, Messages.MESSAGE_EMAIL_ALREADY_EXIST, () ->
            addContactCommand.execute(model));
    }
    @Test
    public void execute_duplicatePhone_failure() {
        Person personInList = model.getClinic().getPersonList().get(0);
        personInList.setContactInformation(new ContactInformation(new Email(""),
            new Phone("123123"), new Address("123123")));
        ContactInformation contactInformationInList = personInList.getContactInformation();
        AddContactCommand addContactCommand = new AddContactCommand(personInList
            .getIdentificationInformation().getName(), contactInformationInList);

        assertThrows(CommandException.class, Messages.MESSAGE_PHONE_ALREADY_EXIST, () ->
            addContactCommand.execute(model));
    }

    @Test
    public void execute_duplicateAddress_failure() {
        Person personInList = model.getClinic().getPersonList().get(0);
        personInList.setContactInformation(new ContactInformation(new Email(""), new Phone(""), new Address("123123")));
        ContactInformation contactInformationInList = personInList.getContactInformation();
        AddContactCommand addContactCommand = new AddContactCommand(personInList
            .getIdentificationInformation().getName(), contactInformationInList);

        assertThrows(CommandException.class, Messages.MESSAGE_ADDRESS_ALREADY_EXIST, () ->
            addContactCommand.execute(model));
    }

    @Test
    public void execute_addEmail_success() throws CommandException {
        Person personInList = model.getClinic().getPersonList().get(0);
        personInList.getContactInformation().setEmail(new Email(""));
        Email emailToAdd = new Email("12345678@email.com");
        AddContactCommand addContactCommand = new AddContactCommand(personInList
            .getIdentificationInformation().getName(),
            new ContactInformation(emailToAdd, null, null));

        addContactCommand.execute(model);
        personInList = model.getClinic().getPersonList().get(0);
        assertEquals(personInList.getContactInformation().getEmail(), emailToAdd);
    }

    @Test
    public void execute_addPhone_success() throws CommandException {
        Person personInList = model.getClinic().getPersonList().get(0);
        personInList.getContactInformation().setPhone(new Phone(""));
        Phone phoneToAdd = new Phone("11111111");
        AddContactCommand addContactCommand = new AddContactCommand(personInList
            .getIdentificationInformation().getName(),
            new ContactInformation(null, phoneToAdd, null));

        addContactCommand.execute(model);
        personInList = model.getClinic().getPersonList().get(0);
        assertEquals(personInList.getContactInformation().getPhone(), phoneToAdd);
    }

    @Test
    public void execute_addAddress_success() throws CommandException {
        Person personInList = model.getClinic().getPersonList().get(0);
        personInList.getContactInformation().setAddress(new Address(""));
        Address addressToAdd = new Address("Some Address");
        AddContactCommand addContactCommand = new AddContactCommand(personInList
            .getIdentificationInformation().getName(),
            new ContactInformation(null, null, addressToAdd));

        addContactCommand.execute(model);
        personInList = model.getClinic().getPersonList().get(0);
        assertEquals(personInList.getContactInformation().getAddress(), addressToAdd);
    }
}
