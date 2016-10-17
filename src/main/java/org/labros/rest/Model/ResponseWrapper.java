package org.labros.rest.Model;

import org.labros.rest.Utilities.DateUtility;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseWrapper {

	String respStatus;
	String errorMessage;
	String timestamp;

	public String getRespStatus() {
		return respStatus;
	}

	public void setRespStatus(String respStatus) {
		this.respStatus = respStatus;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	private String codeToErrorMsg(String respStatus) {
		switch(respStatus)
		{
			case "503":
				return "Internal Server Error";
			case "404":
				return "Not Found";
			case "200":
				return "Success";
			default:
				return "Not implemented";
		}
	}

	public ResponseWrapper(String respStatus)
	{
		this.respStatus = codeToErrorMsg(respStatus);
		timestamp = DateUtility.getCurrentDateTime();
	}

	public ResponseWrapper(String respStatus, String errorMessage)
	{
		this.respStatus = codeToErrorMsg(respStatus);
		this.errorMessage = errorMessage;
		timestamp = DateUtility.getCurrentDateTime();
	}
}