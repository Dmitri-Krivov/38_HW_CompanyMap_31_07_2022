package telran.employee.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import telran.employee.model.Employee;
import telran.employee.model.SalesManager;

public class CompanyStreamImpl implements Company {
	Map<Integer, Employee> employees;
	int capacity;

	public CompanyStreamImpl(int capacity) {
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
		Double res2 = collection.stream()
//				.map(x -> x.calcSalary())
//				//.map(Employee::calcSalary)
//				.reduce(.0, (a, b) -> a + b);

				.mapToDouble(x -> x.calcSalary()).sum();
		return res2;

	}

	@Override
	public double totalSales() {
//		Collection<Employee> collection = employees.values();
//		Double res2 = collection.stream()
//				.filter(x -> x instanceof SalesManager)
//				.map(x -> ((SalesManager) x).getSalesValue())
//				//.map(SalesManager::getSalesValue)
//				.reduce(.0, (acc, n) -> acc + n);
//		return res2;

		return employees.values().stream().filter(x -> x instanceof SalesManager)
// 			.map(x -> ((SalesManager) x).getSalesValue())//1 zdecb mozhet bit raznie sposoby
//			.map(e->(SalesManager) e)//2 ili tak
//		    .map(SalesManager::getSalesValue)

//			.map(this::salesValue)//3
//			.reduce(.0, (acc, n) -> acc + n);

				.mapToDouble(this::salesValue)// 3
				.sum();

	}

	private double salesValue(Employee e) {
		return ((SalesManager) e).getSalesValue();
	}

	@Override
	public int size() {
		return employees.size();
	}

	@Override
	public void printEmployee() {
//		Collection<Employee> collection = employees.values();
//		collection.stream().forEach(System.out::println);

//		 employees.values().forEach(System.out::println);
		employees.forEach((k, v) -> System.out.println(v + "-------" + k));

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
//		ArrayList<Employee> res = new ArrayList<>();
//		Collection<Employee> res2 = employees.values();
//		res2.stream()
//		.filter(x -> predicate.test(x))
//		.forEach(x -> res.add(x));
//		return res.toArray(new Employee[res.size()]);

		return employees.values().stream().filter(predicate).toArray(Employee[]::new);
		// .toArray(s->new Employee[s]);

	}

}
