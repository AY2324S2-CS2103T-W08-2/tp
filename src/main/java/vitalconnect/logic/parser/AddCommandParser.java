package vitalconnect.logic.parser;

import static vitalconnect.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static vitalconnect.logic.parser.CliSyntax.*;

import java.util.Set;
import java.util.stream.Stream;

import vitalconnect.logic.commands.AddCommand;
import vitalconnect.logic.parser.exceptions.ParseException;
import vitalconnect.model.person.Person;
import vitalconnect.model.person.contactinformation.Address;
import vitalconnect.model.person.contactinformation.Email;
import vitalconnect.model.person.contactinformation.Phone;
import vitalconnect.model.person.identificationinformation.Name;
import vitalconnect.model.person.medicalinformation.Allergy;
import vitalconnect.model.person.medicalinformation.Height;
import vitalconnect.model.person.medicalinformation.Weight;
import vitalconnect.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_TAG, PREFIX_HEIGHT, PREFIX_WEIGHT, PREFIX_ALLERGY);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_HEIGHT, PREFIX_WEIGHT, PREFIX_ALLERGY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                PREFIX_HEIGHT, PREFIX_WEIGHT, PREFIX_ALLERGY);
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        Height height = ParserUtil.parseHeight(argMultimap.getValue(PREFIX_HEIGHT).get());
        Weight weight = ParserUtil.parseWeight(argMultimap.getValue(PREFIX_WEIGHT).get());
        Set<Allergy> allergySet = ParserUtil.parseAllergies(argMultimap.getAllValues(PREFIX_ALLERGY));
        Person person = new Person(name, phone, email, address, tagList, height, weight, allergySet);

        return new AddCommand(person);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
