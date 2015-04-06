package in.lms.model;

public class GenericResponse {

	private String response;// possible values : success, failure,
							// internalException
	private String errorMsg;// if response value is failure , it will hold the
							// reason.

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
