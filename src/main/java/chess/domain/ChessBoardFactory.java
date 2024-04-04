package chess.domain;

import chess.domain.piece.Bishop;
import chess.domain.piece.Color;
import chess.domain.piece.Empty;
import chess.domain.piece.King;
import chess.domain.piece.Knight;
import chess.domain.piece.Pawn;
import chess.domain.piece.Piece;
import chess.domain.piece.Queen;
import chess.domain.piece.Rook;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ChessBoardFactory {

    public static ChessBoard makeDefaultChessBoard() {
        Map<Position, Piece> chessBoard = new LinkedHashMap<>();

        makeEmptyChessBoard(chessBoard);
        makeInitialChessBoard(chessBoard);

        return new ChessBoard(chessBoard);
    }

    public static ChessBoard makeLoadedChessBoard(final Map<Position, Piece> savedPieces) {
        Map<Position, Piece> chessBoard = new LinkedHashMap<>();

        makeEmptyChessBoard(chessBoard);
        loadSavedChessBoard(chessBoard, savedPieces);

        return new ChessBoard(chessBoard);
    }

    // 빈 값으로 모두 초기화
    private static void makeEmptyChessBoard(final Map<Position, Piece> chessBoard) {
        for (int rank = 8; rank >= 1; rank--) {
            for (char file = 'a'; file <= 'h'; file++) {
                chessBoard.put(Position.of(file, rank), Empty.EMPTY);
            }
        }
    }

    // 초기 체스판 기물 배치
    private static void makeInitialChessBoard(final Map<Position, Piece> chessBoard) {
        chessBoard.put(Position.of('a', 8), Rook.colorOf(Color.BLACK));
        chessBoard.put(Position.of('b', 8), Knight.colorOf(Color.BLACK));
        chessBoard.put(Position.of('c', 8), Bishop.colorOf(Color.BLACK));
        chessBoard.put(Position.of('d', 8), Queen.colorOf(Color.BLACK));
        chessBoard.put(Position.of('e', 8), King.colorOf(Color.BLACK));
        chessBoard.put(Position.of('f', 8), Bishop.colorOf(Color.BLACK));
        chessBoard.put(Position.of('g', 8), Knight.colorOf(Color.BLACK));
        chessBoard.put(Position.of('h', 8), Rook.colorOf(Color.BLACK));
        for (char file = 'a'; file <= 'h'; file++) {
            chessBoard.put(Position.of(file, 7), Pawn.colorOf(Color.BLACK));
        }
        for (char file = 'a'; file <= 'h'; file++) {
            chessBoard.put(Position.of(file, 2), Pawn.colorOf(Color.WHITE));
        }
        chessBoard.put(Position.of('a', 1), Rook.colorOf(Color.WHITE));
        chessBoard.put(Position.of('b', 1), Knight.colorOf(Color.WHITE));
        chessBoard.put(Position.of('c', 1), Bishop.colorOf(Color.WHITE));
        chessBoard.put(Position.of('d', 1), Queen.colorOf(Color.WHITE));
        chessBoard.put(Position.of('e', 1), King.colorOf(Color.WHITE));
        chessBoard.put(Position.of('f', 1), Bishop.colorOf(Color.WHITE));
        chessBoard.put(Position.of('g', 1), Knight.colorOf(Color.WHITE));
        chessBoard.put(Position.of('h', 1), Rook.colorOf(Color.WHITE));
    }

    // DB에 저장된 기물 상태로 배치
    private static void loadSavedChessBoard(final Map<Position, Piece> chessBoard,
                                            final Map<Position, Piece> savedPieces) {
        for (Entry<Position, Piece> positionPieceEntry : savedPieces.entrySet()) {
            Position position = positionPieceEntry.getKey();
            Piece piece = positionPieceEntry.getValue();

            chessBoard.put(position, piece);
        }
    }
}
