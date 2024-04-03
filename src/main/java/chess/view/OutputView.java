package chess.view;

import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import java.util.List;
import java.util.Map;

public class OutputView {

    public static final String LINE_SEPARATOR = System.lineSeparator();

    public static void printCommandInformation() {
        System.out.println("> 체스 게임을 시작합니다.\n"
                + "> 게임 시작 : start\n"
                + "> 게임 종료 : end\n"
                + "> 게임 이동 : move source위치 target위치 - 예. move b2 b3\n"
                + "> 점수 출력 : status");
    }

    public static void printChessBoard(final List<Piece> pieces) {
        String chessBoardExpression = ChessBoardExpression.toExpression(pieces);
        System.out.println(chessBoardExpression);
    }

    public static void printScoreStatus(final Map<Color, Double> scoreByColor) {
        double blackScore = scoreByColor.get(Color.BLACK);
        double whiteScore = scoreByColor.get(Color.WHITE);

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

    public static void printWinner(final Color winnerColor) {
        System.out.println(String.format("게임 종료: 승자는 %s입니다.", winnerColor));
    }

    public static void printErrorMessage(final String message) {
        System.out.println(LINE_SEPARATOR + message + LINE_SEPARATOR);
    }
}
