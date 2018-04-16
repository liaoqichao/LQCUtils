package test.proxyfactory;

public class Cat implements Animal{

	private int age;
	private String name;
	
	@Override
	public void bark() {
		// TODO Auto-generated method stub
		System.out.println("miao~");
	}
	

	public Cat(int age, String name) {
		super();
		this.age = age;
		this.name = name;
	}



	public Cat() {
		super();
		// TODO Auto-generated constructor stub
	}



	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
