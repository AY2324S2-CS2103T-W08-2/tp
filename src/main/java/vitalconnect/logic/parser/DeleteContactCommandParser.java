package vitalconnect.logic.parser;



import static vitalconnect.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static vitalconnect.logic.parser.CliSyntax.PREFIX_NAME;
import static vitalconnect.logic.parser.CliSyntax.PREFIX_OPTION;

import java.util.ArrayList;
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
            ArgumentTokenizer.tokenize(userInput, PREFIX_NAME, PREFIX_OPTION);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_OPTION)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteContactCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME);
        // Parse options
        ArrayList<Option> options = new ArrayList<>();
        if (argMultimap.getValue(PREFIX_OPTION).isPresent()) {
            String[] optionStrings = argMultimap.getValue(PREFIX_OPTION).get().split(" ");
            options = stringToOptionArray(optionStrings);
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        return new DeleteContactCommand(name, options);
    }

    private ArrayList<Option> stringToOptionArray(String[] optionStrings) throws ParseException {
        ArrayList<Option> options = new ArrayList<>();
        for (String option : optionStrings) {
            if (Option.of(option) == null) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteContactCommand.MESSAGE_USAGE));
            }
            options.add(Option.of(option));
        }
        return options;
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
