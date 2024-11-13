package br.com.techchallenge4.msprodutos.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProdutoEstoque {
    private Long id;
    private Long qtdEstoque;
}
