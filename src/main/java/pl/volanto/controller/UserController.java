package pl.volanto.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.volanto.dto.ContactDTO;
import pl.volanto.dto.UserDTO;
import pl.volanto.entity.User;
import pl.volanto.service.UserService;
import pl.volanto.util.RestPreconditions;


@RestController
@RequestMapping("/api")
public class UserController {

	private final Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;
	
	//@PreAuthorize("hasRole('ADMIN')")
	@GetMapping(value = "/users", 
				produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UserDTO>> getCustomers() {
		log.info("REST request to get all Users");
		return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
	}

	//@PreAuthorize("hasRole('ADMIN')")
	@GetMapping(value = "/users/{id}", 
				produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getUsers(@PathVariable String id) {
		log.info("REST request to get User: {}", id);	
		UserDTO userDTO = userService.findOne(id);
		return  new ResponseEntity<>(userDTO, HttpStatus.OK);
	}
	
	@PostMapping(value = "/users", 
				 produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createUser(@RequestBody UserDTO user) {
		log.info("REST request to add User");
		userService.addUser(user);	
		return new ResponseEntity<>(user, HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/users/{id}", 
				produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateUser(@RequestBody UserDTO updatedUser, 
						   				@PathVariable String id) {
		log.info("REST request to update User: {}", id);
	    RestPreconditions.checkFound(userService.findOne(id), User.class);
	    userService.updateUser(updatedUser, id);
	    return new ResponseEntity<>(HttpStatus.CREATED);
	}	
	
	@DeleteMapping(value = "/users/{id}", 
				   produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteUser(@PathVariable String id) {
		log.info("REST request to update User: {}", id);
		RestPreconditions.checkFound(userService.findOne(id), User.class);
		userService.deleteUser(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping(value = "/users/{id}/contacts", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDTO> addContactToUser(@PathVariable Long id, 
													@RequestBody ContactDTO contactDTO) {
		log.info("REST request to add Contact to User");
		UserDTO u = userService.addContactToUser(id, contactDTO);
		return new ResponseEntity<>(u, HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/users/{userId}/contacts/{contactId}", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDTO> editUsersContact(@PathVariable Long userId, 
													@PathVariable Long contactId,
													@RequestBody ContactDTO contactDTO) {
		log.info("REST request to edit User's Contact");
		UserDTO u = userService.editUsersContact(userId, contactId, contactDTO);
		return new ResponseEntity<>(u, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/users/{userId}/contacts/{contactId}", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDTO> deleteUsersContact(@PathVariable Long userId, 
													  @PathVariable Long contactId) {
		log.info("REST request to delete User's Contact");
		UserDTO u = userService.deleteContactFromUser(userId, contactId);
		return new ResponseEntity<>(u, HttpStatus.OK);
	}
	

}
