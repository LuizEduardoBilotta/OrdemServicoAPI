package br.com.bilotta.ordemServicosAPI.api.model;

import javax.validation.constraints.NotNull;

public class ClienteIdInput {
	
	@NotNull
	private Long Id;

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}
}
