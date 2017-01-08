package pl.volanto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.volanto.entity.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {

}
