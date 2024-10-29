package br.com.fiap.pet.pet.dto;

import java.util.UUID;

public record ProdutoDTO(
        UUID id,
        String nome,
        String descricao,
        double preco,
        String urlImagem
) {
}
