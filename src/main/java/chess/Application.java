package chess;

import chess.controller.ChessController;
import chess.dao.MoveDao;
import chess.service.ChessGameService;

public class Application {

    public static void main(String[] args) {
        MoveDao moveDao = new MoveDao();
        ChessGameService chessGameService = new ChessGameService(moveDao);
        ChessController chessController = new ChessController(chessGameService);
        chessController.run();
    }
}
