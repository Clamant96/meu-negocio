package org.helpconnect.meuNegocio.seguranca;

import java.util.Optional;

import org.helpconnect.meuNegocio.model.Usuario;
import org.helpconnect.meuNegocio.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImplementation implements UserDetailsService {
	
	@Autowired
	private UsuarioRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		Optional<Usuario> user = userRepository.findByUsuario(username);
		
		user.orElseThrow(() -> new UsernameNotFoundException(username + " not found."));
		
		return user.map(UserDetailsImplementation::new).get();		
	}

}
