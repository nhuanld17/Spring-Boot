package jrt.vku.spring.security_JPA.dao;

import jrt.vku.spring.security_JPA.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	public Role findByName(String name);
}
