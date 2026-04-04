package OOP;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		System.out.println("hello world");
//		Developer dev = new Developer("Nguyen", 10, 4500, 9);
//		System.out.println(dev.calculateSalary());
//		Manager manager = new Manager("Tom", 1, 5000, 3000);
//		System.out.println(manager.calculateSalary());
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter your name: ");
		String name = sc.nextLine();
		System.out.println("Enter your id: ");
		int id = Integer.parseInt(sc.nextLine());
		Manager manager = new Manager(name, id , 5000, 3000);
		System.out.println(manager.getName() + " | Total Salary: " + manager.calculateSalary());
		sc.close();
	}

}
