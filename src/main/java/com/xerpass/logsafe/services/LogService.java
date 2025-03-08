package com.xerpass.logsafe.services;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.xerpass.logsafe.models.Log;
import com.xerpass.logsafe.repository.CustomLogRepository;
import com.xerpass.logsafe.repository.LogRepository;

@Service
public class LogService {

    private final LogRepository repository;
    private final CustomLogRepository customRepository;

    public LogService(LogRepository repository, CustomLogRepository customRepository) {
        this.repository = repository;
        this.customRepository = customRepository;
    }

    /**
     * Salva um novo log no sistema
     *
     * @param log Log a ser salvo
     * @param loginUsuarioAutenticado Nome do usuário autenticado
     * @return Log salvo com dados atualizados
     * @throws IllegalArgumentException se o log ou usuário forem inválidos
     */
    @Transactional
    public Log salvar(Log log, String loginUsuarioAutenticado) {
        if (log == null) {
            throw new IllegalArgumentException("O log não pode ser nulo");
        }

        if (!StringUtils.hasText(loginUsuarioAutenticado)) {
            throw new IllegalArgumentException("O usuário responsável é obrigatório");
        }

        log.setUsuarioResponsavel(loginUsuarioAutenticado);
        log.setDataHora(new Date());

        return repository.save(log);
    }

    /**
     * Lista todos os logs com paginação
     *
     * @param pageable Configuração de paginação
     * @return Página com os logs encontrados
     */
    public Page<Log> listarTodos(Pageable pageable) {
        return this.repository.findAll(pageable);
    }

    /**
     * Lista todos os logs (sem paginação)
     * @return Todos os logs do sistema
     * @deprecated Use o método paginado listarTodos(Pageable) para grandes volumes
     */
    @Deprecated
    public Iterable<Log> listarTodos() {
        return this.repository.findAll();
    }

    /**
     * Realiza consulta de logs com filtros opcionais
     */
    public List<Log> consultaParametrizada(
            String produto, String categoria, String cliente,
            Date dataInicio, Date dataFim, String usuario) {

        Map<String, Object> params = new HashMap<>();

        adicionarParametroSeValido(params, CustomLogRepository.PRODUTO, produto);
        adicionarParametroSeValido(params, CustomLogRepository.CATEGORIA, categoria);
        adicionarParametroSeValido(params, CustomLogRepository.CLIENTE, cliente);
        adicionarParametroSeValido(params, CustomLogRepository.DATA_INICIO, dataInicio);
        adicionarParametroSeValido(params, CustomLogRepository.DATA_FIM, dataFim);
        adicionarParametroSeValido(params, CustomLogRepository.USUARIO, usuario);

        return this.customRepository.consultar(params);
    }

    /**
     * Adiciona um parâmetro ao mapa apenas se o valor não for nulo
     */
    private void adicionarParametroSeValido(Map<String, Object> params, String chave, Object valor) {
        if (valor != null) {
            if (!(valor instanceof String) || StringUtils.hasText((String) valor)) {
                params.put(chave, valor);
            }
        }
    }
}