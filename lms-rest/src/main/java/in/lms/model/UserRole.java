package in.lms.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class UserRole implements Serializable {

	private static final long serialVersionUID = 1L;
	private String username;
	private String role;

	public UserRole() {

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public int hashCode() {
		int hashCode = 0;
		if (username != null)
			hashCode ^= username.hashCode();
		if (role != null)
			hashCode ^= role.hashCode();

		return hashCode;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof UserRole))
			return false;
		UserRole target = (UserRole) obj;
		String role2 = target.getRole();
		String username2 = target.getUsername();

		if (role2 != null && username2 != null) {
			return role2.equals(role) ? username2.equals(username) : false;
		} else if (role2 == null) {
			if (username2 != null) {
				return (role == null) ? username2.equals(username) : false;
			} else {
				return (role == null) ? (username == null) : false;
			}
		} else {
			return (username == null) ? role2.equals(role) : false;
		}

	}

	@Override
	public String toString() {
		return "UserAndRole [username=" + username + ", role=" + role + "]";
	}

}
