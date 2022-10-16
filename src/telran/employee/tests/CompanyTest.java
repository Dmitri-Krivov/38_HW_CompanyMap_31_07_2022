package telran.employee.tests;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import telran.employee.dao.Company;
import telran.employee.dao.CompanyStreamImpl;
import telran.employee.model.Employee;
import telran.employee.model.Manager;
import telran.employee.model.SalesManager;
import telran.employee.model.WageEmployee;

class CompanyTest {	
	
	Company company;
	Employee[] firm;

	@BeforeEach
	void setUp() throws Exception {
		company = new CompanyStreamImpl(5);
		firm = new Employee[4];
		firm[0] = new Manager(1000, "John", "Smith", 182, 20_000, 20);
		firm[1] = new WageEmployee(2000, "Mary", "Smith", 182, 40);
		firm[2] = new SalesManager(3000, "Peter", "Jackson", 182, 40_000, 0.1);
		firm[3] = new SalesManager(4000, "Tigran", "Petrosian", 91, 80_000, 0.1);
		for (int i = 0; i < firm.length; i++) {
			company.addEmployee(firm[i]);
		}
	}

	@Test
	void testAddEmployee() {
		assertFalse(company.addEmployee(firm[3]));
		Employee employee = new SalesManager(5000, "Peter", "Jackson", 182, 40_000, 0.1);
		assertTrue(company.addEmployee(employee));
		assertEquals(5, company.size());
		employee = new SalesManager(6000, "Peter", "Jackson", 182, 40_000, 0.1);
		assertFalse(company.addEmployee(employee));
	}

	@Test
	void testRemoveEmployee() {
		Employee actual = company.removeEmployee(3000);
		assertEquals(firm[2], actual);
		assertEquals(firm[2].getLastName(), actual.getLastName());
		assertNull(company.findEmployee(3000));
		assertEquals(3, company.size());
		assertNull(company.removeEmployee(6000));
		assertEquals(3, company.size());
	}

	@Test
	void testFindEmployee() {
		assertEquals(firm[1], company.findEmployee(2000));
		assertNull(company.findEmployee(5000));
	}

	@Test
	void testTotalSalary() {
		assertEquals(44380.0, company.totaleSalary(), 0.01);
	}

	@Test
	void testAverageSalary() {
		assertEquals(44380.0 / 4, company.averageSalary(), 0.01);
	}

	@Test
	void testTotalSales() {
		assertEquals(120000, company.totalSales(), 0.01);
	}

	@Test
	void testSize() {
		assertEquals(4, company.size());
	}

	@Test
	void testPrintEmployees() {
		company.printEmployee();
	}

	@Test
	void testFindEmployeesHoursGreateThan() {
		Employee[] actual = company.findEmployeesHoursGreateThan(100);
		Arrays.sort(actual, (e1, e2)-> Integer.compare(e1.getId(), e2.getId()));
		Employee[] expected = { firm[0], firm[1], firm[2] };
		assertArrayEquals(expected, actual);
	}

	@Test
	void testFindEmployeesSalaryBetween() {
		Employee[] actual = company.findEmployeesSalaryBetween(5000, 8000);
		Arrays.sort(actual, (e1, e2)-> Integer.compare(e1.getId(), e2.getId()));
		Employee[] expected = { firm[1], firm[2] };
		assertArrayEquals(expected, actual);
	}

}
