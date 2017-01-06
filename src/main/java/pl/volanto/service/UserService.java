package pl.volanto.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ma.glasnost.orika.MapperFacade;
import pl.volanto.dto.UserDTO;
import pl.volanto.entity.Contact;
import pl.volanto.entity.User;
import pl.volanto.repository.UserRepository;


@Service
@Transactional
public class UserService {
	
	private final Logger log = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MapperFacade mapper;
	
	@Transactional
	public UserDTO findOne(String id) {
		log.debug("Request to get User: {}", id);
		return mapper.map(userRepository.findOne(Long.valueOf(id)), UserDTO.class);
	}
	
	@Transactional
	public List<UserDTO> findAll() {
		log.debug("Request to get all Users");
		return mapper.mapAsList(
				userRepository.findAll(), 
				UserDTO.class);
	}
	
	@Transactional
	public UserDTO addUser(UserDTO userDto) {
		log.debug("Request to add User");
		User u = new User();
		u.setLogin(userDto.getLogin());
		u.setPassword(userDto.getPassword());
		u.setContacts(mapper.mapAsList(userDto.getContacts(), Contact.class));
		userRepository.save(u);
		return mapper.map(u, UserDTO.class);
	}
	
	@Transactional
	public void updateUser(UserDTO userDto, String id) {
		log.debug("Request to update User: {}", id);
		User updatedUser = userRepository.findOne(Long.valueOf(id));
		updatedUser.setLogin(userDto.getLogin());
		updatedUser.setPassword(userDto.getPassword());
		updatedUser.setContacts(mapper.mapAsList(userDto.getContacts(), Contact.class));
	}
	
	@Transactional
	public void deleteUser(String id) {
		log.debug("Request to delete User: {}", id);
		userRepository.delete(Long.valueOf(id));
	}
	
}

