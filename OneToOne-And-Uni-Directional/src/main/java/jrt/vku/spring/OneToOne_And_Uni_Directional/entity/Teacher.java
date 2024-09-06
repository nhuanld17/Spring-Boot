package jrt.vku.spring.OneToOne_And_Uni_Directional.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "teacher")
public class Teacher {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "first_name", length = 256)
	private String firstName;
	
	@Column(name = "last_name", length = 256)
	private String lastName;
	
	@Column(name = "email", length = 256)
	private String email;
	
	// Thiết lập quan hệ Unidirectional 1-1
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "teacher_detail_id")
	private TeacherDetail teacherDetail;
	
	@Transient
	private String x;
	
	// No parameter contructor
	public Teacher() {
	
	}
	
	public Teacher(String firstName, String lastName, String email, TeacherDetail teacherDetail) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.teacherDetail = teacherDetail;
	}
	
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
	
	public TeacherDetail getTeacherDetail() {
		return teacherDetail;
	}
	
	public void setTeacherDetail(TeacherDetail teacherDetail) {
		this.teacherDetail = teacherDetail;
	}
	
	@Override
	public String toString() {
		return "Teacher{" +
				"id=" + id +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", email='" + email + '\'' +
				", teacherDetail=" + teacherDetail +
				'}';
	}
}
