package org.labros.rest.Utilities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtility {

	public static String getCurrentDateTime()
	{
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}
}
