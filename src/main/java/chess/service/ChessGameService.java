package chess.service;

import chess.dao.MoveDao;
import chess.domain.ChessBoard;
import chess.domain.ChessBoardFactory;
import chess.domain.ChessGame;
import chess.domain.Move;
import chess.domain.Position;
import chess.domain.ScoreCalculator;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.dto.MoveRequest;
import chess.view.CommandArguments;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChessGameService {

    private final ChessGame chessGame;
    private final MoveDao moveDao;

    // TODO: ChessGameService가 정말로 필요할까?
    public ChessGameService(final MoveDao moveDao) {
        ChessBoard chessBoard = ChessBoardFactory.makeChessBoard();
        this.chessGame = new ChessGame(chessBoard, Color.WHITE);
        this.moveDao = moveDao;
    }

    public void saveMove(final Position source, final Position target) {
        moveDao.save(MoveRequest.of(source, target));
    }

    public void loadMoveHistory() {
        List<Move> moveHistory = moveDao.findAllMoves();
        for (Move move : moveHistory) {
            Position source = move.getSource();
            Position target = move.getTarget();
            chessGame.move(source, target);
        }
    }

    public void resetGame() {
        moveDao.deleteAll();
    }

    public void executeMoveCommand(final CommandArguments commandArguments) {
        Position sourcePosition = Position.from(commandArguments.getFirstArgument());
        Position targetPosition = Position.from(commandArguments.getSecondArgument());
        // TODO: 결국 도메인 로직 따로 DB로직 따로인것 아닌가? Service로 넣어줬을 뿐 개선된게 없는 것 같음
        chessGame.move(sourcePosition, targetPosition);
        saveMove(sourcePosition, targetPosition);
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
