package com.jemasters.untestable;

import java.net.URL;
import java.net.URLConnection;

public class LegacyUserValidator {

	public static boolean validateFirstName(String firstName) {
		try {
			URL validationEndpoint = new URL(
					"http://validation.tesco.com/userValidate/?firstName=" + firstName);
			URLConnection connection = validationEndpoint.openConnection();
			return connection.getInputStream().read() == 1 ? true : false;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public static boolean validateLastName(String lastName) {
		try {
			URL validationEndpoint = new URL(
					"http://validation.tesco.com/userValidate/?lastName=" + lastName);
			URLConnection connection = validationEndpoint.openConnection();
			return connection.getInputStream().read() == 1 ? true : false;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

}
