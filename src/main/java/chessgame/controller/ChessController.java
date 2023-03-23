package chessgame.controller;

import chessgame.domain.Game;
import chessgame.view.InputView;
import chessgame.view.OutputView;

public class ChessController {
    private final InputView inputView;
    private final OutputView outputView;

    public ChessController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        Game game = new Game();
        playGame(game);
    }

    private void playGame(Game game) {
        outputView.printStartMessage();
        do {
            eachTurn(game);
        } while (game.isNotEnd());
    }

    private void eachTurn(Game game) {
        Command command = readCommand();
        try {
            game.setFrom(command);
        } catch (IllegalArgumentException e) {
            outputView.printErrorMsg(e.getMessage());
        }
        printChessBoard(game);
    }

    private Command readCommand() {
        Command command;
        do {
            command = generateCommand();
        } while (command == null);
        return command;
    }

    private Command generateCommand() {
        try {
            return Command.of(inputView.readCommand());
        } catch (IllegalArgumentException e) {
            outputView.printErrorMsg(e.getMessage());
            return null;
        }
    }

    private void printChessBoard(Game game) {
        if (game.isRunning()) {
            outputView.printChessBoard(game.board());
        }
    }
}
