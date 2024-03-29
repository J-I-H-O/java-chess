package chess.view;

import chess.domain.ChessBoard;
import chess.domain.piece.Piece;
import java.util.List;

public class OutputView {

    public static final String LINE_SEPARATOR = System.lineSeparator();

    // TODO: 개선
    public void printCommandInformation() {
        System.out.println("> 체스 게임을 시작합니다.\n"
                + "> 게임 시작 : start\n"
                + "> 게임 종료 : end\n"
                + "> 게임 이동 : move source위치 target위치 - 예. move b2 b3\n"
                + "> 점수 출력 : status");
    }

    public void printChessBoard(final ChessBoard chessBoard) {
        List<Piece> pieces = chessBoard.findAllPieces();
        String chessBoardExpression = ChessBoardExpression.toExpression(pieces);
        System.out.println(chessBoardExpression);
    }

    public void printScoreStatus(final double blackScore, final double whiteScore) {
        if (blackScore < whiteScore) {
            System.out.println(String.format("BLACK: %.2f점, WHITE: %.2f점으로 WHITE가 우세합니다.", blackScore, whiteScore));
        }
        if (blackScore > whiteScore) {
            System.out.println(String.format("BLACK: %.2f점, WHITE: %.2f점으로 BLACK이 우세합니다.", blackScore, whiteScore));
        }
        if (blackScore == whiteScore) {
            System.out.println(String.format("BLACK: %.2f점, WHITE: %.2f점으로 동점입니다.", blackScore, whiteScore));
        }
    }

    public void printErrorMessage(final String message) {
        System.out.println(LINE_SEPARATOR + message + LINE_SEPARATOR);
    }
}
