package br.com.techchallenge4.msprodutos.service.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.techchallenge4.msprodutos.controller.exception.ControllerNotFoundException;
import br.com.techchallenge4.msprodutos.dto.ProdutoDTO;
import br.com.techchallenge4.msprodutos.model.Produto;
import br.com.techchallenge4.msprodutos.model.ProdutoEstoque;
import br.com.techchallenge4.msprodutos.repository.ProdutoRepository;
import br.com.techchallenge4.msprodutos.service.ProdutoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProdutoServiceImpl implements ProdutoService {

    @Autowired
    private final ProdutoRepository produtoRepository;

    @Override
    public ProdutoDTO saveProduto(ProdutoDTO produtoDTO) {
    	Produto produto = produtoRepository.save(toProdutoEntity(produtoDTO));
        return toProdutoDTO(produto);
    }

    @Override
    public ProdutoDTO getProduto(Long id) {
    	Produto produto = produtoRepository.findById(id)
                .orElseThrow( () ->
                        new ControllerNotFoundException("Produto não encontrado."));
        return toProdutoDTO(produto);
    }

    @Override
    public Page<ProdutoDTO> getProdutos(Pageable pageable) {
        Page<Produto> produtos = produtoRepository.findAll(pageable);
        return produtos.map(this::toProdutoDTO);
    }

    @Override
    public ProdutoDTO updateProduto(Long id, ProdutoDTO produtoDTO) throws EntityNotFoundException {
    	Produto produto = produtoRepository.findById(id)
                .orElseThrow( () ->
                        new ControllerNotFoundException("Produto não encontrado."));
    	produto.setNome(produtoDTO.nome());
    	produto.setDescricao(produtoDTO.descricao());
    	produto.setQtdEstoque(produtoDTO.qtdEstoque());
    	produto.setPreco(produtoDTO.preco());
    	produto.setAtivo(produtoDTO.ativo());
    	produtoRepository.save(produto);

        return toProdutoDTO(produto);
    }

    @Override
    public boolean deleteProduto(Long id) {
    	produtoRepository.deleteById(id);
        return true;
    }

    @Override
    public ProdutoDTO toProdutoDTO(Produto produto) {
        return new ProdutoDTO(
        		produto.getId(),
        		produto.getNome(),
        		produto.getDescricao(),
        		produto.getQtdEstoque(),
        		produto.getPreco(),
        		produto.getAtivo()
        );
    }

    @Override
    public Produto toProdutoEntity(ProdutoDTO produtoDTO) {
        return new Produto(
        		produtoDTO.id(),
        		produtoDTO.nome(),
        		produtoDTO.descricao(),
        		produtoDTO.qtdEstoque(),
        		produtoDTO.preco(),
        		produtoDTO.ativo()
        );
    }

 
}
