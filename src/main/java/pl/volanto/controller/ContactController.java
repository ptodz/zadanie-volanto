package pl.volanto.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.volanto.dto.ContactDTO;
import pl.volanto.service.ContactService;

@RestController
@RequestMapping("/api")
public class ContactController {

	private final Logger log = LoggerFactory.getLogger(ContactController.class);

	@Autowired
	ContactService contactService;

	@GetMapping(value = "/contacs", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ContactDTO>> getContacts() {
		log.debug("REST request to get all Contacts");
		return new ResponseEntity<>(contactService.findAll(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/contacs/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ContactDTO>> getContact(@PathVariable String id) {
		log.debug("REST request to get Contact: {}", id);	
		return new ResponseEntity<>(contactService.findAll(), HttpStatus.OK);
	}

}
