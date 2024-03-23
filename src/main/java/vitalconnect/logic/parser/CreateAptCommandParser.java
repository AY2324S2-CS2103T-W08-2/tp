package vitalconnect.logic.parser;

import vitalconnect.logic.commands.CreateAptCommand;
import vitalconnect.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new CreateAptCommand object.
 * This parser is responsible for handling the raw input arguments provided
 * for the creation of an appointment and ensuring they meet the expected format
 * which includes the patient's NRIC and the appointment date and time.
 */
public class CreateAptCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the CreateAptCommand
     * and returns a CreateAptCommand object for execution.
     * <p>
     * This method expects the arguments to contain the NRIC of a patient followed by
     * the '/time' keyword and the appointment datetime in 'dd/MM/yyyy HHmm' format.
     * If the arguments do not conform to this expected format, a ParseException is thrown.
     *
     * @param args The input arguments to be parsed, including the patient's NRIC and
     *             the appointment datetime.
     * @return A new CreateAptCommand object encapsulating the parsed patient NRIC
     *         and appointment datetime.
     * @throws ParseException If the provided arguments do not conform to the expected
     *                        format or if other parsing errors occur.
     */
    public CreateAptCommand parse(String args) throws ParseException {
        final String[] nameAndDateTime = args.trim().split("/time", 2);

        if (nameAndDateTime.length < 2) {
            throw new ParseException(CreateAptCommand.MESSAGE_USAGE);
        }

        String ic = nameAndDateTime[0].trim();
        String dateTimeStr = nameAndDateTime[1].trim();


        return new CreateAptCommand(ic, dateTimeStr);
    }
}
