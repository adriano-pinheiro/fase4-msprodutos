package br.com.techchallenge4.msprodutos.service.impl;


import java.math.BigInteger;
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

    public List<ProdutoEstoque> atualizarEstoque(List<ProdutoEstoque> produtoEstoques) {
        List<ProdutoEstoque> produtoEstoqueAtualizados = new ArrayList<>();

        for (ProdutoEstoque prod : produtoEstoques) {

            var produtoId = prod.getId();
            BigInteger quantidade= prod.getQtdEstoque();

            Produto produto = produtoRepository.findById(produtoId).orElse(null);
            if (produto != null) {

                if (quantidade.equals(BigInteger.ZERO)) {
                    throw new NoSuchElementException("A quantidade solicitada deve ser diferente de 0.");
                }

                if (quantidade.compareTo(produto.getQtdEstoque()) > 0) {
                    throw new NoSuchElementException("A quantidade solicitada é maior que o estoque do produto.");
                }

                produto.setQtdEstoque(produto.getQtdEstoque().subtract(quantidade));

                produtoRepository.save(produto);
                produtoEstoqueAtualizados.add(new ProdutoEstoque(produto.getId(), produto.getQtdEstoque()));
            } else {
                throw new NoSuchElementException("Produto não encontrado: " + produtoId);
            }
        }

        return  produtoEstoqueAtualizados;    	
    	//return new ArrayList<>(); // TODO: retirar
    }
}
