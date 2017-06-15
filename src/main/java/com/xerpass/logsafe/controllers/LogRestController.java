package com.xerpass.logsafe.controllers;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xerpass.logsafe.exception.ApiErrorException;
import com.xerpass.logsafe.models.Log;
import com.xerpass.logsafe.services.LogService;
import com.xerpass.logsafe.utils.JwtUtil;
import com.xerpass.logsafe.validators.LogValidator;

@RestController
@RequestMapping("/api/private/logs")
public class LogRestController {

	@Autowired
	private LogService service;
	@Autowired
	private LogValidator validator;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.addValidators(validator);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> gravar(@Valid @RequestBody Log log, BindingResult result, HttpServletRequest request) {

		if (result.hasErrors()) {
			ApiErrorException ex = new ApiErrorException();
			result.getAllErrors().stream().forEach(error -> {
				ex.addMessage(error.getCode(), error.getDefaultMessage());
			});

			throw ex;
		}

		String loginUsuarioAutenticado = JwtUtil.getLoginFromRequest(request);

		return new ResponseEntity<>(this.service.salvar(log, loginUsuarioAutenticado), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET)
	public Iterable<Log> listar(@RequestParam(name = "produto", required = false) String produto,
			@RequestParam(name = "cliente", required = false) String cliente,
			@RequestParam(name = "categoria", required = false) String categoria,
			@RequestParam(name = "from", required=false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date dataInicio,
			@RequestParam(name = "to", required=false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date dataFim,
			@RequestParam(name = "usuario", required = false) String usuario) {
		
		
		return this.service.consultaParametrizada(produto, categoria, cliente, dataInicio, dataFim, usuario);
	}
}
