package chess;

import chess.controller.ChessController;
import chess.dao.PieceDao;
import chess.service.ChessGameService;

public class Application {

    public static void main(String[] args) {
        PieceDao pieceDao = new PieceDao();
        ChessGameService chessGameService = new ChessGameService(pieceDao);
        ChessController chessController = new ChessController(chessGameService);
        chessController.run();
    }
}
