package br.com.bilotta.ordemServicosAPI.domain.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bilotta.ordemServicosAPI.api.model.Comentario;
import br.com.bilotta.ordemServicosAPI.domain.exception.EntidadeNaoEncontradaException;
import br.com.bilotta.ordemServicosAPI.domain.exception.NegocioException;
import br.com.bilotta.ordemServicosAPI.domain.model.Cliente;
import br.com.bilotta.ordemServicosAPI.domain.model.OrdemServico;
import br.com.bilotta.ordemServicosAPI.domain.model.StatusOrdemServico;
import br.com.bilotta.ordemServicosAPI.domain.repository.ClienteRepository;
import br.com.bilotta.ordemServicosAPI.domain.repository.ComentarioRepository;
import br.com.bilotta.ordemServicosAPI.domain.repository.OrdemServicoRepository;

@Service
public class GestaoOrdemServicoService {
	
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ComentarioRepository comentarioRepository;
	
	public OrdemServico criar(OrdemServico ordemServico ) {
		
		Cliente cliente = clienteRepository.findById(ordemServico.getCliente().getId())
						.orElseThrow(() -> new NegocioException("Cliente não encontrado!"));
		
		ordemServico.setCliente(cliente);
		ordemServico.setStatusOrdemServico(StatusOrdemServico.ABERTA);
		ordemServico.setDataAbertura(OffsetDateTime.now());
		
		return ordemServicoRepository.save(ordemServico);
	}
	
	public Comentario adicionarComentario(Long ordemServicoId, String descricao) {
		OrdemServico ordemServico = buscar(ordemServicoId);
		
		Comentario comentario = new Comentario();
		
		comentario.setDataEnvio(OffsetDateTime.now());
		comentario.setDescricao(descricao);
		comentario.setOrdemServico(ordemServico);
		
		return comentarioRepository.save(comentario);
	}
	
	public void finalizar (Long ordemServicoId) {
		OrdemServico ordemServico = buscar(ordemServicoId);
		
		ordemServico.finalizar();
		
		ordemServicoRepository.save(ordemServico);
	}

	private OrdemServico buscar(Long ordemServicoId) {
		return ordemServicoRepository
				.findById(ordemServicoId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Ordem de servico não encontrada!"));
	}
}
