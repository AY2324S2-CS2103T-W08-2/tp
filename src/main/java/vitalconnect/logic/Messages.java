package vitalconnect.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import vitalconnect.logic.parser.Prefix;
import vitalconnect.model.person.Person;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_APPOINTMENTS_LISTED_OVERVIEW =
            "%1$d appointments found for the specified patient.";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_PERSON_NOT_FOUND = "Person not found";
    public static final String MESSAGE_PERSON_ALREADY_EXIST = "Person already exists";
    public static final String MESSAGE_MEDICAL_INFO_ALREADY_EXIST = "Person's medical information already exists";
    public static final String MESSAGE_MEDICAL_INFO_NOT_FOUND = "Medical information not found";
    public static final String MESSAGE_CONTACT_INFO_NOT_FOUND = "Contact information not found";
    public static final String MESSAGE_CONTACT_INFO_ALREADY_EXIST = "Contact information already exists";
    public static final String MESSAGE_NO_PREFIX_PROVIDED = "At least one prefix must be provided";
    public static final String MESSAGE_ALLERGY_ALREADY_EXIST = "Allergy already exists";
    public static final String MESSAGE_OPTION_NOT_VALID = "Option not valid";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Person person) {
        final StringBuilder builder = new StringBuilder();
        builder.append(person.getIdentificationInformation().getName())
                .append("; NRIC: ")
                .append(person.getIdentificationInformation().getNric());
        return builder.toString();
    }

}
