package com.jemasters;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.jemasters.interfaces.DateProvider;

public class Example3IntroductionToMockitoTest {

	/*
	 * This test introduces Mockito
	 * 
	 * Mockito is a powerful unit testing library for creating, stubbing and
	 * verifying behaviour with mock objects
	 * 
	 * Why do I need mocks?
	 * 
	 * Take a look at the class under test. It has an injected property of type
	 * DateProvider, however whatever framework exists to do this work is not
	 * available to us right now. This could be for a number of reasons: 1. No
	 * network connectivity (if it's a webservice) 2. Starting up the framework
	 * would take too long (and unit tests must be fast) 3. We don't have access
	 * to the framework at all (like when writing a plugin) 4. Perhaps we have
	 * access to the framework and it is fast, but it will return the actual
	 * time when the test is run, so the unit test will only work at a
	 * particular time of the day :)
	 * 
	 * Mockito allows us to 'not care' about the dependencies of a class, or
	 * rather, allows us to specify their behaviour for each given test scenario
	 * 
	 * This allows us to concentrate on testing the logic in our class under
	 * test in isolation, without worrying about anything else
	 * 
	 */

	public static SimpleDateFormat date = new SimpleDateFormat("mm-DD-yyyy");

	private Example3IntroductionToMockito classUnderTest;

	/*
	 * The @Mock annotation is used to indicate this object is a mock. I like to
	 * keep the Annotation on the same line as the mock for readability, so I
	 * use the formatter:on/off tags around these declarations so that
	 * Ctrl+Shift+F doesn't put it on the line above
	 */
	//@formatter:off
	@Mock DateProvider mockDateProvider;
	//@formatter:on

	/*
	 * The call to initMocks(this) allows Mockito to inject all annotated mocks
	 * into this test
	 */
	@Before
	public void setUp() throws Exception {
		initMocks(this);

		classUnderTest = new Example3IntroductionToMockito();

		/*
		 * This line sets the mock DateProvider on our class under test.
		 * Essentially we are doing our own dependency injection
		 */
		classUnderTest.setDateProvider(mockDateProvider);
	}

	@Test
	public void shouldKnowIAmAlwaysEighteenYearsOld() throws Exception {
		// Given
		Date birthday = date.parse("16-02-1990");
		when(mockDateProvider.todaysDate()).thenReturn(date.parse("31-04-2008"));

		// When
		Long myAge = classUnderTest.calculateMyAge(birthday);

		// Then
		assertThat(myAge, is(18L));

		// When
		myAge = classUnderTest.calculateMyAge(birthday);

		// Then
		assertThat(myAge, is(18L));
	}
	/*
	 * What just happened?
	 * 
	 * We used when(methodInvocation).thenReturn(stubbedValue) to tell Mockito
	 * that whenever the mock DateProvider's todaysDate() method is called, it
	 * should return a date of 31-04-2008
	 * 
	 * You can debug the unit test to see how it works by setting a breakpoint
	 * inside, highlighting the test name, right click, Debug As -> JUnit Test
	 * or use the keyboard shortcut Shift+Alt+D, T
	 * 
	 * Note that because Mockito makes use of Generics we also have compile-time
	 * type checking of the return type of thenReturn() for the method we are
	 * stubbing
	 */

	@Test
	public void shouldKnowHowIAge() throws Exception {
		// Given
		Date birthday = date.parse("16-02-1990");
		when(mockDateProvider.todaysDate()).thenReturn(date.parse("31-04-2008"))
				.thenReturn(date.parse("31-04-2018")).thenReturn(date.parse("31-04-2028"));

		// Then
		assertThat(classUnderTest.calculateMyAge(birthday), is(18L));
		assertThat(classUnderTest.calculateMyAge(birthday), is(28L));
		assertThat(classUnderTest.calculateMyAge(birthday), is(38L));
	}
	/*
	 * In this test, we've used another nice syntactic sugar API of Mockito
	 * called method chaining to create multiple stubbed return values. The
	 * stubbed values are returned in the same order they are created
	 */

	@Test(expected = RuntimeException.class)
	public void shouldThrowExceptionWhenServiceUnavailable() throws Exception {
		// Given
		Date birthday = date.parse("16-02-1990");
		when(mockDateProvider.todaysDate()).thenReturn(date.parse("31-04-2008"))
				.thenReturn(date.parse("31-04-2018")).thenReturn(date.parse("31-04-2028"))
				.thenThrow(new RuntimeException("ServiceUnavailable"));

		// Then
		assertThat(classUnderTest.calculateMyAge(birthday), is(18L));
		assertThat(classUnderTest.calculateMyAge(birthday), is(28L));
		assertThat(classUnderTest.calculateMyAge(birthday), is(38L));
		classUnderTest.calculateMyAge(birthday);
	}
	/*
	 * You can also expect an exception to be thrown by your mock by using
	 * .thenThrow as above, allowing testing of predictable failures to make
	 * sure your code behaves correctly, or implement and test resiliency
	 * features without having to 'break' things at runtime
	 */

}
