package com.xerpass.logsafe.services;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xerpass.logsafe.models.Cliente;
import com.xerpass.logsafe.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;

	public boolean isClienteValido(String cliente) {
		return this.repository.countByNome(cliente) > 0;
	}

	@Transactional
	public void geraDadosIniciais() {
		if (this.repository.count() > 0)
			return;

		String[] nomes = { "Acre", "Alagoas", "Amapá", "Amazonas", "Bahia", "Ceará", 
						   "Distrito Federal", "Espírito Santo", "Goiás", "Maranhão", 
						   "Mato Grosso", "Mato Grosso do Sul", "Minas Gerais", "Pará ", 
						   "Paraíba", "Paraná", "Pernambuco", "Piauí", "Rio de Janeiro", 
						   "Rio Grande do Norte", "Rio Grande do Sul", "Rondônia", "Roraima", 
						   "Santa Catarina", "São Paulo", "Sergipe", "Tocantins" };

		List<Cliente> clientes = Arrays.asList(nomes).stream().map(nome -> new Cliente(nome))
				.collect(Collectors.toList());

		this.repository.save(clientes);
	}
}
