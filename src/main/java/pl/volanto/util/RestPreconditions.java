package pl.volanto.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class RestPreconditions {

	@ResponseStatus(HttpStatus.NOT_FOUND)
	public static <T> T checkFound(final T resource, Class<?> c) {
		if (resource == null) {
			throw new RuntimeException(c.getSimpleName() + " does not exist!");
		}
		return resource;
	}
}
