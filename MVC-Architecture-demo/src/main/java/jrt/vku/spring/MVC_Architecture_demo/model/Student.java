package jrt.vku.spring.MVC_Architecture_demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", length = 11)
	private int id;
	
	@Column(name = "first_name", length = 45)
	private String firstName;
	
	@Column(name = "last_name", length = 45)
	private String lastName;
	
	@Column(name = "email", length = 45)
	private String email;
	
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
	
	// GET AND SET
	
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
