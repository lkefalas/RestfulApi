package org.labros.rest.Properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Property {

	public static String getMyProperty(String propertyName) {

		Properties prop = new Properties();
		String propertyValue = "";

		try {
				FileInputStream inputStream = new FileInputStream("dev.properties");
				prop.load(inputStream);
				propertyValue = prop.getProperty(propertyName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return propertyValue;
	}
}