package com.jemasters;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class Example1JUnitOverviewTest {

	/*
	 * This test is an introduction to JUnit
	 * 
	 * Tests should have the same name as the class they are testing with the
	 * addition of the word Test at the end and be created under the same
	 * package structure under the test/ folder.
	 * 
	 * NOTE: While there are lots of comments in this test to explain JUnit,
	 * writing comments is generally a sign that your code is hard to read !
	 * 
	 * Only use comments where something is unusual, unexpected, or where
	 * another developer wouldn't normally be expected to understand something
	 * else that is going on. Ideally if you find yourself having to write lots
	 * of comments to explain how something works, you should refactor it so
	 * that comments aren't necessary
	 */

	// This is the class under test, only test one class per JUnit file
	private Example1JUnitOverview classUnderTest;

	/*
	 * @Before runs once before each test method, so put re-usable setup here.
	 * You can call this method whatever you want but setUp make sense so that's
	 * what I use
	 */
	@Before
	public void setUp() throws Exception {
		classUnderTest = new Example1JUnitOverview();
	}

	/*
	 * Tests are annotated with @Test and run one after another, although the
	 * order is not guaranteed, so don't rely on tests running in a specific
	 * order - each test must be independently runnable. You can run an
	 * individual test in a file by highlighting the method and right clicking
	 * then choose Run As -> JUnit Test. You can also use the shortcut
	 * Shift+Alt+X, T
	 */
	@Test
	public void shouldSayHelloToJames() throws Exception {
		// Given
		String name = "James";

		// When
		String hello = classUnderTest.hello(name);

		// Then
		assertThat(hello, is("Hello, James!"));
	}
	/*
	 * 'Assert that' compares the actual result (hello variable) with the
	 * expectation (is Hello, James!). 'is' is a Matcher, which just compares
	 * for equality. Mockito and Hamcrest provide many types of Matcher to make
	 * your test expectations easy for everyone to understand. This style of
	 * using english language terms is called Syntactic Sugar
	 */

	/*
	 * Copying and pasting tests (unlike with production code) is actually fine
	 * :) Each test should be self-descriptive enough to be read independently
	 * of the others...
	 * 
	 * This test is ignored so will not be run by JUnit. Try removing
	 * the @Ignore and running tests again
	 */
	@Ignore
	@Test
	public void shouldSayHelloToAnonymous() throws Exception {
		// Given
		String name = null;

		// When
		String hello = classUnderTest.hello(name);

		// Then
		assertThat(hello, is("Hello, Anonymous!"));
	}
	/*
	 * This test fails, can you make it pass by implementing some new logic in
	 * the hello method?
	 * 
	 * java.lang.AssertionError: Expected: is "Hello, Anonymous!" but: was
	 * "Hello, null!"
	 * 
	 * The important part is that the other tests should still work :)
	 * 
	 * This is the basic strength of JUnit. Changes can be made and all
	 * scenarios verified.
	 */

	/*
	 * The Given/When/Then form is from the BDD school of thought. It's not
	 * necessary to use it all the time, especially when tests are simple to
	 * understand already, but starting with Given/When/Then can make it easier
	 * to think about what you are trying to achieve with a test, and to get you
	 * started quickly if your mind is blank (this happens to me a lot!)
	 */
	@Test
	public void shouldSayHelloToEveryone() throws Exception {
		assertThat(classUnderTest.hello("Emma"), is("Hello, Emma!"));
		assertThat(classUnderTest.hello("Peter"), is("Hello, Peter!"));
		assertThat(classUnderTest.hello("John"), is("Hello, John!"));
		assertThat(classUnderTest.hello("Paul"), is("Hello, Paul!"));
		assertThat(classUnderTest.hello("Lucy"), is("Hello, Lucy!"));
		assertThat(classUnderTest.hello("Anna"), is("Hello, Anna!"));
	}

	/*
	 * You can expect an exception as the outcome of a test by using the
	 * (expected=ExceptionClass.class) parameter of the @Test annotation
	 */
	@Test(expected = RuntimeException.class)
	public void shouldThrowExceptionIfNameIsTooLong() throws Exception {
		// When
		classUnderTest.hello("123456789-123456789-123456789-");
	}

}
