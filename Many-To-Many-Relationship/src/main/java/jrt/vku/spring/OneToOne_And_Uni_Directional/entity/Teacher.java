package jrt.vku.spring.OneToOne_And_Uni_Directional.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

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
	// bảng teacher_detail chứa khóa ngoại tham chiếu đến bảng teacher_detail
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "teacher_detail_id")
	private TeacherDetail teacherDetail;
	
	@Transient
	private String x;
	
	@OneToMany(mappedBy = "teacher",
			// Lấy 1 Teacher -> Course tương ứng cũng được tải lên đồng thời
//			fetch = FetchType.EAGER,
			// Lấy 1 Teacher -> Course và TeacherDetail không được tải lên
			fetch = FetchType.LAZY,
			cascade = {
			CascadeType.DETACH,
			CascadeType.MERGE,
			CascadeType.PERSIST,
			CascadeType.REFRESH
	})
	private List<Course> courses;
	
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
	
	public List<Course> getCourses() {
		return courses;
	}
	
	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}
	
	@Override
	public String toString() {
		return "Teacher{" +
				"id=" + id +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", email='" + email + '\'' +
				'}';
	}
	
	public void addCourse(Course course){
		if(courses == null){
			courses = new ArrayList<>();
		}
		this.courses.add(course);
		course.setTeacher(this);
	}
}
