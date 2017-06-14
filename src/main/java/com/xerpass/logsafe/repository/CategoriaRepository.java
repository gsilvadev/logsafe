package com.xerpass.logsafe.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.xerpass.logsafe.models.Categoria;

@Repository
public interface CategoriaRepository extends PagingAndSortingRepository<Categoria, String> {

	Long countByNome(String nome);

}
