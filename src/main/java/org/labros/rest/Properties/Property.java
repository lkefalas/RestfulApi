package org.labros.rest.Properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Property implements IProperty {

	public static String getMyProperty(String propertyName) throws IOException {
		Properties prop = new Properties();
		String propertyValue = "";
		FileInputStream inputStream = new FileInputStream("dev.properties");
		prop.load(inputStream);
		propertyValue = prop.getProperty(propertyName);

		return propertyValue;
	}
}