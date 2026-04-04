package OOP;

abstract class Empolyee {
	private String name;
	private int id;
	private double baseSalary;
	
	public Empolyee(String name, int id, double baseSalary) {
		this.name = name;
		this.id = id;
		this.baseSalary = baseSalary;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getBaseSalary() { 
		return baseSalary;
	}
	
	public abstract double calculateSalary();
	
}
