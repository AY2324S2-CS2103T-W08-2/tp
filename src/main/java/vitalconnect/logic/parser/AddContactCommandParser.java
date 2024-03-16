package vitalconnect.logic.parser;

import static vitalconnect.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static vitalconnect.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static vitalconnect.logic.parser.CliSyntax.PREFIX_EMAIL;
import static vitalconnect.logic.parser.CliSyntax.PREFIX_ID;
import static vitalconnect.logic.parser.CliSyntax.PREFIX_NAME;
import static vitalconnect.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.stream.Stream;

import vitalconnect.logic.commands.AddContactCommand;
import vitalconnect.logic.parser.exceptions.ParseException;
import vitalconnect.model.person.contactinformation.Address;
import vitalconnect.model.person.contactinformation.ContactInformation;
import vitalconnect.model.person.contactinformation.Email;
import vitalconnect.model.person.contactinformation.Phone;
import vitalconnect.model.person.identificationinformation.Name;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddContactCommandParser implements Parser<AddContactCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AddContactCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_NAME, PREFIX_ID,
                    PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS);

        if (!(arePrefixesPresent(argMultimap, PREFIX_NAME)
            || arePrefixesPresent(argMultimap, PREFIX_ID))
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddContactCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_ID, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS);
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        // ID to be added later
        Phone phone = null;
        Email email = null;
        Address address = null;
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        }

        // Check if at least one of the fields is present
        if (phone == null && email == null && address == null) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddContactCommand.MESSAGE_USAGE));
        }

        ContactInformation contactInformation = new ContactInformation(email, phone, address);

        return new AddContactCommand(name, contactInformation);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
