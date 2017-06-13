package com.xerpass.logsafe.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xerpass.logsafe.models.Log;
import com.xerpass.logsafe.repository.LogRepository;

@Service
public class LogService {

	@Autowired
	private LogRepository repository;
	
	public Log salvar(Log log){
		validacoes(log);
		return repository.save(log);
	}

	private void validacoes(Log log) {
		validaCamposObrigatorios(log);
	}

	private void validaCamposObrigatorios(Log log) {
		
		
	}
}
