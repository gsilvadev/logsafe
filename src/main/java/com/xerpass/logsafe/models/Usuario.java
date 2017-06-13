package com.xerpass.logsafe.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class Usuario implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	private String login;
	private String password;
	private String token;

	@Temporal(TemporalType.TIMESTAMP)
	private Date validadeToken;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "usuario_roles",
		joinColumns = {
			@JoinColumn(name = "login_usuario", 
				referencedColumnName = "login", 
				foreignKey = @ForeignKey(name = "FK_LOGIN_USUARIO", 
										 value = ConstraintMode.CONSTRAINT)) },
		inverseJoinColumns = {
			@JoinColumn(name = "nome_role_usuario",
				referencedColumnName = "nome_role",
				foreignKey = @ForeignKey(name = "FK_ROLE_USUARIO",
										 value = ConstraintMode.CONSTRAINT)) })
	private List<Role> roles = new ArrayList<>();

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getValidadeToken() {
		return validadeToken;
	}

	public void setValidadeToken(Date validadeToken) {
		this.validadeToken = validadeToken;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.login;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
