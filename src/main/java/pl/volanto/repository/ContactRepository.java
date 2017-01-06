package pl.volanto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.volanto.entity.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long> {

}
