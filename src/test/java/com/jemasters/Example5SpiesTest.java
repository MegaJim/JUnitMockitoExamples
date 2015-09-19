package com.jemasters;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Queue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.jemasters.interfaces.DataGrid;

public class Example5SpiesTest {

	/*
	 * A Spy is a special type of mock, which wraps a real object.
	 * 
	 * Mockito can stub behaviours on a spy, or let the real object reply.
	 * 
	 * Mockito can also record & verify interactions with a spy.
	 * 
	 * Because of their dual nature, spies are also known as 'partial mocks'
	 * 
	 * Generally speaking partial mocks are a bad thing, which indicate that
	 * classes are too tightly-coupled, but sometimes (especially when dealing
	 * with legacy code) they are the lesser of two evils
	 * 
	 * Take the Example5Spies class for this test, it extends another class
	 * (NastySuperClass) which performs the field validation. Assume that we
	 * cannot change this super class (maybe it's provided by a jar, or it's
	 * used in too many places to change right now...)
	 * 
	 * We need to make sure our extended class saves the contact details to the
	 * data grid but we can't mock the UserValidator in the superclass because
	 * it's static.
	 * 
	 * Whilst there may be ways around this (such as using PowerMock in a later
	 * example) we should only mock the immediate dependencies of our class -
	 * that includes super classes too
	 * 
	 */

	private Example5Spies formHandler;

	//@formatter:off
	@Mock DataGrid dataGrid;
	@Mock Queue<String> logQueue;
	//@formatter:on

	@Before
	public void setUp() throws Exception {
		initMocks(this);

		formHandler = spy(new Example5Spies());

		formHandler.setDataGrid(dataGrid);
		formHandler.setLogQueue(logQueue);
	}
	/*
	 * The spy() method wraps the real object inside a partial mock.
	 * 
	 * Any interactions with the spy will call the real object unless the spy is
	 * stubbed to do otherwise
	 */

	@Test
	public void shouldStoreUserDetailsToDataGridWhenTheyAreValid() throws Exception {
		// Given
		formHandler.setUserId("user123");
		formHandler.setFirstName("Amitabh");
		formHandler.setLastName("Bachchan");

		doReturn(true).when(formHandler).validateFields();

		// When
		formHandler.handleUpdateUserDetails();

		// Then
		verify(dataGrid).store("user123:firstName", "Amitabh");
		verify(dataGrid).store("user123:lastName", "Bachchan");
	}
	/*
	 * This test introduces two new concepts: 1. We are mocking behaviour on the
	 * object under test by using a spy to avoid dealing with bad code in the
	 * super class 2. An alternative Mockito syntax for stubbing behaviour
	 * 
	 * In previous tests we used the form
	 * when(methodInvocation).thenReturn(stubbedValue), but in this case that
	 * would cause an exception because the method invocation argument to when()
	 * would call the real method validateFields(), but this would not work
	 * because the URL used in the LegacyUserValidator doesn't exist in our
	 * environment. Instead we use the form
	 * doReturn(stubbedValue).when(mock).methodInvocation()
	 * 
	 * Because the method invocation is proxied through the when() method, the
	 * real method is never invoked
	 */

	@Test
	public void shouldNotStoreAnythingWhenDetailsAreInvalid() throws Exception {
		// Given
		doReturn(false).when(formHandler).validateFields();

		// When
		formHandler.handleUpdateUserDetails();

		// Then
		verify(dataGrid, never()).store(anyString(), anyObject());
		verifyZeroInteractions(dataGrid);
	}

	/*
	 * We can also use spying to verify some internal interactions on the class
	 * under test, whilst still allowing the real logic of the spy to be invoked
	 */
	@Test
	public void shouldLogUserUpdate() throws Exception {
		// Given
		formHandler.setUserId("user123");
		formHandler.setFirstName("Amitabh");
		formHandler.setLastName("Bachchan");

		doReturn(true).when(formHandler).validateFields();

		// When
		formHandler.handleUpdateUserDetails();

		// Then
		verify(formHandler).log("Updated user user123");
		verify(logQueue).add("Form Handler: Updated user user123");
	}
	/*
	 * Note that verifying the log interaction on the formHandler is only
	 * possible because it is created as a spy
	 */
}
