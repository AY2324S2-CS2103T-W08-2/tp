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
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_PERSON_NOT_FOUND = "Person not found";
    public static final String MESSAGE_EMAIL_NOT_FOUND = "Email not found";
    public static final String MESSAGE_PHONE_NOT_FOUND = "Phone not found";
    public static final String MESSAGE_ADDRESS_NOT_FOUND = "Address not found";
    public static final String MESSAGE_EMAIL_ALREADY_EXIST = "Email already exists";
    public static final String MESSAGE_PHONE_ALREADY_EXIST = "Phone already exists";
    public static final String MESSAGE_ADDRESS_ALREADY_EXIST = "Address already exists";
    public static final String MESSAGE_NO_PREFIX_PROVIDED = "At least one prefix must be provided";
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
        builder.append(person.getName())
                .append("; Phone: ")
                .append(person.getPhone())
                .append("; Email: ")
                .append(person.getEmail())
                .append("; Address: ")
                .append(person.getAddress())
                .append("; Tags: ");
        person.getTags().forEach(builder::append);
        return builder.toString();
    }

}
