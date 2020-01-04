/**
 * @author 1170300821LuoRuixin
 */
package P3;

public class Board {
	public static final int BOARDSIZELIMIT = 100;
	private int boardType;// �������ͣ�0Ϊ���ڸ����1Ϊ���ڽ�����
	private int boardSize;// ���̴�С��ָ�����������л������еĸ�����Ŀ
	private Piece[][] boardPieces = new Piece[BOARDSIZELIMIT][BOARDSIZELIMIT];// ��������϶�Ӧλ�����ŵ�����

	/**
	 * 
	 * @param boardType//
	 *            �������ͣ�0Ϊ���ڸ����1Ϊ���ڽ�����
	 * @param boardSize//
	 *            ���̴�С��ָ�����������л������еĸ�����Ŀ
	 */
	public Board(int boardType, int boardSize) {
		setBoardSize(boardSize);
		setBoardType(boardType);
	}

	
	
	public int getBoardType() {
		return boardType;
	}



	public void setBoardType(int boardType) {
		this.boardType = boardType;
	}



	public int getBoardSize() {
		return boardSize;
	}



	public void setBoardSize(int boardSize) {
		this.boardSize = boardSize;
	}



	/**
	 * ��ȡ����(px,py)λ�õ����ӣ����λ�ò��Ϸ��׳�MyExp
	 * 
	 * @param px
	 * @param py
	 * @return
	 */
	public Piece getPieceAtCord(int px, int py) {
		try {
			MyExp.assertTrue(this.isCordAvailable(px, py), "����λ����Ϸ�");
		} catch (MyExp e) {
			throw e;
		}
		return boardPieces[px][py];
	}

	private int bdLT() {
		return boardSize + boardType + 1;
	}

	/**
	 * ������piece���������̵�(px,py)λ�ô������λ�ò��Ϸ����׳�MyExp
	 * 
	 * @param px
	 * @param py
	 * @param piece
	 */
	public void setPieceAtCord(int px, int py, Piece piece) {
		try {
			MyExp.assertTrue(this.isCordAvailable(px, py), "����λ����Ϸ�");
		} catch (MyExp e) {
			throw e;
		}
		boardPieces[px][py] = piece;
	}

	/**
	 * �ж�����(cx,cy)�Ƿ���һ���Ϸ�����
	 * 
	 * @param cx
	 * @param cy
	 * @return
	 */
	public boolean isCordAvailable(int cx, int cy) {
		return cx > 0 && cy > 0 && cx <= bdLT() && cy <= bdLT();
	}

	/**
	 * �ж�����piece�Ƿ�������֮�ڡ�
	 * 
	 * @param piece
	 * @return
	 */
	public boolean isPieceInBoard(Piece piece) {
		for (int i = 0; i < bdLT(); i++) {
			for (int j = 0; j < bdLT(); j++) {
				if (boardPieces[i][j] == piece) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * ���player�������е�������Ŀ
	 * 
	 * @param player
	 * @return
	 */
	public int getNumOfPlayerPiecesInBoard(Player player) {
		int n = 0;
		for (int i = 0; i < bdLT(); i++) {
			for (int j = 0; j < bdLT(); j++) {
				if (player.isContainPiece(boardPieces[i][j]))
					n++;
			}
		}
		return n;
	}

	/**
	 * ȥ��(x,y)���ĵ�
	 * 
	 * @param x
	 * @param y
	 */
	public void removePiece(int x, int y) {
		try {
			MyExp.assertTrue(this.isCordAvailable(x, y), "����λ����Ϸ�");
		} catch (MyExp e) {
			throw e;
		}
		boardPieces[x][y] = null;
	}
}
