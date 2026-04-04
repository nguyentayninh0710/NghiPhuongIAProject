package OOP;

public class Manager extends Empolyee{

	private double bonus;
	
	public Manager(String name, int id, double baseSalary, double bonus) {
		super(name, id, baseSalary);
		this.bonus = bonus;
	}
	
	@Override
	public double calculateSalary() {
		// TODO Auto-generated method stub
		return getBaseSalary() + bonus;
	}

}
