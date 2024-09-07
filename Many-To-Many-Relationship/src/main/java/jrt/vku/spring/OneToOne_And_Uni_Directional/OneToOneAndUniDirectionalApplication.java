package jrt.vku.spring.OneToOne_And_Uni_Directional;

import jrt.vku.spring.OneToOne_And_Uni_Directional.dao.CourseDao;
import jrt.vku.spring.OneToOne_And_Uni_Directional.dao.StudentDao;
import jrt.vku.spring.OneToOne_And_Uni_Directional.dao.TeacherDao;
import jrt.vku.spring.OneToOne_And_Uni_Directional.dao.TeacherDetailDao;
import jrt.vku.spring.OneToOne_And_Uni_Directional.entity.Course;
import jrt.vku.spring.OneToOne_And_Uni_Directional.entity.Student;
import jrt.vku.spring.OneToOne_And_Uni_Directional.entity.Teacher;
import jrt.vku.spring.OneToOne_And_Uni_Directional.entity.TeacherDetail;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class OneToOneAndUniDirectionalApplication {

	public static void main(String[] args) {
		SpringApplication.run(OneToOneAndUniDirectionalApplication.class, args);
	}
	
	
	@Bean
	public CommandLineRunner commandLineRunner(TeacherDao teacherDao,
	                                           TeacherDetailDao teacherDetailDao,
	                                           CourseDao courseDao,
	                                           StudentDao studentDao){
		return runner -> {
//			createTeacher(teacherDao);
//			findTeacherByID(teacherDao,1);
//			findTeacherDetailById(teacherDetailDao, 1);
//			createCourse(teacherDao, courseDao);
			// Tìm Teacher với khóa học tương ứng, bật FetchType Eager
//			findTeacherWithCourse_EAGER(teacherDao, 5);
			// Tìm Teacher với khóa học tương ứng, bật FetchType Lazy
//			findTeacherWithCourse_LAZY(teacherDao, courseDao, 5);
			// Tìm Teacher với Course tương ứng, LAZY + SQL(join fetch)
//			findTeacherWithCourse_LAZY_JoinFetch(teacherDao, courseDao, 5);
		
			createCourseAndStudent(courseDao, studentDao);
		};
	}
	
	private void createCourseAndStudent(CourseDao courseDao, StudentDao studentDao) {
		// Create course
		Course course = new Course();
		course.setTitle("Lập trình mạng");
		
		// Create student
		Student student1 = new Student();
		student1.setFirstName("Tung");
		student1.setLastName("Le");
		Student student2 = new Student();
		student2.setFirstName("Te");
		student2.setLastName("Lung");
		
		// Add student to course
		course.addStudent(student1);
		course.addStudent(student2);
		
		courseDao.save(course);
	}
	
	private void findTeacherWithCourse_LAZY_JoinFetch(TeacherDao teacherDao, CourseDao courseDao, int i) {
		Teacher teacher = teacherDao.findTeacherByIdJoinFetch(i);
		System.out.println("Teacher 's information:");
		System.out.println(teacher);
		
		System.out.println("List of course:");
		System.out.println(teacher.getCourses());
	}
	
	private void findTeacherWithCourse_LAZY(TeacherDao teacherDao, CourseDao courseDao, int i) {
		Teacher teacher = teacherDao.findByTeacherId(i);
		System.out.println("Teacher: " + teacher);
		
		// FetchType.Lazy, khi lấy 1 Teacher thì các Course liên quan không được
		// tải lên đồng thời, do đó, phải lấy Course thông qua 1 bước nữa
		// Select Course
		List<Course> courses = courseDao.findCourseByTeacherId(i);
		teacher.setCourses(courses);
		
		System.out.println("List Course:");
		System.out.println(teacher.getCourses());
	}
	
	private void findTeacherWithCourse_EAGER(TeacherDao teacherDao, int id) {
		Teacher teacher1 = teacherDao.findByTeacherId(id);
		System.out.println("Teacher1: " + teacher1);
		System.out.println("Course 1: ");
		System.out.println(teacher1.getCourses());
	}
	
	private void createCourse(TeacherDao teacherDao, CourseDao courseDao) {
		// Create a teacher
		Teacher teacher = new Teacher();
		teacher.setFirstName("Đông Nam");
		teacher.setLastName("Phạm");
		teacher.setEmail("phandongnam@gmail.com");
		
		// Create teacher detail
		TeacherDetail teacherDetail = new TeacherDetail();
		teacherDetail.setGender(true);
		teacherDetail.setAddress("Soviet");
		teacherDetail.setYoutubeChannel("@TOTV");
		
		teacher.setTeacherDetail(teacherDetail);
		
		// Create course
		Course course1 = new Course("Spring Hibernate", "An java course", null, null);
		Course course2 = new Course("Hibernate", "Spring Hibernate", null, null);
		
		teacher.addCourse(course1);
		teacher.addCourse(course2);
		
		System.out.println("Updating teacher ...");
		teacherDao.updateTeacher(teacher);
		System.out.println("Done");
	}
	
	private void findTeacherDetailById(TeacherDetailDao teacherDetailDao, int id) {
		TeacherDetail teacherDetail = teacherDetailDao.findByTeacherDetailId(id);
		System.out.println("Teacher detail: " + teacherDetail);
		System.out.println("Teacher: " + teacherDetail.getTeacher());
	}
	
	private void findTeacherByID(TeacherDao teacherDao, int id) {
		Teacher teacher = teacherDao.findByTeacherId(id);
		System.out.println("Teacher: " + teacher);
		System.out.println("TeacherDetail: " + teacher.getTeacherDetail());
	}
	
	private void createTeacher(TeacherDao teacherDao) {
		Teacher teacher = new Teacher();
		teacher.setFirstName("Nhat Tung");
		teacher.setLastName("Le");
		teacher.setEmail("lenhattung@gmail.com");
		
		TeacherDetail teacherDetail = new TeacherDetail();
		teacherDetail.setGender(true);
		teacherDetail.setAddress("Warsaw - Poland");
		teacherDetail.setYoutubeChannel("@TITV");
		
		teacher.setTeacherDetail(teacherDetail);
		
		// Notify about saving teacher
		System.out.println("saving teacher ..." + teacher);
		teacherDao.save(teacher);
		System.out.println("Done");
		
	}
}
