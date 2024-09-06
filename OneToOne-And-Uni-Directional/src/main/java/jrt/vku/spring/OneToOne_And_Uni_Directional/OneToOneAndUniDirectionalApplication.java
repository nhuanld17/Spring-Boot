package jrt.vku.spring.OneToOne_And_Uni_Directional;

import jrt.vku.spring.OneToOne_And_Uni_Directional.dao.TeacherDao;
import jrt.vku.spring.OneToOne_And_Uni_Directional.dao.TeacherDaoImpl;
import jrt.vku.spring.OneToOne_And_Uni_Directional.entity.Teacher;
import jrt.vku.spring.OneToOne_And_Uni_Directional.entity.TeacherDetail;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OneToOneAndUniDirectionalApplication {

	public static void main(String[] args) {
		SpringApplication.run(OneToOneAndUniDirectionalApplication.class, args);
	}
	
	
	@Bean
	public CommandLineRunner commandLineRunner(TeacherDao teacherDao, TeacherDaoImpl teacherDaoImpl) {
		return runner -> {
			createTeacher(teacherDao);
		};
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
