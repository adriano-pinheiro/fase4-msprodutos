package br.com.techchallenge4.msprodutos.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.techchallenge4.msprodutos.dto.ProdutoDTO;
import br.com.techchallenge4.msprodutos.model.ProdutoEstoque;
import br.com.techchallenge4.msprodutos.service.ProdutoService;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/v1/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    @Autowired
    private final ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<Page<ProdutoDTO>> listarProdutos(
            @PageableDefault(page = 0, size = 10, sort = "nome") Pageable pageable
    ) {
        Page<ProdutoDTO> produtos = produtoService.getProdutos(pageable);
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> listarUmProduto(@PathVariable Long id) {
    	ProdutoDTO produto = produtoService.getProduto(id);
        return ResponseEntity.ok(produto);
    }

    @PostMapping
    public ResponseEntity<ProdutoDTO> cadastrarProduto(@RequestBody ProdutoDTO produtoDTO) {
    	ProdutoDTO produto = produtoService.saveProduto(produtoDTO);
        return new ResponseEntity<>(produto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDTO> atualizarProduto(@PathVariable Long id, @RequestBody ProdutoDTO produtoDTO) {
    	ProdutoDTO produto = produtoService.updateProduto(id, produtoDTO);
        return ResponseEntity.ok(produto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirProduto(@PathVariable Long id) {
    	produtoService.deleteProduto(id);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/atualizar/estoque")
    public List<ProdutoEstoque> atualizarEstoque(@RequestBody List<ProdutoEstoque> produtoEstoques) {
    	List<ProdutoEstoque> produtoEstoqueAtualizados = new ArrayList<>(); // TODO: retirar
    	return produtoEstoqueAtualizados;
        //return produtoService.atualizarEstoque(produtoEstoques);
    }

 
}
