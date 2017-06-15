package com.xerpass.logsafe.validators;

import static org.springframework.util.StringUtils.isEmpty;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.xerpass.logsafe.models.Log;
import com.xerpass.logsafe.services.CategoriaService;
import com.xerpass.logsafe.services.ClienteService;
import com.xerpass.logsafe.services.ProdutoService;

@Component
public class LogValidator implements Validator {

	@Autowired
	private ProdutoService produtoService;
	@Autowired
	private CategoriaService categoriaService;
	@Autowired
	private ClienteService clienteService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Log.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Log log = (Log) target;

		errors = validaProduto(log.getProduto(), errors);
		errors = validaCategoria(log.getCategoria(), errors);
		errors = validaCliente(log.getCliente(), errors);
		errors = validaRegistros(log.getRegistros(), errors);

	}

	private Errors validaRegistros(Map<String, String> registro, Errors errors) {

		if (registro == null || registro.size() == 0){
			errors.rejectValue("registros", "Registros inexistentes", "Deve haver ao menos um registro de log.");
			return errors;
		}
		
		registro.forEach((chave, valor) -> {
			if(isEmpty(chave) || isEmpty(valor)){
				errors.rejectValue("registros", "Registro inválido", "O registro de chave: \""+chave+"\" e valor: \""+valor+"\" são inválidos.");
			}
		});

		return errors;
	}

	private Errors validaCliente(String cliente, Errors errors) {

		if (!this.clienteService.isClienteValido(cliente))
			errors.rejectValue("cliente", "Cliente inválido", "O cliente " + cliente + " não é válido.");

		return errors;
	}

	private Errors validaCategoria(String categoria, Errors errors) {

		if (!this.categoriaService.isCategoriaValida(categoria))
			errors.rejectValue("categoria", "Categoria inválida", "A categoria " + categoria + " não é válida.");

		return errors;
	}

	private Errors validaProduto(String produto, Errors errors) {

		if (!this.produtoService.isProdutoValido(produto))
			errors.rejectValue("produto", "Produto inválido", "O produto " + produto + " não é válido.");

		return errors;
	}

}
