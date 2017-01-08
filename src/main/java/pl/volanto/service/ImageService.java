package pl.volanto.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import pl.volanto.dto.UserDTO;
import pl.volanto.entity.Contact;
import pl.volanto.entity.Image;
import pl.volanto.entity.User;
import pl.volanto.repository.ContactRepository;
import pl.volanto.repository.ImageRepository;
import pl.volanto.util.RestPreconditions;

@Service
@Transactional
public class ImageService {
	
	private final Logger log = LoggerFactory.getLogger(ImageService.class);
	
	public static final String uploadingdir = System.getProperty("user.dir") + "/uploadingdir/";
	
	@Autowired
	private ContactRepository contactRepository;
	
	@Autowired
	private ImageRepository imageRepitory;
	
	public void uploadFileAndAddImage(Long id, MultipartFile file) throws IllegalStateException, IOException {
		
        File f = new File(uploadingdir + file.getOriginalFilename());
        file.transferTo(f);
        log.info("Image saved in: {}", f.getAbsolutePath());
		Image i = new Image(f.getAbsolutePath());
		
		RestPreconditions.checkFound(contactRepository.findOne(Long.valueOf(id)), Contact.class);
		Contact c = contactRepository.findOne(Long.valueOf(id));
		c.setImage(i);
		contactRepository.save(c);
		imageRepitory.save(i);
	}
}
