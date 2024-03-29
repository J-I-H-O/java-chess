package chess.domain;

import chess.domain.piece.Color;
import chess.domain.piece.Direction;
import chess.domain.piece.Empty;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import java.util.List;
import java.util.Map;

public class ChessBoard {

    private final Map<Position, Piece> chessBoard;
    private Color turn;
    private boolean isKingDead;

    public ChessBoard(final Map<Position, Piece> chessBoard) {
        this.chessBoard = chessBoard;
        this.turn = Color.WHITE;
        this.isKingDead = false;
    }

    public void move(final Position source, final Position target) {
        Piece piece = findPieceByPosition(source);
        Direction direction = source.calculateDirection(target);

        validateCommonErrors(source, target, piece);
        List<Position> path = piece.findPath(source, target);
        for (final Position position : path) {
            validateObstacleOnPath(target, position);
            validatePawnDiagonalMove(position, piece, direction);
        }
        movePiece(source, target, piece);
    }

    private void validateCommonErrors(final Position source, final Position target, final Piece piece) {
        if (piece.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 선택한 칸에 기물이 존재하지 않습니다.");
        }
        if (source == target) {
            throw new IllegalArgumentException("[ERROR] 같은 위치로는 이동할 수 없습니다.");
        }
        if (piece.isAlly(findPieceByPosition(target))) {
            throw new IllegalArgumentException("[ERROR] 이동하려는 위치에 아군 기물이 존재합니다.");
        }
        if (this.turn == Color.WHITE && piece.isBlack()) {
            throw new IllegalArgumentException("[ERROR] 지금은 WHITE의 턴 입니다.");
        }
        if (this.turn == Color.BLACK && !piece.isBlack()) {
            throw new IllegalArgumentException("[ERROR] 지금은 BLACK의 턴 입니다.");
        }
    }

    private void validateObstacleOnPath(final Position target, final Position currentPosition) {
        if (currentPosition != target && !chessBoard.get(currentPosition).isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 이동 경로에 기물이 존재합니다.");
        }
    }

    private void validatePawnDiagonalMove(Position position, Piece piece, Direction direction) {
        if (piece.isPawn() && findPieceByPosition(position).isEmpty() && direction.isDiagonal()) {
            throw new IllegalArgumentException("[ERROR] 폰은 도착 위치에 적이 있는 경우에만 대각선으로 이동할 수 있습니다.");
        }
        if (piece.isPawn() && !findPieceByPosition(position).isEmpty() && !direction.isDiagonal()) {
            throw new IllegalArgumentException("[ERROR] 폰은 적 기물이 경로에 존재하는 경우, 직선 방향으로 이동할 수 없습니다.");
        }
    }

    private void movePiece(final Position source, final Position target, final Piece piece) {
        // King이 죽으면 게임 종료
        if (PieceType.KING.name().equals(chessBoard.get(target).getOwnPieceTypeName())) {
            isKingDead = true;
        }
        // 턴 넘기기
        this.turn = Color.switchColor(this.turn);

        chessBoard.put(target, piece);
        chessBoard.put(source, Empty.of());
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
}
