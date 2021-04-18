package org.helpconnect.meuNegocio.seguranca;

import java.util.Collection;
import java.util.List;

import org.helpconnect.meuNegocio.model.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsImplementation implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	
	private String password;
	private String username;
	
	private List<GrantedAuthority> authorities;
	
	public UserDetailsImplementation(Usuario user) {
		this.password = user.getSenha();
		this.username = user.getUsuario();
	}
	
	public UserDetailsImplementation() {
		
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
