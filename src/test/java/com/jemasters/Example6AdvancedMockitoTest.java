package com.jemasters;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.refEq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.jemasters.interfaces.DataGrid;
import com.jemasters.model.Customer;
import com.jemasters.untestable.CustomerContainer;
import com.jemasters.untestable.CustomerDeleter;

public class Example6AdvancedMockitoTest {

	/*
	 * This test demonstrates some more advanced features of Mockito
	 * 
	 * times(number) VerificationMode
	 * 
	 * inOrder VerificationMode
	 * 
	 * refEq Matcher
	 * 
	 * using @InjectMocks to quickly stub classes
	 */

	@InjectMocks
	private Example6AdvancedMockito classUnderTest;

	// @formatter:off
	@Mock DataGrid dataGrid;
	@Mock DataGrid backupDataGrid;
	@Mock DataGrid reportingDataGrid;
	@Mock DataGrid productDataGrid;

	@Mock CustomerDeleter customerDeleter;
	@Mock CustomerContainer customerContainer;
	// @formatter:on

	@Before
	public void setUp() throws Exception {
		initMocks(this);

	}

	@Test
	public void shouldIncrementOnceForEachItemInBasket() throws Exception {
		// Given
		Map<String, Integer> basketProductToQuantity = new HashMap<String, Integer>();
		basketProductToQuantity.put("product1", 1);
		basketProductToQuantity.put("product2", 4);
		basketProductToQuantity.put("product27", 1);

		// When
		classUnderTest.updateBasketSizeToDataGrid(basketProductToQuantity);

		verify(dataGrid, times(6)).increment("basketQuantity");
	}
	/*
	 * We can tell Mockito how many times a particular interaction should happen
	 * by using the times(numInvocations) method
	 */

	@Test
	public void shouldPrepareForUpdateThenPerformUpdateThenCommitUpdate() throws Exception {
		// Given
		Customer customer = new Customer();
		customer.id = "cust123";
		customer.firstName = "Robert";
		customer.lastName = "Stevenson";

		when(dataGrid.prepare(customer)).thenReturn(true);
		when(dataGrid.update(customer)).thenReturn(true);

		// When
		classUnderTest.transactionalUpdateUser(customer);

		// Then
		InOrder customerUpdateOrder = inOrder(dataGrid);
		customerUpdateOrder.verify(dataGrid).prepare(customer);
		customerUpdateOrder.verify(dataGrid).update(customer);
		customerUpdateOrder.verify(dataGrid).commit(customer);
	}
	/*
	 * In this test we are using the inOrder(mock) method to generate an InOrder
	 * object that we can use to make sure the interactions with a given object
	 * occur in precisely the order defined
	 */

	@Test
	public void shouldStoreCustomerProperties() throws Exception {
		// Given
		Map<String, Object> customerProps = new HashMap<String, Object>();
		customerProps.put("id", "cust123");
		customerProps.put("firstName", "Robert");
		customerProps.put("lastName", "Stevenson");
		customerProps.put("sessionId", "session_5678");

		// When
		classUnderTest.storeCustomerProperties(customerProps);

		// Then
		Customer customer = new Customer();
		customer.id = "cust123";
		customer.firstName = "Robert";
		customer.lastName = "Stevenson";
		customer.sessionId = "session_1234";
		verify(dataGrid).store(eq("customer:cust123"), refEq(customer, new String[] { "sessionId" }));
	}
	/*
	 * The test above uses the refEq Matcher, which compares objects using
	 * reflection to inspect properties of the objects are equal, even if they
	 * are not the same object. This is useful when your class builds a class
	 * and you want to match based on it's properties and the object doesn't
	 * override equals()
	 */

	List<Customer> customerList;

	@Test
	public void shouldProtectAgainstSideEffects() throws Exception {
		// Given
		List<String> customers = new ArrayList<String>();
		customers.add("customer1");
		customers.add("customer2");

		customerList = new ArrayList<>();
		Customer customer1 = new Customer();
		customer1.id = "customer1";
		customerList.add(customer1);
		Customer customer2 = new Customer();
		customer2.id = "customer2";
		customerList.add(customer2);

		when(customerContainer.getCustomers()).thenReturn(customerList);

		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				Customer customer = (Customer) invocation.getArguments()[0];
				customerList.remove(customer);
				return null;
			}
		}).when(customerDeleter).deleteCustomer(any(Customer.class), eq(customerContainer));

		// When
		classUnderTest.deleteUsers(customers, customerContainer);

		// Then
		verify(customerDeleter).deleteCustomer(customer1, customerContainer);
		verify(dataGrid).remove("customer1");
		verify(customerDeleter).deleteCustomer(customer2, customerContainer);
		verify(dataGrid).remove("customer2");

	}

	/*
	 * The test above demonstrates how to use the Answer interface to emulate
	 * the actions a method call on a mock might have at runtime
	 * 
	 * For example, in this case the mock called inside the loop is a poorly
	 * documented mutator, which modifies the list held on the container. This
	 * results in too few interactions when the test is run. The code has to be
	 * refactored to perform its work outside the loop, instead storing a
	 * temporary copy of the items to be removed.
	 * 
	 */

	@Test
	public void shouldCheckDataGridHealth() throws Exception {
		// Given
		when(dataGrid.ping()).thenReturn(true);
		when(backupDataGrid.ping()).thenReturn(true);
		when(reportingDataGrid.ping()).thenReturn(true);
		when(productDataGrid.ping()).thenReturn(true);

		// Then
		assertThat(classUnderTest.healthCheckGrids(), is(true));
		verify(dataGrid).ping();
		verify(backupDataGrid).ping();
		verify(reportingDataGrid).ping();
		verify(productDataGrid).ping();
	}
}
