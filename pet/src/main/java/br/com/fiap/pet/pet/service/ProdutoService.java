package br.com.fiap.pet.pet.service;

import br.com.fiap.pet.pet.controller.exception.ControllerNotFoundException;
import br.com.fiap.pet.pet.dto.ProdutoDTO;
import br.com.fiap.pet.pet.entities.Produto;
import br.com.fiap.pet.pet.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Collection<ProdutoDTO> findAll() {
        return produtoRepository.findAll().stream().map(this::toDTO).toList();
    }

    public ProdutoDTO findById(UUID id) {
        return toDTO(produtoRepository.findById(id).orElseThrow(() -> new ControllerNotFoundException("Produto not found with id " + id)));
    }

    public ProdutoDTO save(ProdutoDTO produtoDTO) {
        Produto produto = toEntity(produtoDTO);
        return toDTO(produtoRepository.save(produto));
    }

    public ProdutoDTO update(UUID id, ProdutoDTO produtoDTO) {

        try {
            Produto buscalProduto = produtoRepository.getReferenceById(id);
            buscalProduto.setNome(produtoDTO.nome());
            buscalProduto.setDescricao(produtoDTO.descricao());
            buscalProduto.setPreco(produtoDTO.preco());
            buscalProduto.setUrlImagem(produtoDTO.urlImagem());

            return toDTO(produtoRepository.save(buscalProduto));

        } catch (EntityNotFoundException e) {
            throw new ControllerNotFoundException("Produto not found with id " + id, e);
        }
    }

    public void delete(UUID id) {
        produtoRepository.deleteById(id);
    }

    private ProdutoDTO toDTO(Produto produto) {
        return new ProdutoDTO(produto.getId(), produto.getNome(), produto.getDescricao(), produto.getPreco(), produto.getUrlImagem());
    }

    private Produto toEntity(ProdutoDTO produtoDTO) {
        return new Produto(produtoDTO.id(), produtoDTO.nome(), produtoDTO.descricao(), produtoDTO.preco(), produtoDTO.urlImagem());
    }
}
