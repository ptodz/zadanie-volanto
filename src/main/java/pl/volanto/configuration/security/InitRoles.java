package pl.volanto.configuration.security;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import pl.volanto.entity.Role;
import pl.volanto.entity.User;
import pl.volanto.repository.RoleRepository;
import pl.volanto.repository.UserRepository;

@Component
public class InitRoles {

	private static final Logger LOGGER = LoggerFactory.getLogger(InitRoles.class);

	private final String ADMIN_ROLE = "ADMIN";
	private final String ADMIN_LOGIN = "admin";
	private final String ADMIN_PASS = "volanto@";

	private final String USER_LOGIN = "user";
	private final String USER_PASS = "user";
	private final String USER_ROLE = "USER";

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	@PostConstruct
	public void ensure() {

		Role roleAdmin = roleRepository.findByName(ADMIN_ROLE);
		if (roleAdmin == null) {
			roleAdmin = new Role();
			roleAdmin.setName(ADMIN_ROLE);
			roleRepository.save(roleAdmin);

		}

		Role roleUser = roleRepository.findByName(USER_ROLE);
		if (roleUser == null) {
			roleUser = new Role();
			roleUser.setName(USER_ROLE);
			roleRepository.save(roleUser);
		}

		List<User> users = userRepository.findUsersByRole(roleAdmin);
		if (users.isEmpty()) {
			User newUser = new User(ADMIN_LOGIN, encoder.encode(ADMIN_PASS), roleAdmin);
			userRepository.save(newUser);
		}
		
		List<User> anotherUsers = userRepository.findUsersByRole(roleUser);
		if (anotherUsers.isEmpty()) {
			User newUser = new User(USER_LOGIN, encoder.encode(USER_PASS), roleUser);
			userRepository.save(newUser);
		}

		LOGGER.info("Accounts Ok.");

	}


}
