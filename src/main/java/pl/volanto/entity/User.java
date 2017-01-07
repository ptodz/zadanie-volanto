package pl.volanto.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue
	private Long id;
	
	private String login;
	
	private String password;
	
	@ManyToOne
	Role role;
	
	@OneToMany(cascade = {CascadeType.ALL})
	List<Contact> contacts = new ArrayList<>();

	public User() {
		
	}
	
	public User(String login, String password, Role role) {
		this.login = login;
		this.password = password;
		this.role = role;
	}
	
	public User(Long id, String login, String password, List<Contact> contacts) {
		super();
		this.id = id;
		this.login = login;
		this.password = password;
		this.contacts = contacts;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
