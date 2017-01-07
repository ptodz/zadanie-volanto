package pl.volanto.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ma.glasnost.orika.MapperFacade;
import pl.volanto.dto.ContactDTO;
import pl.volanto.dto.UserDTO;
import pl.volanto.entity.Contact;
import pl.volanto.entity.User;
import pl.volanto.repository.ContactRepository;
import pl.volanto.repository.UserRepository;
import pl.volanto.util.RestPreconditions;


@Service
@Transactional
public class UserService {
	
	private final Logger log = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ContactRepository contactRepository;
	
	@Autowired
	private MapperFacade mapper;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Transactional
	public UserDTO findOne(String id) {
		log.debug("Request to get User: {}", id);
		RestPreconditions.checkFound(userRepository.findOne(Long.valueOf(id)), User.class);
		return mapper.map(userRepository.findOne(Long.valueOf(id)), UserDTO.class);
	}
	
	@Transactional
	public List<UserDTO> findAll() {
		log.debug("Request to get all Users");
		return mapper.mapAsList(userRepository.findAll(), UserDTO.class);
	}
	
	@Transactional
	public UserDTO addUser(UserDTO user) {
		log.debug("Request to add User");
		User u = new User();
		u.setLogin(user.getLogin());
		u.setPassword(encoder.encode(user.getPassword()));
		u.setContacts(mapper.mapAsList(user.getContacts(), Contact.class));
		userRepository.save(u);
		return mapper.map(u, UserDTO.class);
	}
	
	@Transactional
	public void updateUser(UserDTO userDto, String id) {
		log.debug("Request to update User: {}", id);
		RestPreconditions.checkFound(userRepository.findOne(Long.valueOf(id)), User.class);
		User updatedUser = userRepository.findOne(Long.valueOf(id));
		updatedUser.setLogin(userDto.getLogin());
		updatedUser.setPassword(encoder.encode(userDto.getPassword()));
		updatedUser.setContacts(mapper.mapAsList(userDto.getContacts(), Contact.class));
	}
	
	@Transactional
	public void deleteUser(String id) {
		log.debug("Request to delete User: {}", id);
		RestPreconditions.checkFound(userRepository.findOne(Long.valueOf(id)), User.class);
		userRepository.delete(Long.valueOf(id));
	}
	
	//nie wiem czemu hibernate dodaje dwa na raz kontakty, dlatego dalem delete (slabe obejscie)
	@Transactional
	public UserDTO addContactToUser(Long id, ContactDTO contact) {
		log.debug("Request to add Contact to User");
		RestPreconditions.checkFound(userRepository.findOne(id), User.class);
		User u = userRepository.findOne(id);
		Contact c = mapper.map(contact, Contact.class);
		List<Contact> contacts = u.getContacts();
		contacts.add(mapper.map(contact, Contact.class));
		u.setContacts(contacts);
		userRepository.save(u);
		contactRepository.delete(c);;
		return mapper.map(u, UserDTO.class);
	}
	
	@Transactional
	public UserDTO editUsersContact(Long userId, Long contactId, ContactDTO contactDTO) {
		log.debug("Request to edit User's Contact");
		RestPreconditions.checkFound(userRepository.findOne(userId), User.class);
		RestPreconditions.checkFound(contactRepository.findOne(contactId), Contact.class);
		User u = userRepository.findOne(userId);
		Contact c = contactRepository.findOne(contactId);
		if (u.getContacts().contains(c)) {
			c.setCompanyName(contactDTO.getCompanyName());
			c.setEmail(contactDTO.getEmail());
			c.setLastName(contactDTO.getLastName());
			c.setName(contactDTO.getName());
			c.setPhoneNumber(contactDTO.getPhoneNumber());
			userRepository.save(u);
		}
		else 
			throw new RuntimeException("Contact doesn't belong to this User!");
		return mapper.map(u, UserDTO.class);
	}
	
	@Transactional
	public UserDTO deleteContactFromUser(Long userId, Long contactId) {
		log.debug("Request to delete User's: {} Contact: {}", userId, contactId);
		RestPreconditions.checkFound(userRepository.findOne(Long.valueOf(userId)), User.class);
		RestPreconditions.checkFound(contactRepository.findOne(contactId), Contact.class);
		Contact c = contactRepository.findOne(contactId);
		User u = userRepository.findOne(userId);
		if (u.getContacts().contains(c)) {
			u.getContacts().remove(c);
			contactRepository.delete(c);
			userRepository.save(u);
		}
		else
			throw new RuntimeException("Contact doesn't belong to this User!");
		return mapper.map(u, UserDTO.class);
	}
	
	
}

