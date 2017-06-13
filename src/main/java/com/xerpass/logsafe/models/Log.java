package com.xerpass.logsafe.models;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "log")
public class Log implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String produto;
	private String cliente;
	private Date dataHora;
	private String categoria;
	private Map<String, String> registro;

	private Usuario usuarioResponsavel;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_log")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "produto")
	@NotEmpty(message = "field.required")
	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}

	@Column(name = "cliente")
	@NotEmpty(message = "field.required")
	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	@Column(name = "data_hora")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDataHora() {
		return dataHora;
	}

	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}

	@Column(name = "categoria")
	@NotEmpty(message = "field.required")
	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	@ElementCollection
	@CollectionTable(name = "log_registro", 
		joinColumns = { 
			@JoinColumn(name = "id_log_registro", 
				referencedColumnName = "id_log") },
		
		foreignKey = @ForeignKey(name = "FK_REGISTRO_LOG"))
	public Map<String, String> getRegistro() {
		return registro;
	}

	public void setRegistro(Map<String, String> registro) {
		this.registro = registro;
	}

	@ManyToOne
	@JoinColumn(name = "login_usuario", referencedColumnName = "login", foreignKey = @ForeignKey(name = "FK_LOG_USUARIO", value = ConstraintMode.CONSTRAINT))
	public Usuario getUsuarioResponsavel() {
		return usuarioResponsavel;
	}

	public void setUsuarioResponsavel(Usuario usuarioResponsavel) {
		this.usuarioResponsavel = usuarioResponsavel;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Log other = (Log) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
