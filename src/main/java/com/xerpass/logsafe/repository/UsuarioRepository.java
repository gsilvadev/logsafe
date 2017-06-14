package com.xerpass.logsafe.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.xerpass.logsafe.models.Usuario;

public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, Integer>{

	public Usuario getUsuarioByLogin(String login);

	public Long countByLogin(String string);
}
