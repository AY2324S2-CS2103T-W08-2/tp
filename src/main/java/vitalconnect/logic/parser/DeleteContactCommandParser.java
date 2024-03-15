package vitalconnect.logic.parser;



import static vitalconnect.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static vitalconnect.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static vitalconnect.logic.parser.CliSyntax.PREFIX_EMAIL;
import static vitalconnect.logic.parser.CliSyntax.PREFIX_NAME;
import static vitalconnect.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.stream.Stream;

import vitalconnect.logic.commands.DeleteContactCommand;
import vitalconnect.logic.parser.exceptions.ParseException;
import vitalconnect.model.person.identificationinformation.Name;


/**
 * Parses input arguments and creates a new DeleteContactCommand object
 */
public class DeleteContactCommandParser implements Parser<DeleteContactCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteContactCommand
     */
    @Override
    public DeleteContactCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(userInput, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteContactCommand.MESSAGE_USAGE));
        }

        Prefix[] options = new Prefix[3];
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            System.out.println("Phone is present");
            System.out.println(argMultimap.getValue(PREFIX_PHONE).get());
            options[0] = PREFIX_PHONE;
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            System.out.println("Email is present");
            System.out.println(argMultimap.getValue(PREFIX_EMAIL).get());
            options[1] = PREFIX_EMAIL;
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            System.out.println("Address is present");
            System.out.println(argMultimap.getValue(PREFIX_ADDRESS).get());
            options[2] = PREFIX_ADDRESS;
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        return new DeleteContactCommand(name, options);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
