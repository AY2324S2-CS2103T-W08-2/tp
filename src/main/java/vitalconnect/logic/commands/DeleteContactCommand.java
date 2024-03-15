package vitalconnect.logic.commands;

import static vitalconnect.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static vitalconnect.logic.parser.CliSyntax.PREFIX_EMAIL;
import static vitalconnect.logic.parser.CliSyntax.PREFIX_NAME;
import static vitalconnect.logic.parser.CliSyntax.PREFIX_PHONE;
import static vitalconnect.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import vitalconnect.logic.commands.exceptions.CommandException;
import vitalconnect.logic.parser.Prefix;
import vitalconnect.model.Model;
import vitalconnect.model.person.Person;
import vitalconnect.model.person.contactinformation.Address;
import vitalconnect.model.person.contactinformation.ContactInformation;
import vitalconnect.model.person.contactinformation.Email;
import vitalconnect.model.person.contactinformation.Phone;
import vitalconnect.model.person.identificationinformation.Name;

/**
 * Deletes a person from the clinic.
 */
public class DeleteContactCommand extends Command {
    public static final String COMMAND_WORD = "delContact";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Delete a contact of a person. "
        + "Parameters: (required field)\n"
        + PREFIX_NAME + "NAME "
        + "(optional but at least specify one)\n"
        + PREFIX_PHONE
        + PREFIX_EMAIL
        + PREFIX_ADDRESS
        + "\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_NAME + "John Doe "
        + PREFIX_PHONE;

    private final Name name;
    private final Prefix[] options;

    /**
     * Creates an AddCommand to add the specified {@code ContactInformation}
     */
    public DeleteContactCommand(Name name, Prefix[] options) {
        this.name = name;
        this.options = options;
    }

    /**
     * Deletes a contact of a person.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        // if person not exist, throw error
        Person personToEdit = model.findPersonByName(name);
        if (personToEdit == null) {
            throw new CommandException("Person not found");
        }
        // fetch current person contact information
        ContactInformation ciToEdit = personToEdit.getContactInformation();
        System.out.println("aha!!!");
        System.out.println(ciToEdit.toString());
        // check if all fields are null
        if (options[0] == null && options[1] == null && options[2] == null) {
            throw new CommandException("At least one field should not be empty");
        }
        // Run through all the options
        for (Prefix option: options) {
            if (option == null) {
                continue;
            }

            // Check if the field exists on the person's contact information
            if (option.equals(PREFIX_EMAIL) && ciToEdit.getEmailValue().equals("")) {
                throw new CommandException("Email not found on the person");
            }
            if (option.equals(PREFIX_PHONE) && ciToEdit.getPhoneValue().equals("")) {
                throw new CommandException("Phone not found on the person");
            }
            if (option.equals(PREFIX_ADDRESS) && ciToEdit.getAddressValue().equals("")) {
                throw new CommandException("Address not found on the person");
            }

            // Delete the respective field from the person's contact information
            if (option.equals(PREFIX_EMAIL)) {
                ciToEdit.setEmail(new Email(""));
            }
            if (option.equals(PREFIX_PHONE)) {
                ciToEdit.setPhone(new Phone(""));
            }
            if (option.equals(PREFIX_ADDRESS)) {
                ciToEdit.setAddress(new Address(""));
            }
        }
        // update the contact information to the person
        model.updatePersonContactInformation(name, ciToEdit);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult("Contact deleted successfully");
    }
}
