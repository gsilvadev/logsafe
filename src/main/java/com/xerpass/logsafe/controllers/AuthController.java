package com.xerpass.logsafe.controllers;

import static org.springframework.util.StringUtils.isEmpty;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.xerpass.logsafe.exception.ApiErrorException;
import com.xerpass.logsafe.models.Usuario;
import com.xerpass.logsafe.services.CategoriaService;
import com.xerpass.logsafe.services.ClienteService;
import com.xerpass.logsafe.services.ProdutoService;
import com.xerpass.logsafe.services.UsuarioService;
import com.xerpass.logsafe.utils.Md5Util;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private ProdutoService produtoService;
	@Autowired
	private CategoriaService categoriaService;
	@Autowired
	private ClienteService clienteService;
	
	@PostConstruct
	private void persisteDadosFicticiosIniciais(){
		this.usuarioService.geraDadosIniciais();
		this.produtoService.geraDadosIniciais();
		this.categoriaService.geraDadosIniciais();
		this.clienteService.geraDadosIniciais();
	}

	@RequestMapping(method=RequestMethod.POST)
	public ResponseToken authentication(@Param(value = "login") String login,
			@Param(value = "senha") String senha){
		
		if(isEmpty(login) || isEmpty(senha)){
			throw new ApiErrorException("Parâmetros incorretos", "Os parâmetros login e senha são obrigatórios.");
		}
		
		Usuario usuarioEncontrado = this.usuarioService.getUsuarioByLogin(login);
		
		if(!Md5Util.encriptar(senha).equals(usuarioEncontrado.getPassword())){
			throw new ApiErrorException("Login ou senha incorretos", "Login e senha não conferem.");
		}
		
		Algorithm algorithm;
		String token = "";
		
		try {
			algorithm = Algorithm.HMAC512("secreto");
			
			// Criando token com validade de 10 minutos
			token = JWT.create()
					.withIssuer("auth0")
					.withSubject(usuarioEncontrado.getLogin())
					.withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
					.sign(algorithm);
			
		} catch (IllegalArgumentException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return new ResponseToken(token);
	}
	
	protected class ResponseToken{
		
		public String token;
		
		public ResponseToken(String token){
			this.token = token;
		}
	}
}
