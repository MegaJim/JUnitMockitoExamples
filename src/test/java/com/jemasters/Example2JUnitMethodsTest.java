package com.jemasters;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Example2JUnitMethodsTest {

	/*
	 * This test should demonstrate the order of execution for the JUnit
	 * methods.
	 * 
	 * Note that @BeforeClass and @AfterClass are static methods
	 * 
	 * @BeforeClass and @AfterClass are not normally necessary but in some
	 * circumstances can be very useful, e.g. ensuring that some state exists in
	 * the system prior to running the tests, and then setting the state back to
	 * normal afterwards
	 * 
	 * When this test runs you should see something like:
	 */
	/*@formatter:off
	 * 
	 * Running @BeforeClass
	 * Running @Before
	 * Method invoked with: Running test1
	 * Running @After
	 * Running @Before
	 * Method invoked with: Running test2
	 * Running @After
	 * Running @Before
	 * Method invoked with: Running test3
	 * Running @After
	 * Running @AfterClass
	 * 
	 @formatter:on*/
	
	private Example2JUnitMethods classUnderTest;

	@Before
	public void setUp() throws Exception {
		classUnderTest = new Example2JUnitMethods();
		
		System.out.println("Running @Before");
	}

	@After
	public void teardown() throws Exception {
		System.out.println("Running @After");
	}

	@BeforeClass
	public static void classSetup() {
		System.out.println("Running @BeforeClass");
	}

	@AfterClass
	public static void classTeardown() throws Exception {
		System.out.println("Running @AfterClass");
	}

	@Test
	public void test1() throws Exception {
		classUnderTest.method("Running test1");
	}

	@Test
	public void test2() throws Exception {
		classUnderTest.method("Running test2");
	}

	@Test
	public void test3() throws Exception {
		classUnderTest.method("Running test3");
	}
}
