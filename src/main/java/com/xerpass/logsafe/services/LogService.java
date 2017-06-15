package com.xerpass.logsafe.services;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xerpass.logsafe.models.Log;
import com.xerpass.logsafe.repository.CustomLogRepository;
import com.xerpass.logsafe.repository.LogRepository;

@Service
public class LogService {

	@Autowired
	private LogRepository repository;
	@Autowired
	private CustomLogRepository customRepository;
	
	@Transactional
	public Log salvar(Log log, String loginUsuarioAutenticado){
		
		log.setUsuarioResponsavel(loginUsuarioAutenticado);
		log.setDataHora(new Date());
		
		return repository.save(log);
	}

	public Iterable<Log> listarTodos() {
		return this.repository.findAll();
	}
	
	public List<Log> consultaParametrizada(String produto, String categoria, String cliente, Date dataInicio, Date dataFim, String usuario){
		Map<String, Object> params = new HashMap<>();
		if(produto != null)
			params.put(CustomLogRepository.PRODUTO, produto);
		if(categoria != null)
			params.put(CustomLogRepository.CATEGORIA, categoria);
		if(cliente != null)
			params.put(CustomLogRepository.CLIENTE, cliente);
		if(dataInicio != null)
			params.put(CustomLogRepository.DATA_INICIO, dataInicio);
		if(dataFim != null)
			params.put(CustomLogRepository.DATA_FIM, dataFim);
		if(usuario != null)
			params.put(CustomLogRepository.USUARIO, usuario);
		
		return this.customRepository.consultar(params);
	}
}
