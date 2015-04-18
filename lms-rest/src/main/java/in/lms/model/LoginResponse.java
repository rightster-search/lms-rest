package in.lms.model;

public class LoginResponse {

	private String cSessionId;
	private Boolean isUserGenerated;
	private String uid;

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

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	@Override
	public String toString() {
		return "LoginResponse [cSessionId=" + cSessionId + ", isUserGenerated="
				+ isUserGenerated + ", uid=" + uid + "]";
	}

}
