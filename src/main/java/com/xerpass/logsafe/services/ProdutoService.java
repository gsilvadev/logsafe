package com.xerpass.logsafe.services;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xerpass.logsafe.models.Produto;
import com.xerpass.logsafe.repository.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository repository;

	public boolean isProdutoValido(String produto) {

		return this.repository.countByNome(produto) > 0;
	}

	@Transactional
	public void geraDadosIniciais() {
		if (this.repository.count() > 0)
			return;

		String[] nomes = { "Defensoria", "OAB" };

		List<Produto> produtos = Arrays.asList(nomes).stream().map(nome -> new Produto(nome))
				.collect(Collectors.toList());

		this.repository.save(produtos);
	}
}
