package chess.domain;

import chess.domain.piece.Color;
import chess.domain.piece.Direction;
import chess.domain.piece.Empty;
import chess.domain.piece.Piece;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ChessBoard {

    private final Map<Position, Piece> chessBoard;
    private boolean isKingDead;

    public ChessBoard(final Map<Position, Piece> chessBoard) {
        this.chessBoard = chessBoard;
        this.isKingDead = false;
    }

    public void move(final Position source, final Position target) {
        Direction direction = source.calculateDirection(target);

        validateCommonErrors(source, target);
        validatePiecePath(source, target, direction);
        validatePawnDiagonalMove(source, target, direction);

        checkKingDead(target);
        movePiece(source, target);
    }

    private void movePiece(final Position source, final Position target) {
        chessBoard.put(target, chessBoard.get(source));
        chessBoard.put(source, Empty.EMPTY);
    }

    private void checkKingDead(final Position targetPosition) {
        Piece targetPiece = chessBoard.get(targetPosition);
        if (targetPiece.isKing()) {
            this.isKingDead = true;
        }
    }

    private void validateCommonErrors(final Position source, final Position target) {
        Piece sourcePiece = chessBoard.get(source);
        Piece targetPiece = chessBoard.get(target);

        if (sourcePiece.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 선택한 칸에 기물이 존재하지 않습니다.");
        }
        if (source == target) {
            throw new IllegalArgumentException("[ERROR] 같은 위치로는 이동할 수 없습니다.");
        }
        if (sourcePiece.isAlly(targetPiece)) {
            throw new IllegalArgumentException("[ERROR] 이동하려는 위치에 아군 기물이 존재합니다.");
        }
    }

    private void validatePiecePath(final Position source, final Position target, final Direction direction) {
        Piece sourcePiece = chessBoard.get(source);
        List<Position> path = sourcePiece.findPath(source, target, direction);
        for (final Position position : path) {
            validateObstacleOnPath(position, target);
        }
    }

    private void validateObstacleOnPath(final Position currentPosition, final Position target) {
        if (currentPosition != target && !chessBoard.get(currentPosition).isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 이동 경로에 기물이 존재합니다.");
        }
    }

    private void validatePawnDiagonalMove(final Position source, final Position target, final Direction direction) {
        Piece sourcePiece = chessBoard.get(source);

        if (sourcePiece.isPawn() && chessBoard.get(target).isEmpty() && direction.isDiagonal()) {
            throw new IllegalArgumentException("[ERROR] 폰은 도착 위치에 적이 있는 경우에만 대각선으로 이동할 수 있습니다.");
        }
        if (sourcePiece.isPawn() && !chessBoard.get(target).isEmpty() && !direction.isDiagonal()) {
            throw new IllegalArgumentException("[ERROR] 폰은 적 기물이 경로에 존재하는 경우, 직선 방향으로 이동할 수 없습니다.");
        }
    }

    public Map<Position, Piece> filterPiecesByColor(final Color color) {
        return chessBoard.entrySet()
                .stream()
                .filter(entry -> entry.getValue().isSameColorWith(color))
                .collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));
    }

    public List<Piece> findAllPieces() {
        return chessBoard.values()
                .stream()
                .toList();
    }

    public Piece findPieceByPosition(final Position position) {
        return chessBoard.get(position);
    }

    public boolean isKingDead() {
        return isKingDead;
    }

    public Map<Position, Piece> getChessBoard() {
        return Collections.unmodifiableMap(chessBoard);
    }
}
