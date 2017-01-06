package pl.volanto.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.volanto.dto.UserDTO;
import pl.volanto.service.UserService;
import pl.volanto.util.RestPreconditions;


@RestController
@RequestMapping("/api")
public class UserController {

	private final Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@GetMapping(value = "/users", 
				produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UserDTO>> getUsers() {
		log.debug("REST request to get all Users");
		return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
	}

	@GetMapping(value = "/users/{id}", 
				produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getUser(@PathVariable String id) {
		log.debug("REST request to get User: {}", id);	
		UserDTO userDTO = userService.findOne(id);
		RestPreconditions.checkFound(userDTO);
		return  new ResponseEntity<>(userDTO, HttpStatus.OK);
	}
	
	@PostMapping(value = "/users", 
				 produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createUser(@RequestBody UserDTO user) {
		log.debug("REST request to add User");
		userService.addUser(user);	
		return new ResponseEntity<>(user, HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/users/{id}", 
				produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateUser(@RequestBody UserDTO updatedUser, 
						   				@PathVariable String id) {
		log.debug("REST request to update User: {}", id);
	    RestPreconditions.checkFound(userService.findOne(id));
	    userService.updateUser(updatedUser, id);
	    return new ResponseEntity<>(HttpStatus.CREATED);
	}	
	
	@DeleteMapping(value = "/users/{id}", 
				   produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteUser(@PathVariable String id) {
		log.debug("REST request to update User: {}", id);
		RestPreconditions.checkFound(userService.findOne(id));
		userService.deleteUser(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
