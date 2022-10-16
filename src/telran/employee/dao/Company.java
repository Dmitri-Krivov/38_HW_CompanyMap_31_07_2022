package telran.employee.dao;

import telran.employee.model.Employee;

public interface Company {
	String NAME = "Apple";

	boolean addEmployee(Employee employee); 

	Employee removeEmployee(int id);

	Employee findEmployee(int id);

	double totaleSalary();

	default double averageSalary() {
		return totaleSalary() / size();
	}
	double totalSales();

	int size();

	void printEmployee();
	
	Employee[] findEmployeesHoursGreateThan(int hours);
	
	Employee[] findEmployeesSalaryBetween(double min, double max);

}
