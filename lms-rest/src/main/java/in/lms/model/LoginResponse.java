package in.lms.model;

public class LoginResponse {

	private String cSessionId;
	private Boolean isUserGenerated;

	public String getcSessionId() {
		return cSessionId;
	}

	public void setcSessionId(String cSessionId) {
		this.cSessionId = cSessionId;
	}

	public Boolean getIsUserGenerated() {
		return isUserGenerated;
	}

	public void setIsUserGenerated(Boolean isUserGenerated) {
		this.isUserGenerated = isUserGenerated;
	}

	@Override
	public String toString() {
		return "LoginResponse [cSessionId=" + cSessionId + "]";
	}

}
