package br.com.bilotta.ordemServicosAPI.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import br.com.bilotta.ordemServicosAPI.api.model.Comentario;
import br.com.bilotta.ordemServicosAPI.domain.exception.NegocioException;


@Entity
public class OrdemServico {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private Cliente cliente;
	
	private String descricao;
	
	private BigDecimal preco;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private StatusOrdemServico statusOrdemServico;
	
	private OffsetDateTime dataAbertura;
	
	private OffsetDateTime dataFinalizacao;
	
	@OneToMany(mappedBy = "ordemServico")
	private List<Comentario> comentarios = new ArrayList<>();
	
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Cliente getCliente() {
		return cliente;
	}
	
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public BigDecimal getPreco() {
		return preco;
	}
	
	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}
	
	public StatusOrdemServico getStatusOrdemServico() {
		return statusOrdemServico;
	}
	
	public void setStatusOrdemServico(StatusOrdemServico statusOrdemServico) {
		this.statusOrdemServico = statusOrdemServico;
	}
	
	public OffsetDateTime getDataAbertura() {
		return dataAbertura;
	}
	
	public void setDataAbertura(OffsetDateTime dataAbertura) {
		this.dataAbertura = dataAbertura;
	}
	
	public OffsetDateTime getDataFinalizacao() {
		return dataFinalizacao;
	}
	
	public void setDataFinalizacao(OffsetDateTime dataFinalizacao) {
		this.dataFinalizacao = dataFinalizacao;
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
		OrdemServico other = (OrdemServico) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public List<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}
	
	@SuppressWarnings("static-access")
	public boolean poderSerFinalizada() {
		return statusOrdemServico.ABERTA.equals(getStatusOrdemServico());
	}
	
	public boolean naoPodeSerFinalizada() {
		return !poderSerFinalizada();
	}

	@SuppressWarnings("static-access")
	public void finalizar() {
		
		if (naoPodeSerFinalizada()) {
			throw new NegocioException("Ordem de serviço não pode ser finalizada!");
		}
		
		setStatusOrdemServico(statusOrdemServico.FINALIZADA);
		setDataFinalizacao(OffsetDateTime.now());
	}
	
	
}
