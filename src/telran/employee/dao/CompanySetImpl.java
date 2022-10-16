package telran.employee.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import telran.employee.model.Employee;
import telran.employee.model.SalesManager;

public class CompanySetImpl implements Company {
	Set<Employee> employees;
	int capacity;

	public CompanySetImpl(int capacity) {
		employees = new HashSet<>();
		this.capacity = capacity;
	}

//	O(1)
	@Override
	public boolean addEmployee(Employee employee) {
		if (employees == null || employees.size() == capacity) {
			return false;

		}

		return employees.add(employee);
	}

//	O(n)
	@Override
	public Employee removeEmployee(int id) {
		Employee victim = findEmployee(id);
		if (victim != null) {
			employees.remove(victim);
		}
		return victim;
	}

//	O(n)
	@Override
	public Employee findEmployee(int id) {
		for (Employee employee : employees) {
			if (employee.getId() == id) {
				return employee;
			}
		}
		return null;
	}

	@Override
	public double totaleSalary() {
		double sum = 0;
		for (Employee employee : employees) {
			sum += employee.calcSalary();
		}
		return sum;
	}

	@Override
	public double totalSales() {
		double sum = 0;
		for (Employee employee : employees) {
			if (employee instanceof SalesManager) {
				SalesManager sm = (SalesManager) employee;
				sum += sm.getSalesValue();
			}
		}
		return sum;
	}

	@Override
	public int size() {
		return employees.size();
	}

	@Override
	public void printEmployee() {
		for (Employee employee : employees) {
			System.out.println(employee);
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

	private Employee[] findEmployeesByPredicate(Predicate<Employee> predicate) {
		ArrayList<Employee> res = new ArrayList<>();
		for (Employee employee : employees) {
			if (predicate.test(employee)) {
				res.add(employee);
			}
		}
		return res.toArray(new Employee[res.size()]);
	}
}
