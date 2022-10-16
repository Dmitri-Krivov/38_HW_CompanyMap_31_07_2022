package telran.employee.dao;

import java.util.function.Predicate;

import telran.employee.model.Employee;
import telran.employee.model.SalesManager;

public class CompanyImpl implements Company {

	Employee[] employees;
	int size;

	public CompanyImpl(int capacity) {
		employees = new Employee[capacity];
	}

	@Override
	public boolean addEmployee(Employee employee) {
		if (employee == null || size == employees.length || findEmployee(employee.getId()) != null) {
			return false;
		}
		employees[size++] = employee;
		return true;
	}

	@Override
	public Employee removeEmployee(int id) {
		for (int i = 0; i < size; i++) {
			if (id == employees[i].getId()) {
				Employee victim = employees[i];
				employees[i] = employees[--size];
				employees[size] = null;
				return victim;
			}
		}
		return null;
	}

	@Override
	public Employee findEmployee(int id) {
		for (int i = 0; i < size; i++) {
			if (id == employees[i].getId()) {
				return employees[i];
			}
		}
		return null;
	}

	@Override
	public double totaleSalary() {
		double totalSalary = 0;
		for (int i = 0; i < size; i++) {
			totalSalary += employees[i].calcSalary();
		}
		return totalSalary;
	}

//	@Override
//	public double averageSalary() {
//		return totaleSalary() / size;
//	}

	@Override
	public double totalSales() {
		double totalSales = 0;
		for (int i = 0; i < size; i++) {
			if (employees[i] instanceof SalesManager)
				totalSales += ((SalesManager) employees[i]).getSalesValue();
		}
		return totalSales;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public void printEmployee() {
		for (int i = 0; i < size; i++) {
			System.out.println(i + " - - - " + employees[i]);
		}

	}

	@Override
	public Employee[] findEmployeesHoursGreateThan(int hours) {
		return findEmployeesByPredicate(e -> e.getHours() >= hours);
	}

	@Override
	public Employee[] findEmployeesSalaryBetween(double min, double max) {
		Predicate<Employee> predicate = new Predicate<>() {

			@Override
			public boolean test(Employee t) {

				return t.calcSalary() >= min && t.calcSalary() < max;
			}

		};
		return findEmployeesByPredicate(predicate);
	}

	private Employee[] findEmployeesByPredicate(Predicate<Employee> batska) {
		int count = 0;
		for (int i = 0; i < size; i++) {
			if (batska.test(employees[i])) {
				count++;
			}
		}
		Employee[] res = new Employee[count];
		for (int i = 0, j = 0; j < res.length; i++) {
			if (batska.test(employees[i])) {
				res[j++] = employees[i];
			}
		}
		return res;
	}

}
