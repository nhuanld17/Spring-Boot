package jrt.vku.spring.MVC_CRUD_DEMO.repository;

import jrt.vku.spring.MVC_CRUD_DEMO.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
	// Phương thức tìm kiếm tất cả các user với phân trang
	@Override
	Page<Student> findAll(Pageable pageable);
}
