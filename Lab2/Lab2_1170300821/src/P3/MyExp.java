/**
 * @author 1170300821LuoRuixin
 */
package P3;

public class MyExp extends RuntimeException {
	// ������Ϣ
	private String expMsg = "MyException";

	public MyExp(String msg) {
		this.expMsg = msg;
	}

	public String getExpMsg() {
		return expMsg;
	}

	public void setExpMsg(String expMsg) {
		this.expMsg = expMsg;
	}

	@Override
	public String toString() {
		return this.expMsg + "\t ����������";
	}

	// ʵ��assertTrue�Ķ��Թ��ܣ����condΪ�����׳��쳣��ʹ�øú�����������Ϸ��ж�
	public static void assertTrue(boolean cond, String msg) throws MyExp {
		if (!cond)
			throw new MyExp(msg);
	}

}