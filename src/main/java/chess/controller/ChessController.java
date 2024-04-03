package chess.controller;

import chess.domain.piece.Color;
import chess.service.ChessGameService;
import chess.view.CommandArguments;
import chess.view.GameCommand;
import chess.view.InputView;
import chess.view.OutputView;
import java.util.Map;
import java.util.function.Supplier;

public class ChessController {

    private final ChessGameService chessGameService;

    public ChessController(final ChessGameService chessGameService) {
        this.chessGameService = chessGameService;
    }

    public void run() {
        OutputView.printCommandInformation();
        CommandArguments commandArguments = repeatUntilSuccess(() -> readCommandBeforeGame());
        GameCommand gameCommand = commandArguments.parseCommand();

        chessGameService.loadMoveHistory();

        while (gameCommand != GameCommand.END && !chessGameService.isGameOver()) {
            OutputView.printChessBoard(chessGameService.findAllPieces());
            commandArguments = repeatUntilSuccess(() -> readAndExecuteCommandDuringGame());
            gameCommand = commandArguments.parseCommand();
        }

        if (chessGameService.isGameOver()) {
            OutputView.printWinner(chessGameService.getCurrentTurnColor());
            chessGameService.resetGame();
        }
    }

    private CommandArguments readCommandBeforeGame() {
        CommandArguments commandArguments = InputView.readGameCommand();
        GameCommand gameCommand = commandArguments.parseCommand();
        validateCommandBeforeGame(gameCommand);

        return commandArguments;
    }

    private CommandArguments readAndExecuteCommandDuringGame() {
        CommandArguments commandArguments = InputView.readGameCommand();
        GameCommand gameCommand = commandArguments.parseCommand();
        validateCommandDuringGame(gameCommand);
        executeCommand(gameCommand, commandArguments);

        return commandArguments;
    }

    private void executeCommand(final GameCommand gameCommand, final CommandArguments commandArguments) {
        if (GameCommand.MOVE == gameCommand) {
            chessGameService.executeMoveCommand(commandArguments);
        }
        if (GameCommand.STATUS == gameCommand) {
            Map<Color, Double> scoreByColor = chessGameService.executeStatusCommand();
            OutputView.printScoreStatus(scoreByColor);
        }
    }

    private void validateCommandBeforeGame(final GameCommand gameCommand) {
        if (!gameCommand.canExecuteBeforeGame()) {
            throw new IllegalArgumentException("[ERROR] 먼저 게임을 시작해야 합니다.");
        }
    }

    private void validateCommandDuringGame(final GameCommand gameCommand) {
        if (!gameCommand.canExecuteDuringGame()) {
            throw new IllegalArgumentException("[ERROR] 이미 게임이 실행중입니다.");
        }
    }

    private CommandArguments repeatUntilSuccess(final Supplier<CommandArguments> reader) {
        try {
            return reader.get();
        } catch (Exception e) {
            OutputView.printErrorMessage(e.getMessage());
            return repeatUntilSuccess(reader);
        }
    }
}
