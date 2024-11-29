package br.com.techchallenge4.msprodutos.model;

import java.math.BigInteger;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_produtos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Produto {

	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nome;
    private String descricao;
    private BigInteger qtdEstoque;
    private Double preco;
    private Boolean ativo;
    private LocalDateTime dtImportacao;

    public Produto(Long id, String nome, String descricao, BigInteger qtdEstoque, Double preco, Boolean ativo) {
    	this.id = id;
    	this.nome = nome;
    	this.descricao = descricao;
    	this.qtdEstoque = qtdEstoque;
    	this.preco = preco;
    	this.ativo = ativo;
    }
}
