package br.com.techchallenge4.msprodutos.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigInteger;

@Data
@AllArgsConstructor
public class ProdutoEstoque {
    private Long id;
    private BigInteger qtdEstoque;
}
