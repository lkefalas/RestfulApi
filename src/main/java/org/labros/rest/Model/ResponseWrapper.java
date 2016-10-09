package org.labros.rest.Model;

import java.sql.Timestamp;
import java.util.Date;

public class ResponseWrapper {

	String respStatus;
	String errorMessage;
	Timestamp timestamp;

	public ResponseWrapper(String respStatus)
	{
		this.respStatus = respStatus;
		timestamp = new Timestamp(
							new Date().getTime()
						);
	}

	public ResponseWrapper(String respStatus, String errorMessage)
	{
		this.respStatus = respStatus;
		this.errorMessage = errorMessage;
		timestamp = new Timestamp(
							new Date().getTime()
						);
	}
}
