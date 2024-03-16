package vitalconnect.logic.parser;

import static vitalconnect.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static vitalconnect.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static vitalconnect.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static vitalconnect.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static vitalconnect.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static vitalconnect.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static vitalconnect.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static vitalconnect.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static vitalconnect.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static vitalconnect.logic.parser.CliSyntax.OPTION_ADDRESS;
import static vitalconnect.logic.parser.CliSyntax.OPTION_EMAIL;
import static vitalconnect.logic.parser.CliSyntax.OPTION_PHONE;
import static vitalconnect.logic.parser.CliSyntax.PREFIX_NAME;
import static vitalconnect.logic.parser.CliSyntax.PREFIX_OPTION;
import static vitalconnect.logic.parser.CommandParserTestUtil.assertParseFailure;
import static vitalconnect.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static vitalconnect.testutil.TypicalPersons.BOB;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import vitalconnect.logic.Messages;
import vitalconnect.logic.commands.DeleteContactCommand;
import vitalconnect.model.person.Person;
import vitalconnect.testutil.PersonBuilder;

public class DeleteContactCommandParserTest {
    private DeleteContactCommandParser parser = new DeleteContactCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND).build();
        ArrayList<Option> options = new ArrayList<>();
        options.add(OPTION_PHONE);
        options.add(OPTION_EMAIL);
        options.add(OPTION_ADDRESS);
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + " " + PREFIX_OPTION + " "
            + OPTION_PHONE + " " + OPTION_EMAIL + " " + OPTION_ADDRESS,
            new DeleteContactCommand(expectedPerson.getName(), options));
    }

    @Test
    public void parse_someFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND).build();
        // only phone present
        ArrayList<Option> options = new ArrayList<>();
        options.add(OPTION_PHONE);
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + " " + PREFIX_OPTION + " " + OPTION_PHONE,
            new DeleteContactCommand(expectedPerson.getName(), options));
        // only email present
        options = new ArrayList<>();
        options.add(OPTION_EMAIL);
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + " " + PREFIX_OPTION + " " + OPTION_EMAIL,
            new DeleteContactCommand(expectedPerson.getName(), options));
        // only address present
        options = new ArrayList<>();
        options.add(OPTION_ADDRESS);
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + " " + PREFIX_OPTION + " " + OPTION_ADDRESS,
            new DeleteContactCommand(expectedPerson.getName(), options));
        // only phone and email present
        options = new ArrayList<>();
        options.add(OPTION_PHONE);
        options.add(OPTION_EMAIL);
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + " " + PREFIX_OPTION + " " + OPTION_PHONE + " "
            + OPTION_EMAIL, new DeleteContactCommand(expectedPerson.getName(), options));
        // only phone and address present, order change
        options = new ArrayList<>();
        options.add(OPTION_PHONE);
        options.add(OPTION_ADDRESS);
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + " " + PREFIX_OPTION + " " + OPTION_PHONE + " "
            + OPTION_ADDRESS, new DeleteContactCommand(expectedPerson.getName(), options));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteContactCommand.MESSAGE_USAGE);
        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
            expectedMessage);
    }

    @Test
    public void parse_repeatedName_failure() {
        String validExpectedPersonString = NAME_DESC_BOB + " " + PREFIX_OPTION + " " + OPTION_PHONE + " " + OPTION_EMAIL
            + " " + OPTION_ADDRESS;

        System.out.println(NAME_DESC_AMY + validExpectedPersonString);

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedPersonString,
            Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));
    }
}
