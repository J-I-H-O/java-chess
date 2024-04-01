package chess.view;

import java.util.Scanner;

public class InputView {

    private static final Scanner SCANNER = new Scanner(System.in);

    public static CommandArguments readGameCommand() {
        String rawInput = SCANNER.nextLine();
        return new CommandArguments(rawInput);
    }
}
