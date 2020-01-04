/**
 * @author 1170300821LuoRuixin
 */
package P3;

public class Action {
	private Board gameBoard;// ��Ϸ�����̶��������
	private Player playerA, playerB;// ��Ϸ����Ҷ���AB������

	/**
	 * 
	 * @param gameBoard
	 *            ��Ϸ�����̶��������
	 * @param playerA
	 *            ��Ϸ����Ҷ���A������
	 * @param playerB
	 *            ��Ϸ����Ҷ���B������
	 */
	public Action(Board gameBoard, Player playerA, Player playerB) {
		this.gameBoard = gameBoard;
		this.playerA = playerA;
		this.playerB = playerB;
	}

	
	
	/**
	 * �����player��δ�������̵�piece�����䵽(x,y)��
	 * 
	 * @param player
	 * @param piece
	 * @param x
	 * @param y
	 */
	public void putPiece(Player player, Piece piece, int x, int y) {
		piece.setpX(x);
		piece.setpY(y);
		piece.setPieceState(1);
		player.addPiece(piece);
		gameBoard.setPieceAtCord(x, y, piece);
		player.addHistory("-add piece " + piece.getpName() + " to (" + x + "," + y + ")");
	}

	/**
	 * �����player���Ѿ����������ϵ�λ��st�������ƶ����յ�ַed
	 * 
	 * @param player
	 * @param stX
	 * @param stY
	 * @param ed
	 */
	public void movePiece(Player player, int stX, int stY, int edX, int edY) {
		Piece temp = gameBoard.getPieceAtCord(stX, stY);
		temp.setpX(edX);
		temp.setpY(edY);
		gameBoard.setPieceAtCord(edX, edY, temp);
		gameBoard.removePiece(stX, stY);
		player.movePiece(stX, stY, edX, edY);
		player.addHistory(
				"-move the piece " + temp.getpName() + " at (" + stX + "," + stY + ") to (" + edX + "," + edY + ")");
	}

	/**
	 * ���û�player��λ�������ϣ�x��y���������Ƴ�����
	 * 
	 * @param player
	 * @param x
	 * @param y
	 */
	public void removePiece(Player player, int x, int y) {
		String name = new String();
		if (gameBoard.getPieceAtCord(x, y) == null && player.getPiece(x, y) == null) {
			System.out.printf("There is no piece at (%d,%d) !\n", x, y);
		} else {
			gameBoard.removePiece(x, y);
			name = player.getPiece(x, y).getpName();
			player.getPiece(x, y).removeFromBoard();
		}
		player.addHistory("-remove the piece " + name + " at (" + x + "," + y + ")");
	}

	/**
	 * ʹ���û�player��λ������stλ�õ����ӳԵ������ֵ�edλ�õ�����
	 * 
	 * @param player
	 * @param stX
	 * @param stY
	 * @param edX
	 * @param edY
	 */
	public void eatPiece(Player player, int stX, int stY, int edX, int edY) {
		boolean flag = true;
		String name1 = new String();
		String name2 = new String();

		if (player == playerA) {
			if (playerA.getPiece(stX, stY) == null || playerB.getPiece(edX, edY) == null) {
				System.out.printf("There is no piece at (%d,%d) !\n", stX, stY);
				return;
			}
		} else {
			flag = false;
			if (playerB.getPiece(stX, stY) == null || playerA.getPiece(edX, edY) == null) {
				System.out.printf("There is no piece at (%d,%d) !\n", stX, stY);
				return;
			}
		}

		if (gameBoard.getPieceAtCord(stX, stY) == null) {
			System.out.printf("There is no piece at (%d,%d) !\n", stX, stY);
			return;
		}
		if (gameBoard.getPieceAtCord(edX, edY) == null) {
			System.out.printf("There is no piece at (%d,%d) !\n", edX, edY);
			return;
		}

		if (flag) {
			name1 = playerA.getPiece(stX, stY).getpName();
			name2 = playerB.getPiece(edX, edY).getpName();
			removePiece(playerB, edX, edY);
			movePiece(playerA, stX, stY, edX, edY);
		} else {
			name2 = playerA.getPiece(stX, stY).getpName();
			name1 = playerB.getPiece(edX, edY).getpName();
			removePiece(playerA, edX, edY);
			movePiece(playerB, stX, stY, edX, edY);
		}

		player.addHistory("-the " + name1 + " at (" + stX + "," + stY + ")" + " eat the " + name2 + " at (" + edX + ","
				+ edY + ")");
	}
}
