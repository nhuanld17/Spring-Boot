package jrt.vku.spring.CRUD_API_DEMO.dao;

import jrt.vku.spring.CRUD_API_DEMO.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface StudentRepository extends JpaRepository<Student, Integer> {
	@Query("SELECT s FROM Student s WHERE s.lastName = :name")
	public List<Student> findByLastName(@Param("name") String name);
}
