package chess.controller;

import chess.domain.ChessBoard;
import chess.domain.ChessBoardFactory;
import chess.domain.Position;
import chess.domain.ScoreCalculator;
import chess.domain.piece.Color;
import chess.view.CommandArguments;
import chess.view.GameCommand;
import chess.view.InputView;
import chess.view.OutputView;
import java.util.function.Supplier;

public class ChessController {

    private final InputView inputView;
    private final OutputView outputView;

    public ChessController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        ChessBoard chessBoard = ChessBoardFactory.makeChessBoard();
        outputView.printCommandInformation();
        CommandArguments commandArguments = repeatUntilSuccess(() -> readCommandBeforeGame());
        GameCommand gameCommand = commandArguments.parseCommand();

        while (gameCommand != GameCommand.END && !chessBoard.isGameOver()) {
            outputView.printChessBoard(chessBoard);
            commandArguments = repeatUntilSuccess(() -> readAndExecuteCommandDuringGame(chessBoard));
            gameCommand = commandArguments.parseCommand();
        }
    }

    private CommandArguments readCommandBeforeGame() {
        CommandArguments commandArguments = inputView.readGameCommand();
        GameCommand gameCommand = commandArguments.parseCommand();
        validateCommandBeforeGame(gameCommand);

        return commandArguments;
    }

    private CommandArguments readAndExecuteCommandDuringGame(final ChessBoard chessBoard) {
        CommandArguments commandArguments = inputView.readGameCommand();
        GameCommand gameCommand = commandArguments.parseCommand();
        validateCommandDuringGame(gameCommand);
        executeCommand(gameCommand, commandArguments, chessBoard);

        return commandArguments;
    }

    private void executeCommand(final GameCommand gameCommand,
                                final CommandArguments commandArguments,
                                final ChessBoard chessBoard) {
        if (gameCommand == GameCommand.MOVE) {
            executeMoveCommand(commandArguments, chessBoard);
        }
        if (gameCommand == GameCommand.STATUS) {
            executeStatusCommand(chessBoard);
        }
    }

    private void executeMoveCommand(final CommandArguments commandArguments, final ChessBoard chessBoard) {
        Position sourcePosition = Position.from(commandArguments.getFirstArgument());
        Position targetPosition = Position.from(commandArguments.getSecondArgument());
        chessBoard.move(sourcePosition, targetPosition);
    }

    private void executeStatusCommand(final ChessBoard chessBoard) {
        ScoreCalculator scoreCalculator = new ScoreCalculator();
        double blackScore = scoreCalculator.calculateScore(chessBoard.filterPiecesByColor(Color.BLACK));
        double whiteScore = scoreCalculator.calculateScore(chessBoard.filterPiecesByColor(Color.WHITE));
        outputView.printScoreStatus(blackScore, whiteScore);
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
            outputView.printErrorMessage(e.getMessage());
            return repeatUntilSuccess(reader);
        }
    }
}
