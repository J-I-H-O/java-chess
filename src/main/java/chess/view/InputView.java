package chess.view;

import java.util.Scanner;

public class InputView {

    private final Scanner scanner = new Scanner(System.in);

    public CommandArguments readGameCommand() {
        String rawInput = scanner.nextLine();
        return new CommandArguments(rawInput);
    }
}
