package com.xerpass.logsafe.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xerpass.logsafe.exception.ApiErrorException;
import com.xerpass.logsafe.models.Usuario;
import com.xerpass.logsafe.repository.UsuarioRepository;
import com.xerpass.logsafe.utils.Md5Util;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;
	
	public Usuario getUsuarioByLogin(String login){
		
		Usuario usuario = this.repository.getUsuarioByLogin(login);
		
		if(usuario == null)
			throw new ApiErrorException("Usuário não encontrado", login+" não encontrado.");
			
		return usuario;
	}

	@Transactional
	public void geraDadosIniciais() {
		if(this.repository.countByLogin("admin") > 0)
			return;
		
		Usuario usuario = new Usuario("admin", Md5Util.encriptar("admin"));
		
		this.repository.save(usuario);
	}

	@Transactional
	public void salvar(Usuario usuario) {
		this.repository.save(usuario);
	}
}
