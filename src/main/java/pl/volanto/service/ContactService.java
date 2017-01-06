package pl.volanto.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ma.glasnost.orika.MapperFacade;
import pl.volanto.dto.ContactDTO;
import pl.volanto.entity.Contact;
import pl.volanto.repository.ContactRepository;

@Service
public class ContactService {
	
	private final Logger log = LoggerFactory.getLogger(ContactService.class);

	@Autowired
	ContactRepository contactRepository;
	
	@Autowired
	private MapperFacade mapper;
	
	@Transactional
	public ContactDTO findOne(String id) {
		log.debug("Request to get Contact: {}", id);
		return mapper.map(
				contactRepository.findOne(Long.valueOf(id)), 
				ContactDTO.class);
	}
	
	@Transactional
	public List<ContactDTO> findAll() {
		log.debug("Request to get all Contacts");
		return mapper.mapAsList(
				contactRepository.findAll(),
				ContactDTO.class);
	}
	
	@Transactional
	public ContactDTO addContact(ContactDTO contact) {
		log.debug("Request to add Contact");
		Contact c = new Contact();
		c = mapper.map(contact, Contact.class);
		contactRepository.save(c);
		return mapper.map(c, ContactDTO.class);
	}
	
	@Transactional
	public void updateContact(ContactDTO contactDTO, String id) {
		log.debug("Request to update Contact: {}", id);
		Contact updatedContact = contactRepository.findOne(Long.valueOf(id));
		updatedContact.setName(contactDTO.getName());
		updatedContact.setLastName(contactDTO.getLastName());
		updatedContact.setCompanyName(contactDTO.getCompanyName());
		updatedContact.setEmail(contactDTO.getEmail());
		updatedContact.setPhoneNumber(contactDTO.getPhoneNumber());
	}
	
	@Transactional
	public void deleteContact(String id) {
		log.debug("Request to delete Contact: {}", id);
		contactRepository.delete(Long.valueOf(id));
	}
	
	
	
}
