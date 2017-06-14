package com.xerpass.logsafe.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.xerpass.logsafe.models.Cliente;

@Repository
public interface ClienteRepository extends PagingAndSortingRepository<Cliente, String> {

	Long countByNome(String nome);

}
