package pl.volanto.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import pl.volanto.dto.ContactDTO;
import pl.volanto.service.ContactService;
import pl.volanto.service.ImageService;


@RestController
@RequestMapping("/api")
public class ContactController {

	private final Logger log = LoggerFactory.getLogger(ContactController.class);
	
	@Autowired
	private ImageService imageService;
	
	@Autowired
	ContactService contactService;

	@GetMapping(value = "/contacs", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ContactDTO>> getContacts() {
		log.info("REST request to get all Contacts");
		return new ResponseEntity<>(contactService.findAll(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/contacs/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ContactDTO>> getContact(@PathVariable String id) {
		log.info("REST request to get Contact: {}", id);	
		return new ResponseEntity<>(contactService.findAll(), HttpStatus.OK);
	}
	
        
	@PostMapping(value = "/contacts/{id}/file")
    public  void uploadingPost(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws IOException {
    	imageService.uploadFileAndAddImage(id, file);
    }

}
