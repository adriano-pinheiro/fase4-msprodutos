package br.com.techchallenge4.msprodutos.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import br.com.techchallenge4.msprodutos.dto.ProdutoDTO;
import br.com.techchallenge4.msprodutos.model.Produto;
import br.com.techchallenge4.msprodutos.model.ProdutoEstoque;
import jakarta.persistence.EntityNotFoundException;

public interface ProdutoService {

    ProdutoDTO saveProduto(ProdutoDTO produtoDTO);
    ProdutoDTO getProduto(Long id);
    Page<ProdutoDTO> getProdutos(Pageable pageable);
    ProdutoDTO updateProduto(Long id, ProdutoDTO produtoDTO) throws EntityNotFoundException;
    boolean deleteProduto(Long id);
    List<ProdutoEstoque> atualizarEstoque(List<ProdutoEstoque> produtoEstoques);
    ProdutoDTO toProdutoDTO(Produto produto);
    Produto toProdutoEntity(ProdutoDTO produtoDTO);
}
