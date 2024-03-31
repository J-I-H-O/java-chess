package chess.domain;

import chess.domain.piece.Color;
import chess.domain.piece.Direction;
import chess.domain.piece.Empty;
import chess.domain.piece.Piece;
import java.util.List;
import java.util.Map;

public class ChessBoard {

    private final Map<Position, Piece> chessBoard;
    private boolean isGameOver;
    private Color turn;

    public ChessBoard(final Map<Position, Piece> chessBoard) {
        this.chessBoard = chessBoard;
        this.isGameOver = false;
        this.turn = Color.WHITE;
    }

    public void move(final Position source, final Position target) {
        Direction direction = source.calculateDirection(target);

        validateCommonErrors(source, target);
        validatePiecePath(source, target, direction);
        validatePawnDiagonalMove(source, target, direction);

        checkGameOver(target);
        movePiece(source, target);
        this.turn = Color.switchColor(this.turn);
    }

    private void movePiece(final Position source, final Position target) {
        chessBoard.put(target, chessBoard.get(source));
        chessBoard.put(source, Empty.of());
    }

    private void checkGameOver(final Position targetPosition) {
        Piece targetPiece = chessBoard.get(targetPosition);
        if (targetPiece.isKing()) {
            this.isGameOver = true;
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
        if (this.turn == Color.WHITE && sourcePiece.isSameColorWith(Color.BLACK)) {
            throw new IllegalArgumentException("[ERROR] 지금은 WHITE의 턴 입니다.");
        }
        if (this.turn == Color.BLACK && sourcePiece.isSameColorWith(Color.WHITE)) {
            throw new IllegalArgumentException("[ERROR] 지금은 BLACK의 턴 입니다.");
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

    // TODO: 계산 알고리즘 고민해보기
    //       굳이 인자로 모두 받아야 할까?
    public double calculateScoreByColor(final char startFile, final char endFile,
                                        final int startRank, final int endRank, final Color color) {
        double score = 0;

        for (char currentFile = startFile; currentFile <= endFile; currentFile++) {
            int pawnCount = 0;
            for (int currentRank = startRank; currentRank <= endRank; currentRank++) {
                Position currentPosition = Position.of(currentFile, currentRank);
                Piece currentPiece = chessBoard.get(currentPosition);
                if (currentPiece.isSameColorWith(color)) {
                    if (currentPiece.isPawn()) {
                        pawnCount++;
                    }
                    score += currentPiece.getPieceScore();
                }
            }
            if (pawnCount == 1) {
                score += 0.5;
            }
        }
        return score;
    }

    public List<Piece> findAllPieces() {
        return chessBoard.values()
                .stream()
                .toList();
    }

    public Piece findPieceByPosition(final Position position) {
        return chessBoard.get(position);
    }

    public boolean isGameOver() {
        return isGameOver;
    }
}
