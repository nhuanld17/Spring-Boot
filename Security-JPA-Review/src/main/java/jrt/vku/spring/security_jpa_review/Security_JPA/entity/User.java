package jrt.vku.spring.security_jpa_review.Security_JPA.entity;

import jakarta.persistence.*;

import java.sql.Blob;
import java.util.Collection;

@Entity
@Table(name = "users")
public class User {
	@Id // Chỉ định khóa chính
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
	private String firstname;
	
	@Column(name = "lastname")
	private String lastname;
	
	@Column(name = "email")
	private String email;
	
	@Lob // Chỉ định kiểu dữ liệu lớn (Large Object) cho avatar
	@Column(name = "avatar")
	private Blob avatar;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(
		name ="users_roles",
		joinColumns =@JoinColumn(name = "user_id"),
		inverseJoinColumns =@JoinColumn(name = "role_id")
	)
	private Collection<Role> roles;
	
	public User() {
	
	}
	
	public User(Long id, String username, String password, String firstname, String lastname, String email, boolean enabled, Collection<Role> roles, Blob avatar) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.enabled = enabled;
		this.roles = roles;
		this.avatar = avatar;
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
	
	public String getFirstname() {
		return firstname;
	}
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getLastname() {
		return lastname;
	}
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
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
				", firstname='" + firstname + '\'' +
				", lastname='" + lastname + '\'' +
				", email='" + email + '\'' +
				", roles=" + roles +
				'}';
	}
}
