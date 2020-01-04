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
		System.out.println("1.\t����δ�������ϵ�һ�����ӷ��������ϵ�ָ��λ��");
		System.out.println("2.\t�ƶ������ϵ�ĳ��λ�õ��������µ�λ��");
		System.out.println("3.\t��go�����ӣ��Ƴ��������ӣ�");
		System.out.println("4.\t��chess�����ӣ�ʹ�ü������ӳԵ��������ӣ�");
		System.out.println("5.\t��ѯĳ��λ�õ�ռ�����");
		System.out.println("6.\t����������ҷֱ��������ϵ���������");
		System.out.println("7.\t�����˴β���");
		System.out.println("end.\t ������Ϸ");
	}

	public void gameMain() {
		BufferedReader reader = null;
		String[] splitItems;
		try {
			reader = new BufferedReader(new InputStreamReader(System.in));
			while (true) {
				System.out.println("������Ϸ����:");
				String strLine = reader.readLine().trim();
				if (strLine.equals("chess") || strLine.equals("go")) {
					game = new Game(strLine);
					break;
				} else {
					System.out.println("�����������������");
				}
			}
			System.out.println("[�û�A]\t��������������");
			playerAName = reader.readLine().trim();
			System.out.println("[�û�B]\t��������������");
			playerBName = reader.readLine().trim();
			game.initGameWithPlayerName(playerAName, playerBName);
			players.add(game.getPlayerA());
			players.add(game.getPlayerB());
			System.out.println(String.format("%s��%s����Ϸ��ʼ�������β���", playerAName, playerBName));

			int pNI = 0;//�����Ȩ
			while (true) {
				System.out.println("\n" + String.format("->[%s]", players.get(pNI).getPlayerName()));
				printMenu();
				String strLine = reader.readLine().trim();
				boolean exitFlag = false;
				switch (strLine) {
				case "1":
					// ����δ�������ϵ�һ�����ӷ��������ϵ�ָ��λ��
					System.out.println("����(pieceName,edX edY)��");
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
								System.out.println(String.format("%s û��δ���õ� %s ����", player.getPlayerName(), pName));
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
							System.out.println("�������ʹ�����������");
							continue;
						}
					} else {
						System.out.println("�����������������");
						continue;
					}
					break;
				case "2":
					// �ƶ������ϵ�ĳ��λ�õ��������µ�λ��
					System.out.println("����(stX stY edX edY)��");
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
							System.out.println("�������ʹ�����������");
							continue;
						}
					} else {
						System.out.println("�����������������");
						continue;
					}
					break;
				case "3":
					// ����
					System.out.println("����(edX edY)��");
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
							System.out.println("�������ʹ�����������");
							continue;
						}
					} else {
						System.out.println("�����������������");
						continue;
					}
					break;
				case "4":
					// ����
					System.out.println("����(stX stY edX edY)��");
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
							System.out.println("�������ʹ�����������");
							continue;
						}
					} else {
						System.out.println("�����������������");
						continue;
					}
					break;
				case "5":
					// ��ѯĳ��λ�õ�ռ�����
					System.out.println("����(edX edY)��");
					strLine = reader.readLine().trim();
					splitItems = strLine.split("\\s");
					if (splitItems.length == 2) {
						try {
							int px = Integer.valueOf(splitItems[0]);
							int py = Integer.valueOf(splitItems[1]);
							Piece piece = game.getPieceAtCord(px, py);
							Player player = game.getOwnerAtCord(px, py);

							if (player == null) {
								System.out.println("��λ��û������");
							} else {
								System.out.println(
										String.format("��λ��Ϊ %s �� %s ����", player.getPlayerName(), piece.getpName()));
							}

						} catch (MyExp e) {
							System.out.println(e.toString());
							continue;
							// TODO: handle exception
						} catch (NumberFormatException e) {
							System.out.println("�������ʹ�����������");
							continue;
						}
					} else {
						System.out.println("�����������������");
						continue;
					}
					break;
				case "6":
					// ����������ҷֱ��������ϵ���������
					System.out.println(String.format("���\t%s\t�������ϵ���������Ϊ%d", players.get(0).getPlayerName(),
							game.getNumOfPlayerPiecesInBoard(players.get(0))));
					System.out.println(String.format("���\t%s\t�������ϵ���������Ϊ%d", players.get(1).getPlayerName(),
							game.getNumOfPlayerPiecesInBoard(players.get(1))));
					break;
				case "7":
					// ����
					System.out.println("[����]");
					pNI = (pNI + 1) % 2;
					break;
				case "end":
					// ������Ϸ����
					System.out.println("-------->>> END GAME <<<--------");
					exitFlag = true;
					break;
				default:
					System.out.println("�����������������");
					break;
				}
				if (exitFlag)
					break;
			}

			// ѯ���Ƿ�鿴���α�����������ʷ
			pNI = 0;
			for (pNI = 0; pNI < 2; pNI++) {
				while (true) {
					System.out.println();
					System.out.println(String.format("->[%s]", players.get(pNI).getPlayerName()));
					System.out.println("�Ƿ���Ҫ�鿴������Ϸ������ʷ��[yes,no]");
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
			System.out.println("��Ϸ�������ټ���");
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
