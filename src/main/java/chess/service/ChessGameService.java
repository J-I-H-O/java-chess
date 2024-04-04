package chess.service;

import chess.dao.PieceDao;
import chess.domain.ChessBoard;
import chess.domain.ChessBoardFactory;
import chess.domain.ChessGame;
import chess.domain.Position;
import chess.domain.ScoreCalculator;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.dto.PieceRequest;
import chess.view.CommandArguments;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChessGameService {

    private final ChessGame chessGame;
    private final PieceDao pieceDao;

    public ChessGameService(final PieceDao pieceDao) {
        ChessBoard chessBoard = buildChessBoard(pieceDao.findAllPieces());
        this.chessGame = new ChessGame(chessBoard, Color.WHITE);
        this.pieceDao = pieceDao;
    }

    public ChessBoard buildChessBoard(final Map<Position, Piece> loadedPieces) {
        if (loadedPieces.isEmpty()) {
            return ChessBoardFactory.makeDefaultChessBoard();
        }
        return ChessBoardFactory.makeLoadedChessBoard(loadedPieces);
    }

    public void saveCurrentGame() {
        List<PieceRequest> pieceRequests = chessGame.getChessBoard()
                .entrySet()
                .stream()
                .filter(entry -> !entry.getValue().isEmpty())
                .map(entry -> PieceRequest.from(entry.getKey(), entry.getValue()))
                .toList();
        pieceDao.saveAll(pieceRequests);
    }

    public void resetGame() {
        pieceDao.deleteAll();
    }

    public void executeMoveCommand(final CommandArguments commandArguments) {
        Position sourcePosition = Position.from(commandArguments.getFirstArgument());
        Position targetPosition = Position.from(commandArguments.getSecondArgument());
        chessGame.move(sourcePosition, targetPosition);
    }

    public Map<Color, Double> executeStatusCommand() {
        Map<Color, Double> scoreByColor = new HashMap<>();
        ScoreCalculator scoreCalculator = new ScoreCalculator();
        scoreByColor.put(Color.BLACK, chessGame.calculateScoreByColor(scoreCalculator, Color.BLACK));
        scoreByColor.put(Color.WHITE, chessGame.calculateScoreByColor(scoreCalculator, Color.WHITE));

        return scoreByColor;
    }

    public boolean isGameOver() {
        return chessGame.isGameOver();
    }

    public List<Piece> findAllPieces() {
        return chessGame.findAllPieces();
    }

    public Color getCurrentTurnColor() {
        return chessGame.getCurrentTurnColor();
    }
}
