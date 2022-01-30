package ocho.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ocho.exception.EmailNotFoundException;
import ocho.repository.UserRepository;

@Service
public class SecurityService implements UserDetailsService {
    @Autowired
	UserRepository userRepository;

	@Override
	@Transactional
	public UserSecurity loadUserByUsername(String email) throws EmailNotFoundException {
		var user = userRepository.findByEmail(email)
				.orElseThrow(() -> new EmailNotFoundException("User Not Found with email: " + email));

		return UserSecurity.build(user);
	}
}
