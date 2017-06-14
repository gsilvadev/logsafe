package com.xerpass.logsafe.services;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xerpass.logsafe.models.Categoria;
import com.xerpass.logsafe.repository.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repository;

	public boolean isCategoriaValida(String categoria) {
		return this.repository.countByNome(categoria) > 0;
	}

	@Transactional
	public void geraDadosIniciais() {
		if (this.repository.count() > 0)
			return;

		String[] nomes = { "erro", "permissão", "autenticação" };

		List<Categoria> categorias = Arrays.asList(nomes).stream().map(nome -> new Categoria(nome))
				.collect(Collectors.toList());

		this.repository.save(categorias);
	}
}
