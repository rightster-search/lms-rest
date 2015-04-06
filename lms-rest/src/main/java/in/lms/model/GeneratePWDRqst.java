package in.lms.model;

public class GeneratePWDRqst {

	private String emailId;
	private String userRole; // possible values -- admin,student,trainers
	private String authenticationLink;

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getAuthenticationLink() {
		return authenticationLink;
	}

	public void setAuthenticationLink(String authenticationLink) {
		this.authenticationLink = authenticationLink;
	}

	@Override
	public String toString() {
		return "GeneratePWDRqst [emailId=" + emailId + ", userRole=" + userRole
				+ ", authenticationLink=" + authenticationLink + "]";
	}

}
