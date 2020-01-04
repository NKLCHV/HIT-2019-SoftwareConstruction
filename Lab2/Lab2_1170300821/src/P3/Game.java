/**
 * @author 1170300821LuoRuixin
 */
package P3;

import java.io.BufferedReader;
import java.io.FileReader;

public class Game {
	private String gameType;// ��Ϸ����
	private Board gameBoard;// ��Ϸ����
	private Action gameAction;// ��Ϸ����
	private Player PlayerA, PlayerB;// ��Ϸ��ң�playerAΪ����

	/**
	 * 
	 * @param gameType
	 *            ��Ϸ����
	 */
	public Game(String gameType) {
		this.gameType = gameType;
	}

	public Player getPlayerA() {
		return PlayerA;
	}

	public Player getPlayerB() {
		return PlayerB;
	}
	
	public Action getAction() {
		return gameAction;
	}
	
	public Board getBoard() {
		return gameBoard;
	}

	/**
	 * ͨ�������������ҵ����ֳ�ʼ��Game�еĸ������
	 * 
	 * @param paName
	 * @param pbName
	 */
	public void initGameWithPlayerName(String paName, String pbName) {
		PlayerA = new Player(paName);// ��ʼ������player
		PlayerB = new Player(pbName);

		try {
			int type, size;
			String filename = new String();
			if (gameType.equals("chess"))// ������Ϸ����ѡ���ȡ���ļ�
				filename = "src/P3/gameType_config_0.txt";
			else
				filename = "src/P3/gameType_config_1.txt";

			BufferedReader bfReader = new BufferedReader(new FileReader(filename));
			String line = bfReader.readLine();
			String[] splitline = line.split("\\s");// ��ȡ��һ�У�����gameboard
			type = Integer.valueOf(splitline[0]);// ��ȡ�������ͺ����̴�С
			size = Integer.valueOf(splitline[1]);
			gameBoard = new Board(type, size);

			int na = Integer.valueOf(bfReader.readLine());// ��ȡA��������Ŀ
			//System.out.println(na);
			for (int i = 0; i < na; i++) {
				if (gameType.equals("chess")) {
					String pline = bfReader.readLine();
					//System.out.println(pline);
					String[] psplitline = pline.split("\\s");
					int px = Integer.valueOf(psplitline[1]);
					int py = Integer.valueOf(psplitline[2]);
					Piece piece = new Piece(psplitline[0], 1, px, py);
					PlayerA.addPiece(piece);
					gameBoard.setPieceAtCord(px, py, piece);
				} else {
					Piece piece = new Piece("����", 0, 0, 0);
					PlayerA.addPiece(piece);
				}
			}

			int nb = Integer.valueOf(bfReader.readLine());// ��ȡB��������Ŀ
			//System.out.println(nb);
			for (int i = 0; i < nb; i++) {
				if (gameType.equals("chess")) {
					String pline = bfReader.readLine();
					String[] psplitline = pline.split("\\s");
					int px = Integer.valueOf(psplitline[1]);
					int py = Integer.valueOf(psplitline[2]);
					Piece piece = new Piece(psplitline[0], 1, px, py);
					PlayerB.addPiece(piece);
					gameBoard.setPieceAtCord(px, py, piece);
				} else {
					Piece piece = new Piece("����", 0, 0, 0);
					PlayerB.addPiece(piece);
				}
			}

			gameAction = new Action(gameBoard, PlayerA, PlayerB);// ����gameaction

			bfReader.close();

		} catch (Exception e) {
			System.out.println("file error!");
			e.printStackTrace();
		}
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
		try {
			MyExp.assertTrue(gameBoard.isCordAvailable(x, y), "����λ����Ϸ�");
		} catch (MyExp e) {
			throw e;
		}
		gameAction.putPiece(player, piece, x, y);
	}

	/**
	 * �����player���Ѿ����������ϵ�λ��st�������ƶ����յ�ַed
	 * 
	 * @param player
	 * @param stX
	 * @param stY
	 * @param edX
	 * @param edY
	 */
	public void movePiece(Player player, int stX, int stY, int edX, int edY) {
		try {
			MyExp.assertTrue(gameBoard.isCordAvailable(stX, stY), "����λ����Ϸ�");
			MyExp.assertTrue(gameBoard.isCordAvailable(edX, edY), "����λ����Ϸ�");
		} catch (MyExp e) {
			throw e;
		}
		gameAction.movePiece(player, stX, stY, edX, edY);
	}

	/**
	 * ���û�player��λ��������(x,y)�������Ƴ�����
	 * 
	 * @param player
	 * @param x
	 * @param y
	 */
	public void removePiece(Player player, int x, int y) {
		try {
			MyExp.assertTrue(gameBoard.isCordAvailable(x, y), "����λ����Ϸ�");
		} catch (MyExp e) {
			throw e;
		}
		gameAction.removePiece(player, x, y);
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
		try {
			MyExp.assertTrue(gameBoard.isCordAvailable(stX, stY), "����λ����Ϸ�");
			MyExp.assertTrue(gameBoard.isCordAvailable(edX, edY), "����λ����Ϸ�");
		} catch (MyExp e) {
			throw e;
		}
		gameAction.eatPiece(player, stX, stY, edX, edY);
	}

	/**
	 * ��ô���(x,y)λ�õ����ӵ�������
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public Player getOwnerAtCord(int x, int y) {
		try {
			MyExp.assertTrue(gameBoard.isCordAvailable(x, y), "����λ����Ϸ�");
		} catch (MyExp e) {
			throw e;
		}
		Piece piece = gameBoard.getPieceAtCord(x, y);
		if (PlayerA.isContainPiece(piece))
			return PlayerA;
		else if (PlayerB.isContainPiece(piece))
			return PlayerB;
		return null;
	}

	/**
	 * ��ȡ����(x,y)λ�õ�����piece�����û�������򷵻�null
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public Piece getPieceAtCord(int x, int y) {
		try {
			MyExp.assertTrue(gameBoard.isCordAvailable(x, y), "����λ����Ϸ�");
		} catch (MyExp e) {
			throw e;
		}
		Piece piece = null;
		piece = gameBoard.getPieceAtCord(x, y);
		return piece;
	}

	/**
	 * ��ȡ�û��������ϵ�����������Ŀ
	 * 
	 * @param player
	 * @return
	 */
	public int getNumOfPlayerPiecesInBoard(Player player) {
		return gameBoard.getNumOfPlayerPiecesInBoard(player);
	}
}
