package ua.kh.butov.ishop.entity;

public class Account extends AbstractEntity<Integer> {
	private static final long serialVersionUID = -6889352515111174105L;
	
	private String name;
	private String email;

	public Account() {
		super();
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

	@Override
	public String toString() {
		return String.format("Account [id=%s, name=%s, email=%s]", getId(), name, email);
	}
}
