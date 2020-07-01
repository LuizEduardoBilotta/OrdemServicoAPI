package br.com.bilotta.ordemServicosAPI.api.model;

import java.time.OffsetDateTime;

public class ComentarioModel {
	
	private Long Id;
	private String descricao;
	private OffsetDateTime dataEnvio;
	
	
	public Long getId() {
		return Id;
	}
	
	public void setId(Long id) {
		Id = id;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public OffsetDateTime getDataEnvio() {
		return dataEnvio;
	}
	
	public void setDataEnvio(OffsetDateTime dataEnvio) {
		this.dataEnvio = dataEnvio;
	}
	
	
}
