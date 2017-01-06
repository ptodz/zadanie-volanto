package pl.volanto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.volanto.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
