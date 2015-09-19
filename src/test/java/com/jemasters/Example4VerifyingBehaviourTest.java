package com.jemasters;

import static com.jemasters.Example3IntroductionToMockitoTest.date;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.jemasters.interfaces.DataGrid;

public class Example4VerifyingBehaviourTest {

	/*
	 * The other powerful feature of Mockito is that it can record all
	 * interactions with Mocked objects.
	 * 
	 * This means you can verify that 'something' happens in your code when it's
	 * important to do so, even when that 'something' doesn't return a result
	 * for you to check from the test, common examples of this are: 1. Telling a
	 * database to store something 2. Putting a message on a queue 3. Properly
	 * initialising a webservice and so on...
	 * 
	 * Conversely, you can make sure that 'something' never happens
	 * 
	 */

	private Example4VerifyingBehaviour classUnderTest;

	//@formatter:off
	@Mock Example3IntroductionToMockito ageCalculator;
	@Mock DataGrid dataGrid;
	//@formatter:on

	@Before
	public void setUp() throws Exception {
		initMocks(this);

		classUnderTest = new Example4VerifyingBehaviour();
		classUnderTest.setAgeCalculator(ageCalculator);
		classUnderTest.setDataGrid(dataGrid);
	}

	@Test
	public void shouldSaveAgeToDataGrid() throws Exception {
		// Given
		Date birthday = date.parse("16-02-1990");
		when(ageCalculator.calculateMyAge(birthday)).thenReturn(18L);

		// When
		classUnderTest.calculateAgeAndStore("James", birthday);

		// Then
		verify(dataGrid).store("James:birthday", 18L);
	}
	/*
	 * Using verify(mock).methodInvocation(arguments) we can ensure that our
	 * code does not one, but two things: 1. It correctly retrieves the age of
	 * James from the age calculator (there's no other way it can know this) 2.
	 * It stores this age into the data grid under the right key
	 */

	@Test
	public void shouldNotStoreAnAgeIfSomethingGoesWrong() throws Exception {
		// Given
		Date birthday = date.parse("16-02-1990");
		when(ageCalculator.calculateMyAge(any(Date.class)))
				.thenThrow(new RuntimeException("ServiceUnavailable"));

		// When
		classUnderTest.calculateAgeAndStore("James", birthday);

		// Then
		verify(dataGrid, never()).store(anyString(), anyObject());
		verifyZeroInteractions(dataGrid);
	}
	/*
	 * This test introduces two more ways of using Matchers and Verfication in
	 * Mockito
	 * 
	 * In the call to when() we're using the wildcard matcher any(Date.class) to
	 * indicate that whatever date is passed in, an exception should be thrown
	 * 
	 * In the call to verify() we're using the never() verification mode to
	 * indicate that the store method (again with wildcard matchers) is never
	 * invoked, and finally that there are in fact no interactions with the
	 * dataGrid at all !
	 * 
	 * Try uncommenting the code in the calculateAgeAndStore method's catch
	 * block to prove that this test works. You'll get a test result that tells
	 * you exactly where the code you didn't want called was invoked from :)
	 */
	/* @formatter:off
	 * org.mockito.exceptions.verification.NeverWantedButInvoked: 
	 * 		dataGrid.store(<any>, <any>);
	 * 	Never wanted here:
	 * 		-> at com.jemasters.Example4VerifyingBehaviourTest.shouldNotStoreAnAgeIfSomethingGoesWrong(Example4VerifyingBehaviourTest.java:83)
	 * 	But invoked here:
	 * 		-> at com.jemasters.Example4VerifyingBehaviour.calculateAgeAndStore(Example4VerifyingBehaviour.java:21)
	 * 
	 * @formatter:on
	 */
}
