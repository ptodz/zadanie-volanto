package pl.volanto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.volanto.entity.Role;
import pl.volanto.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	List<User> findUsersByRole(Role role);
	
	User findByLogin(String login);
}
