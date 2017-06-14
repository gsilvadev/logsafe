package com.xerpass.logsafe.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.xerpass.logsafe.models.Produto;

@Repository
public interface ProdutoRepository extends PagingAndSortingRepository<Produto, String> {

	Long countByNome(String nome);

}
