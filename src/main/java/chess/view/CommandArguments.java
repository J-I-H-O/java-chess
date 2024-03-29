package chess.view;

import java.util.List;

public class CommandArguments {

    private static final String DELIMITER = " ";
    private static final int COMMAND_INDEX = 0;
    private static final int FIRST_ARGUMENT_INDEX = 1;
    private static final int SECOND_ARGUMENT_INDEX = 2;

    private final List<String> splitInput;

    public CommandArguments(final String rawInput) {
        splitInput = List.of(rawInput.split(DELIMITER));
    }

    public GameCommand parseCommand() {
        String rawCommand = splitInput.get(COMMAND_INDEX);
        return GameCommand.findCommand(rawCommand);
    }

    public String getFirstArgument() {
        return splitInput.get(FIRST_ARGUMENT_INDEX);
    }

    public String getSecondArgument() {
        return splitInput.get(SECOND_ARGUMENT_INDEX);
    }
}
