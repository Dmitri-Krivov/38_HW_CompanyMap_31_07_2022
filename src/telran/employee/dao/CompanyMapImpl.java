package telran.employee.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import telran.employee.model.Employee;
import telran.employee.model.SalesManager;

public class CompanyMapImpl implements Company {
	Map<Integer, Employee> employees;
	int capacity;
	public CompanyMapImpl(int capacity) {
		employees = new HashMap<>();
		this.capacity = capacity;
	}

	@Override
	public boolean addEmployee(Employee employee) {
		if (employees == null || employees.size() == capacity) {
			return false;
		}
		return employees.putIfAbsent(employee.getId(), employee) == null;
	}

	@Override
	public Employee removeEmployee(int id) {
		return employees.remove(id);

	}

	@Override
	public Employee findEmployee(int id) {
		return employees.get(id);
	}

	@Override
	public double totaleSalary() {
		Collection<Employee> collection = employees.values();
		double res = 0;
		for (Employee employee : collection) {
			res += employee.calcSalary();
		}
		return res;

	}

	@Override
	public double totalSales() {
		Collection<Employee> collection = employees.values();
		double res = 0;
		for (Employee employee : collection) {
			if (employee instanceof SalesManager) {
				res += ((SalesManager) employee).getSalesValue();
			}
		}
		return res;

	}

	@Override
	public int size() {
		return employees.size();
	}

	@Override
	public void printEmployee() {
		Collection<Employee> collection = employees.values();
		collection.forEach(e -> System.out.println(e));
//		collection.stream().forEach(System.out::println);

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
		for (Employee employee : employees.values()) {
			if (predicate.test(employee)) {
				res.add(employee);
			}
		}
		return res.toArray(new Employee[res.size()]);
	}

}
