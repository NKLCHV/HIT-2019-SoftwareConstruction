/**
 * @author 1170300821LuoRuixin
 */
package P3;

public class Piece {
	private int pieceState = 0;
	private int pX = 0, pY = 0;
	private String pName;

	/**
	 * 
	 * @param pName
	 * @param pieceState
	 *            0Ϊδ���ã�1Ϊ�Ѿ����ã�2Ϊ����֮���ó������Ҳ�����
	 * @param pX
	 *            ����
	 * @param pY
	 */
	public Piece(String pName, int pieceState, int pX, int pY) {
		setPieceState(pieceState);
		setpName(pName);
		setpX(pX);
		setpY(pY);

	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public int getpX() {
		return pX;
	}

	public void setpX(int pX) {
		this.pX = pX;
	}

	public int getpY() {
		return pY;
	}

	public void setpY(int pY) {
		this.pY = pY;
	}

	public int getPieceState() {
		return pieceState;
	}

	public void setPieceState(int pieceState) {
		this.pieceState = pieceState;
	}

	public String getpName() {
		return pName;
	}

	public void removeFromBoard() {
		pieceState = 2;
		pX = 0;
		pY = 0;
	}
}
