package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_EXTENSION_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.storage.util.RolodexStorageUtil.ROLODEX_FILE_EXTENSION;

import org.junit.Test;

import seedu.address.logic.commands.NewCommand;

public class NewCommandParserTest {

    private NewCommandParser parser = new NewCommandParser();

    @Test
    public void parseEmptyArgThrowsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, NewCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseNonEmptyArgInvalidFilePathThrowsParseException() {
        // needs a root directory as reference for anchored paths (e.g. ./x.rldx)
        assertParseFailure(parser, "default.rldx",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, NewCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "random",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, NewCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "random.rldx",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, NewCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseFilePathInvalidExtensionThrowsFileExtensionParseException() {
        String filePathWithInvalidExtension = "C:\\Users\\Rolodex\\Downloads\\default.error";
        assertParseFailure(parser, filePathWithInvalidExtension,
                String.format(MESSAGE_INVALID_EXTENSION_FORMAT, ROLODEX_FILE_EXTENSION));

        filePathWithInvalidExtension = "directory with/some spacings/invalid.extension";
        assertParseFailure(parser, filePathWithInvalidExtension,
                String.format(MESSAGE_INVALID_EXTENSION_FORMAT, ROLODEX_FILE_EXTENSION));
    }

    @Test
    public void parseFilePathAllowsSpacing() {
        // spacing should be allowed for directories
        String filePathWithSpacing = "directory with/some spacings/default.rldx";
        NewCommand expectedOpenCommand = new NewCommand(filePathWithSpacing);
        assertParseSuccess(parser, filePathWithSpacing, expectedOpenCommand);

        filePathWithSpacing = "56 75/22 3456g 45642345/y5/ bhh3 57y357/ 65467y5 - 0 o1/validExtension.rldx";
        expectedOpenCommand = new NewCommand(filePathWithSpacing);
        assertParseSuccess(parser, filePathWithSpacing, expectedOpenCommand);
    }

    @Test
    public void parseFilePathSwitchesBackslashes() {
        // backslashes are swapped out for forward slashes in directory
        String filePathWithBackSlashes = "C:\\Users\\Rolodex\\Downloads\\default.rldx";
        String filePathWithForwardSlashes = "C:/Users/Rolodex/Downloads/default.rldx";

        NewCommand expectedOpenCommand = new NewCommand(filePathWithForwardSlashes);
        assertParseSuccess(parser, filePathWithBackSlashes, expectedOpenCommand);
    }
}
