package br.com.bilotta.ordemServicosAPI.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.bilotta.ordemServicosAPI.domain.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	
	//Criando os metodos personalizados
	
	//Busca de clientes por nome.
	List<Cliente> findByNome(String nome);
	
	//Busca de clientes por nome, contendo alguma letra na parte do nome.
	List<Cliente> findByNomeContaining(String Nome);
	
	Cliente findByEmail(String email);
}
