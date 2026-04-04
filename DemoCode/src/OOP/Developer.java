package OOP;

public class Developer extends Empolyee{

	private int overtimeHours;
	
	public Developer(String name, int id, double baseSalary, int overtimeHours) {
		super(name, id, baseSalary);
		this.overtimeHours = overtimeHours;
	}

	@Override
	public double calculateSalary() {
		// TODO Auto-generated method stub
		return getBaseSalary() + (overtimeHours*getBaseSalary()) * 2;
	}

}
