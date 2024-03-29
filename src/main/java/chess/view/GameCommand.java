package chess.view;

import java.util.Arrays;

public enum GameCommand {

    START("start", true, false),
    END("end", true, true),
    MOVE("move", false, true),
    STATUS("status", false, true),
    ;

    private final String value;
    private final boolean canExecuteBeforeGame;
    private final boolean canExecuteDuringGame;

    GameCommand(final String value, final boolean canExecuteBeforeGame, final boolean canExecuteDuringGame) {
        this.value = value;
        this.canExecuteBeforeGame = canExecuteBeforeGame;
        this.canExecuteDuringGame = canExecuteDuringGame;
    }

    public static GameCommand findCommand(final String rawCommand) {
        return Arrays.stream(values())
                .filter(command -> command.value.equals(rawCommand))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 명령어입니다."));
    }

    public boolean canExecuteBeforeGame() {
        return canExecuteBeforeGame;
    }

    public boolean canExecuteDuringGame() {
        return canExecuteDuringGame;
    }
}
