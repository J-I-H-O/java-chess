package chess.domain;

import chess.domain.piece.Color;

public class ChessGame {

    private final ChessBoard chessBoard;
    private Color turnColor;

    public ChessGame(final ChessBoard chessBoard, final Color turnColor) {
        this.chessBoard = chessBoard;
        this.turnColor = turnColor;
    }

    public void move(final Position source, final Position target) {
        validateTurn(source, this.turnColor);
        chessBoard.move(source, target);

        if (chessBoard.isKingDead()) {
            return;
        }
        this.turnColor = Color.switchColor(this.turnColor);
    }

    private void validateTurn(final Position source, final Color turnColor) {
        if (!chessBoard.findPieceByPosition(source).isSameColorWith(turnColor)) {
            throw new IllegalArgumentException(String.format("[ERROR] 지금은 %s의 턴입니다.", turnColor));
        }
    }

    public double calculateScoreByColor(final ScoreCalculator scoreCalculator, final Color color) {
        return scoreCalculator.calculateScore(chessBoard.filterPiecesByColor(color));
    }

    public boolean isGameOver() {
        return chessBoard.isKingDead();
    }

    public Color getCurrentTurnColor() {
        return this.turnColor;
    }
}
