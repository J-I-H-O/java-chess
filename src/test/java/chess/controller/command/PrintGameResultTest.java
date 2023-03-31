package chess.controller.command;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import chess.controller.command.*;
import chess.domain.board.Board;
import chess.domain.board.GameResultBySide;
import chess.domain.board.ResultCalculator;
import chess.domain.board.ScoreBySide;
import chess.domain.piece.Pieces;
import chess.domain.position.File;
import chess.domain.position.Position;
import chess.domain.position.Rank;
import chess.domain.ChessGame;
import chess.domain.service.ChessGameService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PrintGameResultTest {

    private final ChessGameService chessGameService = new ChessGameService(new ChessGame(1L, new Board(new Pieces()), Turn.WHITE));

    @Test
    @DisplayName("게임 결과 출력 상태에서 시작 시 새로운 플레이 상태로 전이된다.")
    void start() {
        // given
        PrintGameResult printGameResult = new PrintGameResult(new ResultCalculator(new ScoreBySide(), new GameResultBySide()), chessGameService);

        // when
        CommandStatus newPlay = printGameResult.start();

        // then
        assertThat(newPlay).isInstanceOf(Play.class);
        assertThat(newPlay).isNotEqualTo(printGameResult);
    }

    @Test
    @DisplayName("게임 결과 출력 상태에서 이동 시 말이 이동한 상태 & 턴이 넘어간 플레이 상태로 전이된다.")
    void move() {
        // given
        PrintGameResult printGameResult = new PrintGameResult(new ResultCalculator(new ScoreBySide(), new GameResultBySide()), chessGameService);
        Position sourcePosition = new Position(File.A, Rank.TWO);
        Position targetPosition = new Position(File.A, Rank.FOUR);

        // when
        CommandStatus newPlay = printGameResult.move(sourcePosition, targetPosition);

        // then
        assertThat(newPlay).isInstanceOf(Play.class);
        assertThat(newPlay).isNotEqualTo(printGameResult);
        assertThat(newPlay.getTurnDisplayName()).isEqualTo("black");
    }

    @Test
    @DisplayName("게임 결과 출력 상태에서 종료 시 종료 상태로 전이된다.")
    void end() {
        // given
        PrintGameResult printGameResult = new PrintGameResult(new ResultCalculator(new ScoreBySide(), new GameResultBySide()), chessGameService);

        // when, then
        assertThat(printGameResult.end()).isInstanceOf(End.class);
    }

    @Test
    @DisplayName("게임 결과 출력 상태에서 기물들을 가져올 시 예외를 던진다.")
    void getPieces() {
        // given
        PrintGameResult printGameResult = new PrintGameResult(new ResultCalculator(new ScoreBySide(), new GameResultBySide()), chessGameService);

        // when, then
        assertThatThrownBy(() -> printGameResult.getPieces())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("[ERROR] 게임 결과 출력 상태에서는 기물들을 반환할 수 없습니다.");
    }

    @Test
    @DisplayName("게임 결과 출력 상태에서 턴 이름을 가져올 시 예외를 던진다.")
    void getTurnDisplayName() {
        // given
        PrintGameResult printGameResult = new PrintGameResult(new ResultCalculator(new ScoreBySide(), new GameResultBySide()), chessGameService);

        // when, then
        assertThatThrownBy(() -> printGameResult.getTurnDisplayName())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("[ERROR] 게임 결과 출력 상태에서는 턴 이름을 반환할 수 없습니다.");
    }

    @Test
    @DisplayName("게임 결과 출력 상태에서 진영별 점수를 가져올 수 있다.")
    void getScoreBySide() {
        // given
        PrintGameResult printGameResult = new PrintGameResult(new ResultCalculator(new ScoreBySide(), new GameResultBySide()), chessGameService);

        // when, then
        Assertions.assertDoesNotThrow(() -> printGameResult.getScoreBySide());
    }

    @Test
    @DisplayName("게임 결과 출력 상태에서 진영별 결과를 가져올 수 있다.")
    void getGameResultBySide() {
        // given
        PrintGameResult printGameResult = new PrintGameResult(new ResultCalculator(new ScoreBySide(), new GameResultBySide()), chessGameService);

        // when, then
        Assertions.assertDoesNotThrow(() -> printGameResult.getGameResultBySide());
    }

    @Test
    @DisplayName("게임 결과 출력 상태에서 이전 게임으로 재시작할 시 예외를 던진다.")
    void restart() {
        // given
        PrintGameResult printGameResult = new PrintGameResult(new ResultCalculator(new ScoreBySide(), new GameResultBySide()), chessGameService);
        Long gameId = 1L;

        // when, then
        assertThatThrownBy(() -> printGameResult.restart(gameId))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("[ERROR] 게임 결과 출력 상태에서는 이전 게임으로 재시작할 수 없습니다.");
    }
}