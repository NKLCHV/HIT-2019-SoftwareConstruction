/**
 * @author 1170300821LuoRuixin
 */
package P3;

import java.io.BufferedReader;
import java.io.FileReader;

public class Game {
	private String gameType;// 游戏类型
	private Board gameBoard;// 游戏棋盘
	private Action gameAction;// 游戏动作
	private Player PlayerA, PlayerB;// 游戏玩家，playerA为先手

	/**
	 * 
	 * @param gameType
	 *            游戏类型
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
	 * 通过传入的两个玩家的名字初始化Game中的各类对象
	 * 
	 * @param paName
	 * @param pbName
	 */
	public void initGameWithPlayerName(String paName, String pbName) {
		PlayerA = new Player(paName);// 初始化两个player
		PlayerB = new Player(pbName);

		try {
			int type, size;
			String filename = new String();
			if (gameType.equals("chess"))// 根据游戏类型选择读取的文件
				filename = "src/P3/gameType_config_0.txt";
			else
				filename = "src/P3/gameType_config_1.txt";

			BufferedReader bfReader = new BufferedReader(new FileReader(filename));
			String line = bfReader.readLine();
			String[] splitline = line.split("\\s");// 读取第一行，创建gameboard
			type = Integer.valueOf(splitline[0]);// 获取棋盘类型和棋盘大小
			size = Integer.valueOf(splitline[1]);
			gameBoard = new Board(type, size);

			int na = Integer.valueOf(bfReader.readLine());// 读取A的棋子数目
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
					Piece piece = new Piece("黑子", 0, 0, 0);
					PlayerA.addPiece(piece);
				}
			}

			int nb = Integer.valueOf(bfReader.readLine());// 读取B的棋子数目
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
					Piece piece = new Piece("白子", 0, 0, 0);
					PlayerB.addPiece(piece);
				}
			}

			gameAction = new Action(gameBoard, PlayerA, PlayerB);// 创建gameaction

			bfReader.close();

		} catch (Exception e) {
			System.out.println("file error!");
			e.printStackTrace();
		}
	}

	/**
	 * 将玩家player的未处于棋盘的piece棋子落到(x,y)处
	 * 
	 * @param player
	 * @param piece
	 * @param x
	 * @param y
	 */
	public void putPiece(Player player, Piece piece, int x, int y) {
		try {
			MyExp.assertTrue(gameBoard.isCordAvailable(x, y), "棋子位置须合法");
		} catch (MyExp e) {
			throw e;
		}
		gameAction.putPiece(player, piece, x, y);
	}

	/**
	 * 将玩家player的已经处于棋盘上的位于st的棋子移动到空地址ed
	 * 
	 * @param player
	 * @param stX
	 * @param stY
	 * @param edX
	 * @param edY
	 */
	public void movePiece(Player player, int stX, int stY, int edX, int edY) {
		try {
			MyExp.assertTrue(gameBoard.isCordAvailable(stX, stY), "棋子位置须合法");
			MyExp.assertTrue(gameBoard.isCordAvailable(edX, edY), "棋子位置须合法");
		} catch (MyExp e) {
			throw e;
		}
		gameAction.movePiece(player, stX, stY, edX, edY);
	}

	/**
	 * 将用户player的位于棋盘上(x,y)的棋子移出棋盘
	 * 
	 * @param player
	 * @param x
	 * @param y
	 */
	public void removePiece(Player player, int x, int y) {
		try {
			MyExp.assertTrue(gameBoard.isCordAvailable(x, y), "棋子位置须合法");
		} catch (MyExp e) {
			throw e;
		}
		gameAction.removePiece(player, x, y);
	}

	/**
	 * 使用用户player的位于棋盘st位置的棋子吃掉到对手的ed位置的棋子
	 * 
	 * @param player
	 * @param stX
	 * @param stY
	 * @param edX
	 * @param edY
	 */
	public void eatPiece(Player player, int stX, int stY, int edX, int edY) {
		try {
			MyExp.assertTrue(gameBoard.isCordAvailable(stX, stY), "棋子位置须合法");
			MyExp.assertTrue(gameBoard.isCordAvailable(edX, edY), "棋子位置须合法");
		} catch (MyExp e) {
			throw e;
		}
		gameAction.eatPiece(player, stX, stY, edX, edY);
	}

	/**
	 * 获得处于(x,y)位置的棋子的所有者
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public Player getOwnerAtCord(int x, int y) {
		try {
			MyExp.assertTrue(gameBoard.isCordAvailable(x, y), "棋子位置须合法");
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
	 * 获取处于(x,y)位置的棋子piece，如果没有棋子则返回null
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public Piece getPieceAtCord(int x, int y) {
		try {
			MyExp.assertTrue(gameBoard.isCordAvailable(x, y), "棋子位置须合法");
		} catch (MyExp e) {
			throw e;
		}
		Piece piece = null;
		piece = gameBoard.getPieceAtCord(x, y);
		return piece;
	}

	/**
	 * 获取用户在棋盘上的所有棋子数目
	 * 
	 * @param player
	 * @return
	 */
	public int getNumOfPlayerPiecesInBoard(Player player) {
		return gameBoard.getNumOfPlayerPiecesInBoard(player);
	}
}
