package chess.controller;

import chess.domain.ChessBoard;
import chess.domain.ChessBoardFactory;
import chess.domain.Position;
import chess.domain.piece.Color;
import chess.view.GameCommand;
import chess.view.InputView;
import chess.view.OutputView;
import java.util.List;
import java.util.function.Supplier;

public class ChessController {

    private static final int COMMAND_INDEX = 0;
    private static final int SOURCE_POSITION_INDEX = 1;
    private static final int TARGET_POSITION_INDEX = 2;

    private final InputView inputView;
    private final OutputView outputView;

    public ChessController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        final ChessBoard chessBoard = ChessBoardFactory.makeChessBoard();
        outputView.printCommandInformation();

        List<String> commandArguments = repeat(this::readInitialCommand);
        String gameCommand = commandArguments.get(COMMAND_INDEX);
        outputView.printChessBoard(chessBoard);

        repeat(() -> playTurn(chessBoard, gameCommand, commandArguments));
    }

    private void playTurn(ChessBoard chessBoard, String gameCommand, List<String> commandArguments) {
        // TODO: 현재 King이 죽으면 바로 게임이 끝나지 않고 추가 명령어를 입력해야 하는 상황. 수정 필요.
        while (!GameCommand.isEndCommand(gameCommand) && !chessBoard.isKingDead()) {
            if (GameCommand.isMoveCommand(gameCommand)) {
                playMoveCommand(chessBoard, gameCommand, commandArguments);
                outputView.printChessBoard(chessBoard);
            }
            if (GameCommand.isStatusCommand(gameCommand)) {
                double blackScore = chessBoard.calculateScoreByColor('a', 'h', 1, 8, Color.BLACK);
                double whiteScore = chessBoard.calculateScoreByColor('a', 'h', 1, 8, Color.WHITE);
                outputView.printScoreStatus(blackScore, whiteScore);
            }

            commandArguments = readInGameCommand();
            gameCommand = commandArguments.get(COMMAND_INDEX);
        }
    }

    private void playMoveCommand(final ChessBoard chessBoard,
                                 final String gameCommand,
                                 final List<String> commandArguments) {
        if (GameCommand.isMoveCommand(gameCommand)) {
            Position source = Position.from(commandArguments.get(SOURCE_POSITION_INDEX));
            Position target = Position.from(commandArguments.get(TARGET_POSITION_INDEX));
            chessBoard.move(source, target);
            outputView.printChessBoard(chessBoard);
        }
    }

    private List<String> readInitialCommand() {
        List<String> commandArguments = inputView.readGameCommand();
        String gameCommand = commandArguments.get(COMMAND_INDEX);

        if (GameCommand.isMoveCommand(gameCommand) || GameCommand.isStatusCommand(gameCommand)) {
            throw new IllegalArgumentException("[ERROR] 먼저 게임을 시작해야 합니다.");
        }

        return commandArguments;
    }

    private List<String> readInGameCommand() {
        List<String> commandArguments = inputView.readGameCommand();
        String gameCommand = commandArguments.get(COMMAND_INDEX);

        if (GameCommand.isStartCommand(gameCommand)) {
            throw new IllegalArgumentException("[ERROR] 이미 게임이 시작된 상태입니다.");
        }

        return commandArguments;
    }

    private <T> T repeat(final Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
            return repeat(supplier);
        }
    }

    private void repeat(final Runnable runnable) {
        try {
            runnable.run();
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
            repeat(runnable);
        }
    }
}
