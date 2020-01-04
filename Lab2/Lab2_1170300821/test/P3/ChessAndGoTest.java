package P3;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ChessAndGoTest {

	@Test
	public void testGame() {
		Game testgame1 = new Game("chess");
		testgame1.initGameWithPlayerName("Aplayer", "Bplayer");
		// 测试初始化
		assertEquals("车", testgame1.getBoard().getPieceAtCord(1, 1).getpName());
		assertEquals("车", testgame1.getBoard().getPieceAtCord(8, 8).getpName());
		assertEquals("马", testgame1.getBoard().getPieceAtCord(1, 2).getpName());
		assertEquals("象", testgame1.getBoard().getPieceAtCord(1, 3).getpName());
		assertEquals("王", testgame1.getBoard().getPieceAtCord(1, 4).getpName());
		assertEquals("后", testgame1.getBoard().getPieceAtCord(1, 5).getpName());
		assertEquals("象", testgame1.getBoard().getPieceAtCord(1, 6).getpName());
		assertEquals("马", testgame1.getBoard().getPieceAtCord(1, 7).getpName());
		assertEquals("车", testgame1.getBoard().getPieceAtCord(1, 8).getpName());
		assertEquals("兵", testgame1.getBoard().getPieceAtCord(2, 1).getpName());
		assertEquals("兵", testgame1.getBoard().getPieceAtCord(7, 1).getpName());
		assertEquals("兵", testgame1.getBoard().getPieceAtCord(2, 8).getpName());

		// 测试move
		testgame1.movePiece(testgame1.getPlayerA(), 1, 1, 3, 3);
		assertEquals("车", testgame1.getBoard().getPieceAtCord(3, 3).getpName());
		assertEquals(null, testgame1.getBoard().getPieceAtCord(1, 1));
		testgame1.movePiece(testgame1.getPlayerA(), 7, 2, 5, 5);
		assertEquals("兵", testgame1.getBoard().getPieceAtCord(5, 5).getpName());
		assertEquals(null, testgame1.getBoard().getPieceAtCord(7, 2));
		testgame1.movePiece(testgame1.getPlayerA(), 8, 4, 4, 5);
		assertEquals("王", testgame1.getBoard().getPieceAtCord(4, 5).getpName());
		assertEquals(null, testgame1.getBoard().getPieceAtCord(8, 4));
		testgame1.movePiece(testgame1.getPlayerA(), 8, 5, 5, 6);
		assertEquals("后", testgame1.getBoard().getPieceAtCord(5, 6).getpName());
		assertEquals(null, testgame1.getBoard().getPieceAtCord(8, 5));
		testgame1.movePiece(testgame1.getPlayerA(), 5, 6, 8, 5);
		assertEquals("后", testgame1.getBoard().getPieceAtCord(8, 5).getpName());
		assertEquals(null, testgame1.getBoard().getPieceAtCord(5, 6));
		testgame1.movePiece(testgame1.getPlayerA(), 4, 5, 8, 4);
		assertEquals("王", testgame1.getBoard().getPieceAtCord(8, 4).getpName());
		assertEquals(null, testgame1.getBoard().getPieceAtCord(4, 5));

		// 测试remove
		testgame1.removePiece(testgame1.getPlayerA(), 1, 2);
		assertEquals(null, testgame1.getBoard().getPieceAtCord(1, 2));
		assertEquals(15, testgame1.getNumOfPlayerPiecesInBoard(testgame1.getPlayerA()));
		testgame1.removePiece(testgame1.getPlayerA(), 1, 3);
		assertEquals(null, testgame1.getBoard().getPieceAtCord(1, 3));
		assertEquals(14, testgame1.getNumOfPlayerPiecesInBoard(testgame1.getPlayerA()));
		testgame1.removePiece(testgame1.getPlayerA(), 1, 4);
		assertEquals(null, testgame1.getBoard().getPieceAtCord(1, 4));
		assertEquals(13, testgame1.getNumOfPlayerPiecesInBoard(testgame1.getPlayerA()));
		testgame1.removePiece(testgame1.getPlayerA(), 1, 5);
		assertEquals(null, testgame1.getBoard().getPieceAtCord(1, 5));
		assertEquals(12, testgame1.getNumOfPlayerPiecesInBoard(testgame1.getPlayerA()));
		testgame1.removePiece(testgame1.getPlayerA(), 1, 6);
		assertEquals(null, testgame1.getBoard().getPieceAtCord(1, 6));
		assertEquals(11, testgame1.getNumOfPlayerPiecesInBoard(testgame1.getPlayerA()));

		// 测试eat
		testgame1.eatPiece(testgame1.getPlayerA(), 2, 1, 7, 7);
		assertEquals(null, testgame1.getBoard().getPieceAtCord(2, 1));
		assertEquals("兵", testgame1.getBoard().getPieceAtCord(7, 7).getpName());
		assertEquals(15, testgame1.getNumOfPlayerPiecesInBoard(testgame1.getPlayerB()));

		// 测试getnumber
		assertEquals(11, testgame1.getNumOfPlayerPiecesInBoard(testgame1.getPlayerA()));
		assertEquals(15, testgame1.getNumOfPlayerPiecesInBoard(testgame1.getPlayerB()));

		// 测试put
		Game testgame2 = new Game("go");
		testgame2.initGameWithPlayerName("Aplayer", "Bplayer");
		assertEquals(0, testgame2.getNumOfPlayerPiecesInBoard(testgame2.getPlayerA()));
		testgame2.putPiece(testgame2.getPlayerA(), testgame2.getPlayerA().getAnyPieceByFilter("黑子", 0), 1, 1);
		assertEquals("黑子", testgame2.getBoard().getPieceAtCord(1, 1).getpName());
		assertEquals(1, testgame2.getNumOfPlayerPiecesInBoard(testgame2.getPlayerA()));
		testgame2.putPiece(testgame2.getPlayerA(), testgame2.getPlayerA().getAnyPieceByFilter("黑子", 0), 2, 1);
		assertEquals("黑子", testgame2.getBoard().getPieceAtCord(2, 1).getpName());
		assertEquals(2, testgame2.getNumOfPlayerPiecesInBoard(testgame2.getPlayerA()));
		testgame2.putPiece(testgame2.getPlayerB(), testgame2.getPlayerB().getAnyPieceByFilter("白子", 0), 3, 1);
		assertEquals("白子", testgame2.getBoard().getPieceAtCord(3, 1).getpName());
		assertEquals(1, testgame2.getNumOfPlayerPiecesInBoard(testgame2.getPlayerB()));
	}

	@Test
	public void testBoard() {
		Game testgame1 = new Game("chess");
		testgame1.initGameWithPlayerName("Aplayer", "Bplayer");
		Board testboard = testgame1.getBoard();
		//测试get
		assertEquals("马", testboard.getPieceAtCord(1, 2).getpName());
		assertEquals("象", testboard.getPieceAtCord(1, 3).getpName());
		assertEquals("王", testboard.getPieceAtCord(1, 4).getpName());
		assertEquals("后", testboard.getPieceAtCord(1, 5).getpName());
		assertEquals("象", testboard.getPieceAtCord(1, 6).getpName());
		assertEquals("马", testboard.getPieceAtCord(1, 7).getpName());
		assertEquals("车", testboard.getPieceAtCord(1, 8).getpName());
		assertEquals("兵", testboard.getPieceAtCord(2, 1).getpName());
		assertEquals("兵", testboard.getPieceAtCord(7, 1).getpName());
		assertEquals("兵", testboard.getPieceAtCord(2, 8).getpName());
		
		//测试set
		Piece tPiece1 = new Piece("test1", 1, 5, 5); 
		Piece tPiece2 = new Piece("test2", 1, 5, 6); 
		Piece tPiece3 = new Piece("test3", 1, 5, 7); 
		Piece tPiece4 = new Piece("test4", 1, 5, 8); 
		Piece tPiece5 = new Piece("test5", 1, 5, 4);
		testboard.setPieceAtCord(5, 5, tPiece1);
		assertEquals("test1", testboard.getPieceAtCord(5, 5).getpName());
		testboard.setPieceAtCord(5, 6, tPiece2);
		assertEquals("test2", testboard.getPieceAtCord(5, 6).getpName());
		testboard.setPieceAtCord(5, 7, tPiece3);
		assertEquals("test3", testboard.getPieceAtCord(5, 7).getpName());
		testboard.setPieceAtCord(5, 8, tPiece4);
		assertEquals("test4", testboard.getPieceAtCord(5, 8).getpName());
		testboard.setPieceAtCord(5, 4, tPiece5);
		assertEquals("test5", testboard.getPieceAtCord(5, 4).getpName());
		
		//测试isPieceInBoard
		assertEquals(true, testboard.isPieceInBoard(tPiece1));
		assertEquals(true, testboard.isPieceInBoard(tPiece2));
		assertEquals(true, testboard.isPieceInBoard(tPiece3));
		assertEquals(true, testboard.isPieceInBoard(tPiece4));
		assertEquals(true, testboard.isPieceInBoard(tPiece5));
		
		//测试remove
		testboard.removePiece(5, 5);
		assertEquals(false, testboard.isPieceInBoard(tPiece1));
		testboard.removePiece(5, 6);
		assertEquals(false, testboard.isPieceInBoard(tPiece2));
		testboard.removePiece(5, 7);
		assertEquals(false, testboard.isPieceInBoard(tPiece3));
	}
}
