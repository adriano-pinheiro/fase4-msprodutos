package br.com.techchallenge4.msprodutos.service;

import br.com.techchallenge4.msprodutos.model.Produto;
import br.com.techchallenge4.msprodutos.model.ProdutoEstoque;
import br.com.techchallenge4.msprodutos.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Produto> listarProdutos() {
        return produtoRepository.findAll();
    }

    public ResponseEntity<?> listarUmProduto(Long produtoId) {

        Produto produto = produtoRepository.findById(produtoId).orElse(null);
        if (produto != null) {
            return ResponseEntity.ok(produto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
        }
    }

    public Produto cadastrarProduto(Produto produto) {

        return produtoRepository.save(produto);
    }

    public Produto atualizarProduto(Long produtoId, Produto novoProduto) {
        Produto produtoExistente = produtoRepository.findById(produtoId).orElse(null);

        if (produtoExistente != null) {
            produtoExistente.setNome(novoProduto.getNome());
            produtoExistente.setDescricao(novoProduto.getDescricao());
            produtoExistente.setQtdEstoque(novoProduto.getQtdEstoque());
            produtoExistente.setPreco(novoProduto.getPreco());
            produtoExistente.setAtivo(novoProduto.isAtivo());

            return produtoRepository.save(produtoExistente);
        } else {
            throw new NoSuchElementException("Produto não encontrado");
        }
    }

    public void excluirProduto(Long produtoId) {
        Produto produtoExistente = produtoRepository.findById(produtoId).orElse(null);

        if (produtoExistente != null) {
            produtoRepository.delete(produtoExistente);
        } else {
            throw new NoSuchElementException("Produto não encontrado");
        }
    }

    public List<ProdutoEstoque> atualizarEstoque(List<ProdutoEstoque> produtoEstoques) {

        List<ProdutoEstoque> produtoEstoqueAtualizados = new ArrayList<>();

        for (ProdutoEstoque prod : produtoEstoques) {

            var produtoId = prod.getId();
            var quantidade= prod.getQtdEstoque();

            Produto produto = produtoRepository.findById(produtoId).orElse(null);
            if (produto != null) {

                if (quantidade == 0) {
                    throw new NoSuchElementException("A quantidade solicitada deve ser diferente de 0.");
                }

                if (quantidade > produto.getQtdEstoque()) {
                    throw new NoSuchElementException("A quantidade solicitada é maior que o estoque do produto.");
                }

                produto.setQtdEstoque(produto.getQtdEstoque() - quantidade);

                produtoRepository.save(produto);
                produtoEstoqueAtualizados.add(new ProdutoEstoque(produto.getId(), produto.getQtdEstoque()));
            } else {
                throw new NoSuchElementException("Produto não encontrado: " + produtoId);
            }
        }

        return  produtoEstoqueAtualizados;

    }
}