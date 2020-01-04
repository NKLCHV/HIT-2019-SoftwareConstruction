package P3;

public class te {

	public static void main(String[] args) {
		Game testgame1 = new Game("chess");
		testgame1.initGameWithPlayerName("Aplayer", "Bplayer");
		System.out.println(testgame1.getBoard().getPieceAtCord(1, 1).getpName());
		System.out.println(testgame1.getBoard().getPieceAtCord(8, 8).getpName());
		testgame1.movePiece(testgame1.getPlayerA(), 1, 1, 3, 3);
		// System.out.println(testgame1.getBoard().getPieceAtCord(1, 1).getpName());

		Game testgame2 = new Game("go");
		testgame2.initGameWithPlayerName("Aplayer", "Bplayer");
		System.out.println(testgame2.getPlayerA().getPlayerPieces().isEmpty());

		
		
		 
		Piece piece = testgame2.getPlayerA().getAnyPieceByFilter("╨звс", 0);
		if (piece != null)
			System.out.println(piece.getpName());
	}
}
