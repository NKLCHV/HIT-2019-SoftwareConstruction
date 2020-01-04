/**
 * @author 1170300821LuoRuixin
 */
package P3;

public class Board {
	public static final int BOARDSIZELIMIT = 100;
	private int boardType;// 棋盘类型，0为放在格子里，1为放在交点上
	private int boardSize;// 棋盘大小，指的是棋盘上行或列所有的格子数目
	private Piece[][] boardPieces = new Piece[BOARDSIZELIMIT][BOARDSIZELIMIT];// 存放棋盘上对应位置所放的棋子

	/**
	 * 
	 * @param boardType//
	 *            棋盘类型，0为放在格子里，1为放在交点上
	 * @param boardSize//
	 *            棋盘大小，指的是棋盘上行或列所有的格子数目
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
	 * 获取处于(px,py)位置的棋子，如果位置不合法抛出MyExp
	 * 
	 * @param px
	 * @param py
	 * @return
	 */
	public Piece getPieceAtCord(int px, int py) {
		try {
			MyExp.assertTrue(this.isCordAvailable(px, py), "棋子位置须合法");
		} catch (MyExp e) {
			throw e;
		}
		return boardPieces[px][py];
	}

	private int bdLT() {
		return boardSize + boardType + 1;
	}

	/**
	 * 将棋子piece放置在棋盘的(px,py)位置处。如果位置不合法则抛出MyExp
	 * 
	 * @param px
	 * @param py
	 * @param piece
	 */
	public void setPieceAtCord(int px, int py, Piece piece) {
		try {
			MyExp.assertTrue(this.isCordAvailable(px, py), "棋子位置须合法");
		} catch (MyExp e) {
			throw e;
		}
		boardPieces[px][py] = piece;
	}

	/**
	 * 判断坐标(cx,cy)是否是一个合法坐标
	 * 
	 * @param cx
	 * @param cy
	 * @return
	 */
	public boolean isCordAvailable(int cx, int cy) {
		return cx > 0 && cy > 0 && cx <= bdLT() && cy <= bdLT();
	}

	/**
	 * 判断棋子piece是否处于棋盘之内。
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
	 * 获得player在棋盘中的棋子数目
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
	 * 去除(x,y)处的点
	 * 
	 * @param x
	 * @param y
	 */
	public void removePiece(int x, int y) {
		try {
			MyExp.assertTrue(this.isCordAvailable(x, y), "棋子位置须合法");
		} catch (MyExp e) {
			throw e;
		}
		boardPieces[x][y] = null;
	}
}
