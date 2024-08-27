package jrt.vku.spring.CRUD_API_DEMO.enity;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {
	@Id
	@Column(name = "id", length = 11)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "last_name", length = 45, nullable = true)
	private String lastName;
	
	@Column(name = "first_name", length = 45, nullable = true)
	private String firstName;
	
	@Column(name = "email", length = 45, nullable = true)
	private String email;
	
	// Constructor rỗng bắt buộc
	public Student() {
	}
	
	public Student(String firstName, String lastName, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}
	
	public Student(int id, String firstName, String lastName, String email) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}
	
	// Get và Set
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	
}
