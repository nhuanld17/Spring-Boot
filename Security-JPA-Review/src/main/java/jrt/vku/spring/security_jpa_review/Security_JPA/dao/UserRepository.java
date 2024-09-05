package jrt.vku.spring.security_jpa_review.Security_JPA.dao;

import jrt.vku.spring.security_jpa_review.Security_JPA.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	public User findByUsername(String username);
}
