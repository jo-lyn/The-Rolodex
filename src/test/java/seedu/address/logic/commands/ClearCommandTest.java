package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalRolodex;

import org.junit.Test;

import guitests.RolodexGuiTest;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearCommandTest extends RolodexGuiTest {

    @Test
    public void executeEmptyRolodexSuccess() {
        Model model = new ModelManager();
        assertCommandSuccess(prepareCommand(model), model, ClearCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void executeNonEmptyRolodexSuccess() {
        Model model = new ModelManager(getTypicalRolodex(), new UserPrefs());
        assertCommandSuccess(prepareCommand(model), model, ClearCommand.MESSAGE_SUCCESS, model);
    }

    /**
     * Generates a new {@code ClearCommand} which upon execution, clears the contents in {@code model}.
     */
    private ClearCommand prepareCommand(Model model) {
        ClearCommand command = new ClearCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
