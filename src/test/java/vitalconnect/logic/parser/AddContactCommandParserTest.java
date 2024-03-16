package vitalconnect.logic.parser;

import static vitalconnect.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static vitalconnect.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static vitalconnect.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static vitalconnect.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static vitalconnect.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static vitalconnect.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static vitalconnect.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static vitalconnect.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static vitalconnect.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static vitalconnect.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static vitalconnect.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static vitalconnect.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static vitalconnect.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static vitalconnect.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static vitalconnect.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static vitalconnect.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static vitalconnect.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static vitalconnect.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static vitalconnect.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static vitalconnect.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static vitalconnect.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static vitalconnect.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static vitalconnect.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static vitalconnect.logic.parser.CliSyntax.PREFIX_EMAIL;
import static vitalconnect.logic.parser.CliSyntax.PREFIX_NAME;
import static vitalconnect.logic.parser.CliSyntax.PREFIX_PHONE;
import static vitalconnect.logic.parser.CommandParserTestUtil.assertParseFailure;
import static vitalconnect.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static vitalconnect.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import vitalconnect.logic.Messages;
import vitalconnect.logic.commands.AddContactCommand;
import vitalconnect.model.person.Person;
import vitalconnect.model.person.contactinformation.Address;
import vitalconnect.model.person.contactinformation.ContactInformation;
import vitalconnect.model.person.contactinformation.Email;
import vitalconnect.model.person.contactinformation.Phone;
import vitalconnect.model.person.identificationinformation.Name;
import vitalconnect.testutil.PersonBuilder;

public class AddContactCommandParserTest {
    private AddContactCommandParser parser = new AddContactCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND).build();
        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB,
            new AddContactCommand(expectedPerson.getName(), expectedPerson.getContactInformation()));
    }

    @Test
    public void parse_someFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).withTags(VALID_TAG_HUSBAND).build();
        Email email = new Email(VALID_EMAIL_BOB);
        Phone phone = new Phone(VALID_PHONE_BOB);
        Address address = new Address(VALID_ADDRESS_BOB);
        // only phone present
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB,
            new AddContactCommand(expectedPerson.getName(), new ContactInformation(null, phone, null)));
        // only email present
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + EMAIL_DESC_BOB,
            new AddContactCommand(expectedPerson.getName(), new ContactInformation(email, null, null)));
        // only address present
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + ADDRESS_DESC_BOB,
            new AddContactCommand(expectedPerson.getName(), new ContactInformation(null, null, address)));
        // only phone and email present
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB,
            new AddContactCommand(expectedPerson.getName(), new ContactInformation(email, phone, null)));
        // only phone and address present, order change
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + ADDRESS_DESC_BOB + PHONE_DESC_BOB,
            new AddContactCommand(expectedPerson.getName(), new ContactInformation(null, phone, address)));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddContactCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
            expectedMessage);
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedPersonString = NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
            + ADDRESS_DESC_BOB + TAG_DESC_FRIEND;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedPersonString,
            Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple phones
        assertParseFailure(parser, PHONE_DESC_AMY + validExpectedPersonString,
            Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple emails
        assertParseFailure(parser, EMAIL_DESC_AMY + validExpectedPersonString,
            Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple addresses
        assertParseFailure(parser, ADDRESS_DESC_AMY + validExpectedPersonString,
            Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // multiple fields repeated
        assertParseFailure(parser,
            validExpectedPersonString + PHONE_DESC_AMY + EMAIL_DESC_AMY + NAME_DESC_AMY + ADDRESS_DESC_AMY
            + validExpectedPersonString,
            Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_ADDRESS, PREFIX_EMAIL, PREFIX_PHONE));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedPersonString,
            Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, INVALID_EMAIL_DESC + validExpectedPersonString,
            Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, INVALID_PHONE_DESC + validExpectedPersonString,
            Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid address
        assertParseFailure(parser, INVALID_ADDRESS_DESC + validExpectedPersonString,
            Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedPersonString + INVALID_NAME_DESC,
            Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, validExpectedPersonString + INVALID_EMAIL_DESC,
            Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, validExpectedPersonString + INVALID_PHONE_DESC,
            Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid address
        assertParseFailure(parser, validExpectedPersonString + INVALID_ADDRESS_DESC,
            Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
            + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
            + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB
            + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
            + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Address.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
            Name.MESSAGE_CONSTRAINTS);
    }
}
