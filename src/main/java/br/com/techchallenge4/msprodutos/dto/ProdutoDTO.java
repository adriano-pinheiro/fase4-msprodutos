package br.com.techchallenge4.msprodutos.dto;

import java.math.BigInteger;

public record ProdutoDTO (
    Long id,
    String nome,
    String descricao,
    BigInteger qtdEstoque,
    Double preco,
    Boolean ativo
){}
