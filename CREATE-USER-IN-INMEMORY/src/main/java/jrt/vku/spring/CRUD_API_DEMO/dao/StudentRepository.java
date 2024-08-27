package jrt.vku.spring.CRUD_API_DEMO.dao;

import jrt.vku.spring.CRUD_API_DEMO.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

}
