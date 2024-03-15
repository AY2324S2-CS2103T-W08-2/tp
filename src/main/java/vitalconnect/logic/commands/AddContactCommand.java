package vitalconnect.logic.commands;

import static java.util.Objects.requireNonNull;
import static vitalconnect.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static vitalconnect.logic.parser.CliSyntax.PREFIX_EMAIL;
import static vitalconnect.logic.parser.CliSyntax.PREFIX_NAME;
import static vitalconnect.logic.parser.CliSyntax.PREFIX_PHONE;
import static vitalconnect.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import vitalconnect.logic.commands.exceptions.CommandException;
import vitalconnect.model.Model;
import vitalconnect.model.person.Person;
import vitalconnect.model.person.contactinformation.Address;
import vitalconnect.model.person.contactinformation.ContactInformation;
import vitalconnect.model.person.contactinformation.Email;
import vitalconnect.model.person.contactinformation.Phone;
import vitalconnect.model.person.identificationinformation.Name;

/**
 * Adds a person to the clinic.
 */
public class AddContactCommand extends Command {
    public static final String COMMAND_WORD = "addContact";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a contact of a person. "
            + "Parameters: (required field)\n"
            + PREFIX_NAME + "NAME "
            + "(optional but at least specify one)\n"
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 ";

    private final Name name;
    private final Email email;
    private final Phone phone;
    private final Address address;

    /**
     * Creates an AddCommand to add the specified {@code ContactInformation}
     */
    public AddContactCommand(Name name, ContactInformation contactInformation) {
        requireNonNull(contactInformation);
        this.name = name;
        this.email = contactInformation.getEmail();
        this.phone = contactInformation.getPhone();
        this.address = contactInformation.getAddress();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        // if person not exist, throw error
        Person p = model.findPersonByName(name);
        if (p == null) {
            throw new CommandException("Person not found");
        }
        // fetch current person contact information, update fields.
        ContactInformation ci = p.getContactInformation();
        System.out.println("aha!!!");
        System.out.println(ci.getPhone());
        // check if current field of change is already occupied
        Email emptyEmail = new Email("");
        Phone emptyPhone = new Phone("");
        Address emptyAddress = new Address("");
        if (!ci.getEmail().equals(emptyEmail) && !email.equals(emptyEmail)) {
            throw new CommandException("Email already exists");
        }
        if (!ci.getPhone().equals(emptyPhone) && !phone.equals(emptyPhone)) {
            throw new CommandException("Phone already exists");
        }
        if (!ci.getAddress().equals(emptyAddress) && !address.equals(emptyAddress)) {
            throw new CommandException("Address already exists");
        }
        // add not null contact information
        if (!email.equals(emptyEmail)) {
            ci.setEmail(email);
        }
        if (!phone.equals(emptyPhone)) {
            ci.setPhone(phone);
        }
        if (!address.equals(emptyAddress)) {
            ci.setAddress(address);
        }
        // set contact information
        model.updatePersonContactInformation(name, ci);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        // update the person to the model
        return new CommandResult("Contact added successfully");
    }
}
