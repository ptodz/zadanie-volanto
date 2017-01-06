package pl.volanto.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import pl.volanto.exception.UserNotFoundException;

public class RestPreconditions {

	@ResponseStatus(HttpStatus.NOT_FOUND)
	public static <T> T checkFound(final T resource) {
		if (resource == null) {
			throw new UserNotFoundException();
		}
		return resource;
	}
}
