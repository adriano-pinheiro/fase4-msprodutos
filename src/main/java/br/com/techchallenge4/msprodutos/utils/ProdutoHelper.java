package br.com.techchallenge4.msprodutos.utils;

import br.com.techchallenge4.msprodutos.dto.ProdutoDTO;

import java.math.BigInteger;

public abstract class ProdutoHelper {

    protected ProdutoHelper() {
    }

    public static ProdutoDTO criarProduto(){
        return new ProdutoDTO(null,
                "Produto 1",
                "Essa é a descrição do produto",
                BigInteger.valueOf(10),
                5D,
                true
        );
    }

}
