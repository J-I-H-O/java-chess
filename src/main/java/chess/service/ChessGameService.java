package chess.service;

import chess.dao.MoveDao;
import chess.domain.ChessGame;
import chess.domain.Move;
import chess.domain.Position;
import chess.dto.MoveRequest;
import java.util.List;

public class ChessGameService {

    private final MoveDao moveDao;

    public ChessGameService(MoveDao moveDao) {
        this.moveDao = moveDao;
    }

    public void saveMove(final Position source, final Position target) {
        moveDao.save(MoveRequest.of(source, target));
    }

    public void loadMoveHistory(final ChessGame chessGame) {
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
}
