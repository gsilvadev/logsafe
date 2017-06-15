package com.xerpass.logsafe.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.xerpass.logsafe.models.Log;

@Repository
public class CustomLogRepository {

	@PersistenceContext
	private EntityManager manager;

	public static final String PRODUTO = "produto_logRepository";
	public static final String CATEGORIA = "categoria_logRepository";
	public static final String CLIENTE = "cliente_logRepository";
	public static final String DATA_INICIO = "dataInicio_logRepository";
	public static final String DATA_FIM = "dataFim_logRepository";
	public static final String USUARIO = "usuario_logRepository";

	private String consulta;

	private Map<String, String> queryFragments;

	public CustomLogRepository() {
		this.consulta = "select distinct(log) from Log log left join fetch log.registros registro ";
		this.queryFragments = new HashMap<>();

		this.queryFragments.put(PRODUTO, " log.produto = :" + PRODUTO);
		this.queryFragments.put(CATEGORIA, " log.categoria = :" + CATEGORIA);
		this.queryFragments.put(CLIENTE, " log.cliente = :" + CLIENTE);
		this.queryFragments.put(DATA_INICIO,
				" year(log.dataHora) >= year(:" + DATA_INICIO + ") and month(log.dataHora) >= month(:" + DATA_INICIO
						+ ") and day(log.dataHora) >= day(:" + DATA_INICIO + ")");
		this.queryFragments.put(DATA_FIM,
				" year(log.dataHora) <= year(:" + DATA_FIM + ") and month(log.dataHora) <= month(:" + DATA_FIM
						+ ") and day(log.dataHora) <= day(:" + DATA_FIM + ")");
		this.queryFragments.put(USUARIO, " log.usuarioResponsavel = :" + USUARIO);
	}

	public List<Log> consultar(Map<String, Object> params) {
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append(this.consulta);

		if (!params.isEmpty()) {

			queryBuilder.append(" where ");
			params.keySet().stream().forEach(chave -> {
				queryBuilder.append(this.queryFragments.get(chave));
				queryBuilder.append(" and ");
			});
			queryBuilder.append(" 1 = 1");
		}

		TypedQuery<Log> query = manager.createQuery(queryBuilder.toString(), Log.class);

		params.forEach((chave, valor) -> query.setParameter(chave, valor));

		List<Log> result = query.getResultList();

		return result;
	}

}
