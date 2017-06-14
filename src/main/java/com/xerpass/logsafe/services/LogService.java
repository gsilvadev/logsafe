package com.xerpass.logsafe.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xerpass.logsafe.models.Log;
import com.xerpass.logsafe.repository.LogRepository;

@Service
public class LogService {

	@Autowired
	private LogRepository repository;
	
	@Transactional
	public Log salvar(Log log, String loginUsuarioAutenticado){
		
		log.setUsuarioResponsavel(loginUsuarioAutenticado);
		log.setDataHora(new Date());
		
		return repository.save(log);
	}

	public Iterable<Log> listarTodos() {
		return this.repository.findAll();
	}
}
