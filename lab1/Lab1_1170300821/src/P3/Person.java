package P3;

public class Person {

	public String name;
	
	public void SetName(String name) {
		this.name=name;
	}
	
	public String GetName() {
		return name;		
	}
	
	public Person(String string) {
		SetName(string);
	}
}
