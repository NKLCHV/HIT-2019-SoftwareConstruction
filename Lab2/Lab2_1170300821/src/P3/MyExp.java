/**
 * @author 1170300821LuoRuixin
 */
package P3;

public class MyExp extends RuntimeException {
	// 错误信息
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
		return this.expMsg + "\t 请重新输入";
	}

	// 实现assertTrue的断言功能，如果cond为假则抛出异常，使用该函数进行输入合法判断
	public static void assertTrue(boolean cond, String msg) throws MyExp {
		if (!cond)
			throw new MyExp(msg);
	}

}