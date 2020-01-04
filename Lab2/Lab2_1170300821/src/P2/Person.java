package P2;

public class Person {
	private String name;
	
	public Person(String string) {
		setName(string);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}
}
