package jrt.vku.spring.OneToOne_And_Uni_Directional.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "student")
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "fisrt_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@ManyToMany(fetch = FetchType.LAZY,
			cascade = {
				CascadeType.PERSIST, CascadeType.MERGE,
				CascadeType.DETACH, CascadeType.REFRESH
			},
			mappedBy = "students"
	)
	private List<Course> courses;
	
	public Student() {
	}
	
	public Student(int id, String firstName, String lastName, List<Course> courses) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.courses = courses;
	}
	
	public List<Course> getCourses() {
		return courses;
	}
	
	public void setCourses(List<Course> courses) {
		this.courses = courses;
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
	
	@Override
	public String toString() {
		return "Student{" +
				"id=" + id +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				'}';
	}
}
