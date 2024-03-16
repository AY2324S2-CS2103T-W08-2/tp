package vitalconnect.logic.commands;

import static java.util.Objects.requireNonNull;
import static vitalconnect.logic.Messages.MESSAGE_ADDRESS_NOT_FOUND;
import static vitalconnect.logic.Messages.MESSAGE_EMAIL_NOT_FOUND;
import static vitalconnect.logic.Messages.MESSAGE_NO_PREFIX_PROVIDED;
import static vitalconnect.logic.Messages.MESSAGE_PERSON_NOT_FOUND;
import static vitalconnect.logic.Messages.MESSAGE_PHONE_NOT_FOUND;
import static vitalconnect.logic.parser.CliSyntax.OPTION_ADDRESS;
import static vitalconnect.logic.parser.CliSyntax.OPTION_EMAIL;
import static vitalconnect.logic.parser.CliSyntax.OPTION_PHONE;
import static vitalconnect.logic.parser.CliSyntax.PREFIX_NAME;
import static vitalconnect.logic.parser.CliSyntax.PREFIX_OPTION;
import static vitalconnect.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.Objects;

import vitalconnect.logic.commands.exceptions.CommandException;
import vitalconnect.logic.parser.Option;
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
        + PREFIX_OPTION + " "
        + OPTION_PHONE + " "
        + OPTION_EMAIL + " "
        + OPTION_ADDRESS
        + "\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_NAME + "John Doe "
        + PREFIX_OPTION + " "
        + OPTION_PHONE;

    private final Name name;
    private final ArrayList<Option> options;

    /**
     * Creates an AddCommand to add the specified {@code ContactInformation}
     */
    public DeleteContactCommand(Name name, ArrayList<Option> options) {
        requireNonNull(name);
        this.name = name;
        this.options = options;
    }

    /**
     * Deletes a contact of a person.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        // if person not exist, throw error
        Person personToEdit = model.findPersonByName(name);
        if (personToEdit == null) {
            throw new CommandException(MESSAGE_PERSON_NOT_FOUND);
        }
        // fetch current person contact information
        ContactInformation ciToEdit = personToEdit.getContactInformation();
        // check if all fields are null
        if (options.stream().allMatch(Objects::isNull)) {
            throw new CommandException(MESSAGE_NO_PREFIX_PROVIDED);
        }
        // Run through all the options
        for (Option option: options) {
            if (option == null) {
                continue;
            }

            // Check if the field exists on the person's contact information
            if (option.equals(OPTION_EMAIL) && ciToEdit.getEmailValue().equals("")) {
                throw new CommandException(MESSAGE_EMAIL_NOT_FOUND);
            }
            if (option.equals(OPTION_PHONE) && ciToEdit.getPhoneValue().equals("")) {
                throw new CommandException(MESSAGE_PHONE_NOT_FOUND);
            }
            if (option.equals(OPTION_ADDRESS) && ciToEdit.getAddressValue().equals("")) {
                throw new CommandException(MESSAGE_ADDRESS_NOT_FOUND);
            }

            // Delete the respective field from the person's contact information
            if (option.equals(OPTION_EMAIL)) {
                ciToEdit.setEmail(new Email(""));
            }
            if (option.equals(OPTION_PHONE)) {
                ciToEdit.setPhone(new Phone(""));
            }
            if (option.equals(OPTION_ADDRESS)) {
                ciToEdit.setAddress(new Address(""));
            }
        }
        // update the contact information to the person
        model.updatePersonContactInformation(name, ciToEdit);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult("Contact deleted successfully");
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof DeleteContactCommand)) {
            return false;
        }
        return name.equals(((DeleteContactCommand) other).name)
            && options.equals(((DeleteContactCommand) other).options); // state check
    }
}
