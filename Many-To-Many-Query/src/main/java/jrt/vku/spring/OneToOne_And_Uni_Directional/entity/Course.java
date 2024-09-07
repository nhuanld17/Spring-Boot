package jrt.vku.spring.OneToOne_And_Uni_Directional.entity;


import jakarta.persistence.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "course")
public class Course {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "start_date")
	private Date startDate;
	
	@Column(name = "end_date")
	private Date endDate;
	
	// Teacher-1 - N-Course: Khóa chính bên 1 thêm vào bên N như khóa ngoại
	@ManyToOne(cascade = {
			CascadeType.DETACH,
			CascadeType.MERGE,
			CascadeType.REFRESH,
			CascadeType.PERSIST
	})
	@JoinColumn(name = "teacher_id")
	private Teacher teacher;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = {
			CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REFRESH, CascadeType.DETACH
	})
	@JoinTable(
			name = "course_student",
			joinColumns = @JoinColumn(name = "course_id"),
			inverseJoinColumns = @JoinColumn(name = "student_id")
	)
	private List<Student> students;
	
	// No arg constructor
	public Course() {
	}
	
	public Course(String title, String description, Date startDate, Date endDate) {
		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public List<Student> getStudents() {
		return students;
	}
	
	public void setStudents(List<Student> students) {
		this.students = students;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Date getEndDate() {
		return endDate;
	}
	
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public Teacher getTeacher() {
		return teacher;
	}
	
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	
	@Override
	public String toString() {
		return "Course{" +
				"id=" + id +
				", title='" + title + '\'' +
				", description='" + description + '\'' +
				", startDate=" + startDate +
				", endDate=" + endDate +
				'}';
	}
	
	public void addStudent(Student student) {
		if (students == null) {
			students = new ArrayList<>();
		}
		
		students.add(student);
	}
}
