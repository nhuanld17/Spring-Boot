package jrt.vku.spring.CRUD_API_DEMO.dao;

import jrt.vku.spring.CRUD_API_DEMO.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {
	
	// Truy vấn mở rộng với @Query và JPQL
	@Query("SELECT s FROM Student s WHERE s.lastName = :name")
	public List<Student> findByLastName(@Param("name") String name);
	
	// Tìm sinh viên theo firstNamw
	public List<Student> findByFirstName(String firstName);
	
	// Tìm sinh viên theo firstName và lastName
	public List<Student> findByFirstNameAndLastName(String firstName, String lastName);
	
	// Truy vấn các sinh viên có firstName khác với giá trị tìm kiếm
	public List<Student> findByFirstNameNot(String firstName);
}
