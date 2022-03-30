package chess.chessgame;

public class ChessboardTest {
    /*

    private Chessboard chessboard;

    @BeforeEach
    void setUp() {
        chessboard = new Chessboard(new InitializedChessboardGenerator());
    }

    @Test
    @DisplayName("체스판 row 사이즈 확인")
    void checkSizeOfChessboard() {
        assertThat(chessboard.getBoard().size()).isEqualTo(8);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7})
    @DisplayName("체스판 column 사이즈 확인")
    void checkSizeOfChessboard(int col) {

        assertThat(chessboard.getBoard().get(col).size()).isEqualTo(8);
    }

    @ParameterizedTest
    @MethodSource("pieces")
    @DisplayName("초기 기물 위치 확인")
    void checkPiece(int x, int y, Type type, Color color) {
        Piece piece = chessboard.getBoard().get(x).get(y);

        assertThat(piece.isSameType(type)).isTrue();
        assertThat(piece.isSameColor(color)).isTrue();
    }

    @Test
    @DisplayName("이동하려는 위치에 기물이 없는 경우 예외 발생")
    void checkBlankTarget() {
        assertThatThrownBy(() -> chessboard.move(new MovingPosition("c4", "c5"), new Turn()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이동하려는 위치에 기물이 없습니다.");
    }

    @Test
    @DisplayName("상대편의 기물을 움직이려는 경우 예외 발생")
    void checkWrongTurn() {
        Turn turn = new Turn();
        assertThatThrownBy(() -> chessboard.move(new MovingPosition("a7", "a6"), turn))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("상대편의 기물은 움직일 수 없습니다.");
    }

    @Test
    @DisplayName("같은편의 기물을 잡으려는 경우 예외 발생")
    void checkColor() {
        Turn turn = new Turn();
        assertThatThrownBy(() -> chessboard.move(new MovingPosition("a1", "a2"), turn))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("상대편의 기물으로만 이동할 수 있습니다.");
    }

    @Test
    @DisplayName("주어진 좌표 후보들중에 기물이 있는 좌표가 있다면 예외 발생")
    void checkCandidatesOfPossibleCoordinates() {
        Turn turn = new Turn();
        assertThatThrownBy(() -> chessboard.move(new MovingPosition("a1", "a3"), turn))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("가로막는 기물이 있습니다.");
    }

    @Test
    @DisplayName("올바르게 점수를 계산하는지 확인")
    void computeScore() {
        chessboard = new Chessboard(() -> List.of(List.of(
                new Queen(Color.BLACK),
                new Rook(Color.BLACK),
                new Bishop(Color.BLACK),
                new Knight(Color.BLACK),
                new Pawn(Color.BLACK)
        )));
        assertThat(chessboard.computeScore(Color.BLACK, 0.5))
                .isEqualTo(20.5);
    }

    @Test
    @DisplayName("같은 세로줄에 같은 폰이 있는 경우 0.5점으로 계산하는지 확인")
    void computeScorePawn() {
        chessboard = new Chessboard(() -> List.of(
                List.of(new Pawn(Color.BLACK), new Pawn(Color.WHITE), new Pawn(Color.WHITE)),
                List.of(new Pawn(Color.BLACK), new Pawn(Color.WHITE), new Pawn(Color.WHITE)),
                List.of(new Pawn(Color.BLACK), new Pawn(Color.WHITE), new Pawn(Color.WHITE))
        ));
        assertThat(chessboard.computeScore(Color.BLACK, 0.5))
                .isEqualTo(1.5);
    }

    private static Stream<Arguments> pieces() {
        return Stream.of(
                Arguments.of(0, 0, Type.ROOK, Color.BLACK),
                Arguments.of(0, 1, Type.KNIGHT, Color.BLACK),
                Arguments.of(0, 2, Type.BISHOP, Color.BLACK),
                Arguments.of(0, 3, Type.QUEEN, Color.BLACK),
                Arguments.of(1, 4, Type.PAWN, Color.BLACK),

                Arguments.of(7, 4, Type.KING, Color.WHITE),
                Arguments.of(7, 5, Type.BISHOP, Color.WHITE),
                Arguments.of(7, 6, Type.KNIGHT, Color.WHITE),
                Arguments.of(7, 7, Type.ROOK, Color.WHITE),
                Arguments.of(6, 5, Type.PAWN, Color.WHITE)
        );
    }

     */

}