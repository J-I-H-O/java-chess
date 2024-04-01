package chess.controller;

import chess.domain.ChessBoard;
import chess.domain.ChessBoardFactory;
import chess.domain.ChessGame;
import chess.domain.Position;
import chess.domain.ScoreCalculator;
import chess.domain.piece.Color;
import chess.view.CommandArguments;
import chess.view.GameCommand;
import chess.view.InputView;
import chess.view.OutputView;
import java.util.function.Supplier;

public class ChessController {

    public void run() {
        ChessBoard chessBoard = ChessBoardFactory.makeChessBoard();
        ChessGame chessGame = new ChessGame(chessBoard, Color.WHITE);
        OutputView.printCommandInformation();
        CommandArguments commandArguments = repeatUntilSuccess(() -> readCommandBeforeGame());
        GameCommand gameCommand = commandArguments.parseCommand();

        while (gameCommand != GameCommand.END && !chessGame.isGameOver()) {
            OutputView.printChessBoard(chessBoard);
            commandArguments = repeatUntilSuccess(() -> readAndExecuteCommandDuringGame(chessGame));
            gameCommand = commandArguments.parseCommand();
        }
    }

    private CommandArguments readCommandBeforeGame() {
        CommandArguments commandArguments = InputView.readGameCommand();
        GameCommand gameCommand = commandArguments.parseCommand();
        validateCommandBeforeGame(gameCommand);

        return commandArguments;
    }

    private CommandArguments readAndExecuteCommandDuringGame(final ChessGame chessGame) {
        CommandArguments commandArguments = InputView.readGameCommand();
        GameCommand gameCommand = commandArguments.parseCommand();
        validateCommandDuringGame(gameCommand);
        executeCommand(gameCommand, commandArguments, chessGame);

        return commandArguments;
    }

    private void executeCommand(final GameCommand gameCommand,
                                final CommandArguments commandArguments,
                                final ChessGame chessGame) {
        if (gameCommand == GameCommand.MOVE) {
            executeMoveCommand(commandArguments, chessGame);
        }
        if (gameCommand == GameCommand.STATUS) {
            executeStatusCommand(chessGame);
        }
    }

    private void executeMoveCommand(final CommandArguments commandArguments, final ChessGame chessGame) {
        Position sourcePosition = Position.from(commandArguments.getFirstArgument());
        Position targetPosition = Position.from(commandArguments.getSecondArgument());
        chessGame.move(sourcePosition, targetPosition);
    }

    private void executeStatusCommand(final ChessGame chessGame) {
        ScoreCalculator scoreCalculator = new ScoreCalculator();
        double blackScore = chessGame.calculateScoreByColor(scoreCalculator, Color.BLACK);
        double whiteScore = chessGame.calculateScoreByColor(scoreCalculator, Color.WHITE);
        OutputView.printScoreStatus(blackScore, whiteScore);
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
