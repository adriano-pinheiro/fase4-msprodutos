package br.com.techchallenge4.msprodutos.controller;

import br.com.techchallenge4.msprodutos.model.Produto;
import br.com.techchallenge4.msprodutos.model.ProdutoEstoque;
import br.com.techchallenge4.msprodutos.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public List<Produto> listarProdutos() {
        return produtoService.listarProdutos();
    }

    @GetMapping("/{produtoId}")
    public ResponseEntity<?> listarUmProduto(@PathVariable Long produtoId) {
        return produtoService.listarUmProduto(produtoId);
    }
    @PostMapping
    public Produto cadastrarProduto(@RequestBody Produto produto) {
        return produtoService.cadastrarProduto(produto);
    }

    @PutMapping("/{produtoId}")
    public Produto atualizarProduto(
            @PathVariable Long produtoId, @RequestBody Produto novoProduto) {

        return produtoService.atualizarProduto(produtoId, novoProduto);
    }

    @DeleteMapping("/{produtoId}")
    public void excluirProduto(@PathVariable Long produtoId) {
        produtoService.excluirProduto(produtoId);
    }

    @PutMapping("/atualizar/estoque")
    public List<ProdutoEstoque> atualizarEstoque(@RequestBody List<ProdutoEstoque> produtoEstoques) {
        return produtoService.atualizarEstoque(produtoEstoques);
    }
}
