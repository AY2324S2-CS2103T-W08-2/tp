package vitalconnect.logic.parser;

import static vitalconnect.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static vitalconnect.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import vitalconnect.commons.core.LogsCenter;
import vitalconnect.logic.commands.AddCommand;
import vitalconnect.logic.commands.AddContactCommand;
import vitalconnect.logic.commands.ClearCommand;
import vitalconnect.logic.commands.Command;
import vitalconnect.logic.commands.CreateAptCommand;
import vitalconnect.logic.commands.DeleteAptCommand;
import vitalconnect.logic.commands.DeleteCommand;
import vitalconnect.logic.commands.DeleteContactCommand;
import vitalconnect.logic.commands.EditCommand;
import vitalconnect.logic.commands.ExitCommand;
import vitalconnect.logic.commands.FindCommand;
import vitalconnect.logic.commands.HelpCommand;
import vitalconnect.logic.commands.ListAptCommand;
import vitalconnect.logic.commands.ListCommand;
import vitalconnect.logic.parser.exceptions.ParseException;




/**
 * Parses user input.
 */
public class ClinicParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(ClinicParser.class);

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        // Note to developers: Change the log level in config.json to enable lower level (i.e., FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);

        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case CreateAptCommand.COMMAND_WORD:
            return new CreateAptCommandParser().parse(arguments);

        case ListAptCommand.COMMAND_WORD:
            return new ListAptCommand();

        case DeleteAptCommand.COMMAND_WORD:
            return new DeleteAptCommandParser().parse(arguments);

        case AddContactCommand.COMMAND_WORD:
            return new AddContactCommandParser().parse(arguments);

        case DeleteContactCommand.COMMAND_WORD:
            return new DeleteContactCommandParser().parse(arguments);

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
