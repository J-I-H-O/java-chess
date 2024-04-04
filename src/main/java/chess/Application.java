package chess;

import chess.controller.ChessController;
import chess.dao.ChessGameDao;
import chess.dao.PieceDao;
import chess.service.ChessGameService;

public class Application {

    public static void main(String[] args) {
        ChessGameDao chessGameDao = new ChessGameDao();
        PieceDao pieceDao = new PieceDao();
        ChessGameService chessGameService = new ChessGameService(chessGameDao, pieceDao);
        ChessController chessController = new ChessController(chessGameService);
        chessController.run();
    }
}
