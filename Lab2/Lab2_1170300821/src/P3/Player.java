/**
 * @author 1170300821LuoRuixin
 */
package P3;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Player {
	private String playerName = null;// �������
	private StringBuilder gameHistory = new StringBuilder();// ��Ҳ�����ʷ
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
	 * ����ҵ�set�����piece
	 * 
	 * @param piece
	 * @return �����������и����ӣ��򷵻�false��
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
	 * ���player�Ƿ�����piece
	 * 
	 * @param piece
	 * @return �жϸ�����Ƿ����ָ������
	 */
	public Boolean isContainPiece(Piece piece) {
		if (piece == null)
			return false;
		else
			return playerPieces.contains(piece) && piece.getPieceState() == 1;
	}

	/**
	 * �������ʷ�����һ���Ĳ���
	 * 
	 * @param gameStep
	 */
	public void addHistory(String gameStep) {
		gameHistory.append(gameStep);
	}

	/**
	 * ���Ѿ����������ϵ�λ��st�������ƶ����յ�ַed
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
	 * ����(x,y)����piece�����������򷵻�null
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
