package pl.volanto.configuration.security;


import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private final String LOGIN = "x-login";

	@Autowired
	private UserDetailsService userDetailsService;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SecurityConfiguration.class);

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(
				passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf()
				.disable()
				.authorizeRequests()
	            .antMatchers("/api/users").hasAuthority("ADMIN")
	            .antMatchers("/api").permitAll()
				.anyRequest()
				.authenticated()
				.accessDecisionManager(accessDecisionManager())
				.and()
				.formLogin()
				.defaultSuccessUrl("/swagger/index.html")
				.successHandler(new AuthenticationSuccessHandler() {

					@Override
					public void onAuthenticationSuccess(
							HttpServletRequest request,
							HttpServletResponse response,
							Authentication authentication) throws IOException,
							ServletException {

						response.setStatus(HttpServletResponse.SC_OK);

						LOGGER.info(authentication.getPrincipal() + " is connected ");

						User user = (User) authentication.getPrincipal();
						HttpSession session = request.getSession();
						session.setAttribute(LOGIN, user.getUsername());
						response.sendRedirect("/swagger/index.html");
					}
				}).permitAll().and().httpBasic();

	}

	@Bean
	public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
		return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(
				new HttpSessionEventPublisher());
	}

	@Bean
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}



	@Bean
	public AffirmativeBased accessDecisionManager() {
		AffirmativeBased affirmativeBased = new AffirmativeBased(Arrays.asList(
				(AccessDecisionVoter) roleVoter(), authenticatedVoter(),
				webExpressionVoter()));
		// affirmativeBased.setAllowIfAllAbstainDecisions(false);
		return affirmativeBased;
	}

	@Bean
	public RoleVoter roleVoter() {
		final RoleVoter roleVoter = new RoleVoter();
		roleVoter.setRolePrefix("");
		return roleVoter;
	}

	@Bean
	public AuthenticatedVoter authenticatedVoter() {
		return new AuthenticatedVoter();
	}

	@Bean
	WebExpressionVoter webExpressionVoter() {
		return new WebExpressionVoter();
	}
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}

}
