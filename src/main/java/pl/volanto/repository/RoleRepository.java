package pl.volanto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.volanto.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	
	public Role findByName(String name);
	
}
