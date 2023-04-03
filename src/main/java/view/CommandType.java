package view;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public enum CommandType {
    START("start"::equals),
    END("end"::equals),
    MOVE(command -> Pattern.matches(Constants.MOVE_COMMAND_REGEX, command));

    private final Predicate<String> condition;

    CommandType(Predicate<String> condition) {
        this.condition = condition;
    }

    public static CommandType findByCommand(String command) {
        return Arrays.stream(CommandType.values())
                .filter(commandType -> commandType.condition.test(command))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 커멘드 입력입니다."));
    }

    public boolean isEnd() {
        return this == END;
    }

    public boolean isMoving() {
        return this == MOVE;
    }

    public boolean isStart() {
        return this == START;
    }

    private static class Constants {
        private static final String MOVE_COMMAND_REGEX = "^move [a-z][0-9] [a-z][0-9]$";
    }
}
