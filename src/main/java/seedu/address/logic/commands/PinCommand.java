package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class PinCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "pin";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Pins the selected person identified "
            + "by the index number used in the last person listing.\n "
            + "Parameters: INDEX (must be a positive integer)\n "
            + "Example: " + COMMAND_WORD + " 1 ";

    private static final String MESSAGE_PIN_PERSON_SUCCESS = "Pinned Person: %1$s";
    private static final String MESSAGE_ALREADY_PINNED = "Person is already pinned!";

    private final Index index;

    public PinCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToPin = lastShownList.get(index.getZeroBased());

        if (personToPin.isPinned()) {
            return new CommandResult(MESSAGE_ALREADY_PINNED);
        } else {
            personToPin.setPin();
            return new CommandResult(String.format(MESSAGE_PIN_PERSON_SUCCESS, personToPin));
        }

    }
}
