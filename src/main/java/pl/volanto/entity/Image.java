package pl.volanto.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Image {
	
	@Id
	@GeneratedValue
	Long id;
	
	String path;
	
	public Image(String path){
		this.path = path;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return path;
	}

	public void setUrl(String url) {
		this.path = url;
	}
}
