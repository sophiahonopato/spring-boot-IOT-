package com.exemplo.livros.service;

import com.exemplo.livros.exception.ResourceNotFoundException;
import com.exemplo.livros.model.Livro;
import com.exemplo.livros.repository.LivroRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivroService {

    private final LivroRepository repository;

    public LivroService(LivroRepository repository) {
        this.repository = repository;
    }

    public List<Livro> listar() {
        return repository.findAll();
    }

    public Livro buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado com id " + id));
    }

    public Livro criar(Livro livro) {
        livro.setId(null);
        return repository.save(livro);
    }

    public Livro atualizar(Long id, Livro dados) {
        Livro existente = buscarPorId(id);
        existente.setTitulo(dados.getTitulo());
        existente.setAutor(dados.getAutor());
        existente.setAnoPublicacao(dados.getAnoPublicacao());
        existente.setPreco(dados.getPreco());
        return repository.save(existente);
    }

    public void deletar(Long id) {
        Livro existente = buscarPorId(id);
        repository.delete(existente);
    }
}
