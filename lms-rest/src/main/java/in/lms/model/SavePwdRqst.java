package in.lms.model;

public class SavePwdRqst {

	// private String role;
	private String uid;
	private String oldPwd;
	private String newPWd;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getOldPwd() {
		return oldPwd;
	}

	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}

	public String getNewPWd() {
		return newPWd;
	}

	public void setNewPWd(String newPWd) {
		this.newPWd = newPWd;
	}
	
	@Override
	public String toString() {
		return "SavePwdRqst [uid=" + uid + ", oldPwd=" + oldPwd + ", newPWd="
				+ newPWd + "]";
	}

}
