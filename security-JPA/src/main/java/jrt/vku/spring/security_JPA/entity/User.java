package jrt.vku.spring.security_JPA.entity;

import jakarta.persistence.*;

import java.sql.Blob;
import java.util.Collection;

@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "password", length = 256)
	private String password;
	
	@Column(name = "enabled")
	private boolean enabled;
	
	@Column(name = "firstname")
	private String firstName;
	
	@Column(name = "lastname")
	private String lastName;
	
	@Column(name = "email")
	private String email;
	
	@Lob
	@Column(name = "avatar")
	private Blob avatar;
	
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(
			name = "users_roles",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id")
	)
	private Collection<Role> roles;
	
	public User() {
	}
	
	public User(Blob avatar, String email, boolean enabled, String firstName, Long id, String lastName, String password, Collection<Role> roles, String username) {
		this.avatar = avatar;
		this.email = email;
		this.enabled = enabled;
		this.firstName = firstName;
		this.id = id;
		this.lastName = lastName;
		this.password = password;
		this.roles = roles;
		this.username = username;
	}
	
	public Blob getAvatar() {
		return avatar;
	}
	
	public void setAvatar(Blob avatar) {
		this.avatar = avatar;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Collection<Role> getRoles() {
		return roles;
	}
	
	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	@Override
	public String toString() {
		return "User{" +
				"avatar=" + avatar +
				", id=" + id +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", enabled=" + enabled +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", email='" + email + '\'' +
				", roles=" + roles +
				'}';
	}
}
