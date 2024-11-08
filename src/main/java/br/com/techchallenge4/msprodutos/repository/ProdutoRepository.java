package br.com.techchallenge4.msprodutos.repository;

import br.com.techchallenge4.msprodutos.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
