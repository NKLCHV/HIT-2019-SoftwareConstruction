/**
 * @author 1170300821LuoRuixin
 */
package P3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MyChessAndGoGame {
	
	public Game game;
	public String playerAName, playerBName;
	public ArrayList<Player> players = new ArrayList<Player>();

	public void printMenu() {
		System.out.println("1.\t将尚未在棋盘上的一颗棋子放在棋盘上的指定位置");
		System.out.println("2.\t移动棋盘上的某个位置的棋子至新的位置");
		System.out.println("3.\t（go）提子（移除对手棋子）");
		System.out.println("4.\t（chess）吃子（使用己方棋子吃掉对手棋子）");
		System.out.println("5.\t查询某个位置的占用情况");
		System.out.println("6.\t计算两个玩家分别在棋盘上的棋子总数");
		System.out.println("7.\t跳过此次操作");
		System.out.println("end.\t 结束游戏");
	}

	public void gameMain() {
		BufferedReader reader = null;
		String[] splitItems;
		try {
			reader = new BufferedReader(new InputStreamReader(System.in));
			while (true) {
				System.out.println("输入游戏类型:");
				String strLine = reader.readLine().trim();
				if (strLine.equals("chess") || strLine.equals("go")) {
					game = new Game(strLine);
					break;
				} else {
					System.out.println("输入错误，请重新输入");
				}
			}
			System.out.println("[用户A]\t请输入您的名称");
			playerAName = reader.readLine().trim();
			System.out.println("[用户B]\t请输入您的名称");
			playerBName = reader.readLine().trim();
			game.initGameWithPlayerName(playerAName, playerBName);
			players.add(game.getPlayerA());
			players.add(game.getPlayerB());
			System.out.println(String.format("%s，%s，游戏开始，请依次操作", playerAName, playerBName));

			int pNI = 0;//标记牌权
			while (true) {
				System.out.println("\n" + String.format("->[%s]", players.get(pNI).getPlayerName()));
				printMenu();
				String strLine = reader.readLine().trim();
				boolean exitFlag = false;
				switch (strLine) {
				case "1":
					// 将尚未在棋盘上的一颗棋子放在棋盘上的指定位置
					System.out.println("输入(pieceName,edX edY)：");
					strLine = reader.readLine().trim();
					splitItems = strLine.split("\\s");
					if (splitItems.length == 3) {
						try {
							int px = Integer.valueOf(splitItems[1]);
							int py = Integer.valueOf(splitItems[2]);
							String pName = splitItems[0];
							Player player = players.get(pNI);
							Piece piece = player.getAnyPieceByFilter(pName, 0);
							if (piece == null) {
								System.out.println(String.format("%s 没有未放置的 %s 棋子", player.getPlayerName(), pName));
								continue;
							}
							game.putPiece(player, piece, px, py);
							pNI = (pNI + 1) % 2;
							System.out.println("[SUCCESS]");
						} catch (MyExp e) {
							System.out.println(e.toString());
							continue;
							// TODO: handle exception
						} catch (NumberFormatException e) {
							System.out.println("输入类型错误，重新输入");
							continue;
						}
					} else {
						System.out.println("输入错误，请重新输入");
						continue;
					}
					break;
				case "2":
					// 移动棋盘上的某个位置的棋子至新的位置
					System.out.println("输入(stX stY edX edY)：");
					strLine = reader.readLine().trim();
					splitItems = strLine.split("\\s");
					if (splitItems.length == 4) {
						try {
							int stX = Integer.valueOf(splitItems[0]), stY = Integer.valueOf(splitItems[1]),
									edX = Integer.valueOf(splitItems[2]), edY = Integer.valueOf(splitItems[3]);
							Player player = players.get(pNI);
							game.movePiece(player, stX, stY, edX, edY);
							pNI = (pNI + 1) % 2;
							System.out.println("[SUCCESS]");
						} catch (MyExp e) {
							System.out.println(e.toString());
							continue;
							// TODO: handle exception
						} catch (NumberFormatException e) {
							System.out.println("输入类型错误，重新输入");
							continue;
						}
					} else {
						System.out.println("输入错误，请重新输入");
						continue;
					}
					break;
				case "3":
					// 提子
					System.out.println("输入(edX edY)：");
					strLine = reader.readLine().trim();
					splitItems = strLine.split("\\s");
					if (splitItems.length == 2) {
						try {
							int px = Integer.valueOf(splitItems[0]);
							int py = Integer.valueOf(splitItems[1]);
							Player player = players.get(pNI);
							game.removePiece(player, px, py);
							pNI = (pNI + 1) % 2;
							System.out.println("[SUCCESS]");

						} catch (MyExp e) {
							System.out.println(e.toString());
							continue;
							// TODO: handle exception
						} catch (NumberFormatException e) {
							System.out.println("输入类型错误，重新输入");
							continue;
						}
					} else {
						System.out.println("输入错误，请重新输入");
						continue;
					}
					break;
				case "4":
					// 吃子
					System.out.println("输入(stX stY edX edY)：");
					strLine = reader.readLine().trim();
					splitItems = strLine.split("\\s");
					if (splitItems.length == 4) {
						try {
							int stX = Integer.valueOf(splitItems[0]), stY = Integer.valueOf(splitItems[1]),
									edX = Integer.valueOf(splitItems[2]), edY = Integer.valueOf(splitItems[3]);
							Player player = players.get(pNI);
							game.eatPiece(player, stX, stY, edX, edY);
							pNI = (pNI + 1) % 2;
							System.out.println("[SUCCESS]");
						} catch (MyExp e) {
							System.out.println(e.toString());
							continue;
							// TODO: handle exception
						} catch (NumberFormatException e) {
							System.out.println("输入类型错误，重新输入");
							continue;
						}
					} else {
						System.out.println("输入错误，请重新输入");
						continue;
					}
					break;
				case "5":
					// 查询某个位置的占用情况
					System.out.println("输入(edX edY)：");
					strLine = reader.readLine().trim();
					splitItems = strLine.split("\\s");
					if (splitItems.length == 2) {
						try {
							int px = Integer.valueOf(splitItems[0]);
							int py = Integer.valueOf(splitItems[1]);
							Piece piece = game.getPieceAtCord(px, py);
							Player player = game.getOwnerAtCord(px, py);

							if (player == null) {
								System.out.println("该位置没有棋子");
							} else {
								System.out.println(
										String.format("该位置为 %s 的 %s 棋子", player.getPlayerName(), piece.getpName()));
							}

						} catch (MyExp e) {
							System.out.println(e.toString());
							continue;
							// TODO: handle exception
						} catch (NumberFormatException e) {
							System.out.println("输入类型错误，重新输入");
							continue;
						}
					} else {
						System.out.println("输入错误，请重新输入");
						continue;
					}
					break;
				case "6":
					// 计算两个玩家分别在棋盘上的棋子总数
					System.out.println(String.format("玩家\t%s\t在棋盘上的棋子总数为%d", players.get(0).getPlayerName(),
							game.getNumOfPlayerPiecesInBoard(players.get(0))));
					System.out.println(String.format("玩家\t%s\t在棋盘上的棋子总数为%d", players.get(1).getPlayerName(),
							game.getNumOfPlayerPiecesInBoard(players.get(1))));
					break;
				case "7":
					// 跳过
					System.out.println("[跳过]");
					pNI = (pNI + 1) % 2;
					break;
				case "end":
					// 结束游戏操作
					System.out.println("-------->>> END GAME <<<--------");
					exitFlag = true;
					break;
				default:
					System.out.println("输入错误，请重新输入");
					break;
				}
				if (exitFlag)
					break;
			}

			// 询问是否查看本次比赛的走棋历史
			pNI = 0;
			for (pNI = 0; pNI < 2; pNI++) {
				while (true) {
					System.out.println();
					System.out.println(String.format("->[%s]", players.get(pNI).getPlayerName()));
					System.out.println("是否需要查看本次游戏操作历史？[yes,no]");
					boolean exitFlag = true;
					switch (reader.readLine().trim()) {
					case "yes":
						System.out.println(players.get(pNI).getGameHistoryString());
						break;
					case "no":
						break;
					default:
						exitFlag = false;
						break;
					}
					if (exitFlag)
						break;
				}
			}
			System.out.println("游戏结束，再见！");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
				// TODO: handle exception
			}
		}
	}

	public static void main(String[] args) {
		new MyChessAndGoGame().gameMain();
	}

}
