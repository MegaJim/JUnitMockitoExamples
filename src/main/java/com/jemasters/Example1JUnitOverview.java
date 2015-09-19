package com.jemasters;

public class Example1JUnitOverview {

	public String hello(String name) {
		if (name != null && name.length() >= 30)
			throw new RuntimeException("Sorry, I don't know how to say that!");

		return "Hello, " + name + "!";
	}
}
