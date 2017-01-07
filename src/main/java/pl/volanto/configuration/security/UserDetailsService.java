package pl.volanto.configuration.security;


import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import pl.volanto.entity.Role;
import pl.volanto.repository.UserRepository;

@Component("userDetailsService")
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(UserDetailsService.class);

    @Autowired
    private UserRepository userRepository;

	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(final String username)
			throws UsernameNotFoundException {

		pl.volanto.entity.User userInDb = userRepository.findByLogin(username);
		if (userInDb != null) {
			List<GrantedAuthority> authorities = getAuthorities(userInDb);
			User user = new User(userInDb.getLogin(), userInDb.getPassword(),
					authorities);

			return user;
		}
		throw new UsernameNotFoundException("User not exists: " + username);

	}

	private List<GrantedAuthority> getAuthorities(pl.volanto.entity.User userInDb) {
		List<GrantedAuthority> authorities = Lists.newArrayList();
		Role permission = userInDb.getRole();

		authorities.add(new SimpleGrantedAuthority(permission.getName()));

		return authorities;
	}

	private Collection<? extends GrantedAuthority> getPortalUserAuthorities() {
		List<GrantedAuthority> authorities = Lists.newArrayList();
		authorities.add(new SimpleGrantedAuthority("PORTAL_USER_ROLE"));
		return authorities;
	}
}
