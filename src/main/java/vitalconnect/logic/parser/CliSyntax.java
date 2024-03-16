package vitalconnect.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_ID = new Prefix("id/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_OPTION = new Prefix("o/");
    public static final Option OPTION_PHONE = Option.of("-p");
    public static final Option OPTION_EMAIL = Option.of("-e");
    public static final Option OPTION_ADDRESS = Option.of("-a");
}
