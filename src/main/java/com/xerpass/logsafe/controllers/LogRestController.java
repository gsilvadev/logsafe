package com.xerpass.logsafe.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xerpass.logsafe.models.Log;
import com.xerpass.logsafe.services.LogService;

@RestController
@RequestMapping("/logs")
public class LogRestController {

	@Autowired
	private LogService service;
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<?> gravar(@Valid @RequestBody Log log, BindingResult result){
		
		if(result.hasErrors()){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(this.service.salvar(log), HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public void listar(@RequestParam(name="produto", required=false) String produto, 
					   @RequestParam(name="cliente", required=false) String cliente){
		System.out.println("Teste");
	}
}
