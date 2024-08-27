package jrt.vku.spring.MVC_Architecture_demo.repository;


import jrt.vku.spring.MVC_Architecture_demo.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

}
