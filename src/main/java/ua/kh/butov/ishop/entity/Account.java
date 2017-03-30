package ua.kh.butov.ishop.entity;

import ua.kh.butov.ishop.framework.annotation.jdbc.Column;
import ua.kh.butov.ishop.model.CurrentAccount;

public class Account extends AbstractEntity<Integer> implements CurrentAccount {
	private static final long serialVersionUID = -6889352515111174105L;

	private String name;
	private String email;
	@Column("avatar_url")
	private String avatarUrl;

	public Account() {
	}

	public Account(String name, String email, String avatarUrl) {
		this.name = name;
		this.email = email;
		this.avatarUrl = avatarUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	@Override
	public String toString() {
		return String.format("Account [id=%s, name=%s, email=%s]", getId(), name, email);
	}

	@Override
	public String getDescription() {
		return name + "(" + email + ")";
	}
}
