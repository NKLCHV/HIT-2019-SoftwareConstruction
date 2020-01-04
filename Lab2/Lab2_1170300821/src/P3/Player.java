/**
 * @author 1170300821LuoRuixin
 */
package P3;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Player {
	private String playerName = null;// 玩家名称
	private StringBuilder gameHistory = new StringBuilder();// 玩家操作历史
	private Set<Piece> playerPieces=new HashSet<>();

	public Player(String playerName) {
		setPlayerName(playerName);
	}

	public String getPlayerName() {
		return playerName;
	}

	public StringBuilder getGameHistoryString() {
		return gameHistory;
	}

	public Set<Piece> getPlayerPieces() {
		return playerPieces;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	/**
	 * 向玩家的set中添加piece
	 * 
	 * @param piece
	 * @return 若如果玩家已有该棋子，则返回false。
	 */
	public Boolean addPiece(Piece piece) {
		//System.out.println(piece.getpName()+"  "+piece.getpX()+" "+piece.getpY());
		if (playerPieces.contains(piece))
			return false;
		if(piece == null)
			return false;
		//piece.setPieceState(1);
		playerPieces.add(piece);
		return true;
	}

	/**
	 * 检查player是否棋子piece
	 * 
	 * @param piece
	 * @return 判断该玩家是否包含指定棋子
	 */
	public Boolean isContainPiece(Piece piece) {
		if (piece == null)
			return false;
		else
			return playerPieces.contains(piece) && piece.getPieceState() == 1;
	}

	/**
	 * 向操作历史中添加一步的操作
	 * 
	 * @param gameStep
	 */
	public void addHistory(String gameStep) {
		gameHistory.append(gameStep);
	}

	/**
	 * 将已经处于棋盘上的位于st的棋子移动到空地址ed
	 * 
	 * @param stX
	 * @param stY
	 * @param edX
	 * @param edY
	 * @return
	 */
	public boolean movePiece(int stX, int stY, int edX, int edY) {
		for (Piece piece : playerPieces) {
			if (piece.getpX() == stX && piece.getpY() == stY && piece.getPieceState() == 1) {
				piece.setpX(edX);
				piece.setpY(edY);
				return true;
			}
		}
		return false;
	}

	/**
	 * 返回(x,y)处的piece，若不存在则返回null
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public Piece getPiece(int x, int y) {
		for (Piece piece : playerPieces) {
			if (piece.getpX() == x && piece.getpY() == y && piece.getPieceState() == 1) {
				return piece;
			}
		}
		return null;
	}

	/**
	 * 
	 * @return
	 */
	public Piece getAnyPieceByFilter(String name,int state) {
		for(Piece piece : playerPieces) {
			//System.out.println(piece.getpName()+" "+piece.getPieceState());
			if(piece.getpName().equals(name) && piece.getPieceState() == state)
				return piece;
		}
		return null;
	}
}
