package com.exemplo.livros.service;

import com.exemplo.livros.exception.ResourceNotFoundException;
import com.exemplo.livros.model.Livro;
import com.exemplo.livros.repository.LivroRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LivroServiceTest {

    @Mock
    private LivroRepository repository;

    @InjectMocks
    private LivroService service;

    @Test
    void deveBuscarLivroPorIdExistente() {
        Livro livro = Livro.builder().id(1L).titulo("Dom Casmurro").autor("Machado de Assis")
                .anoPublicacao(1899).preco(29.9).build();
        when(repository.findById(1L)).thenReturn(Optional.of(livro));

        Livro resultado = service.buscarPorId(1L);

        assertEquals("Dom Casmurro", resultado.getTitulo());
        verify(repository).findById(1L);
    }

    @Test
    void deveLancarExcecaoQuandoLivroNaoExiste() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.buscarPorId(99L));
    }

    @Test
    void deveCriarLivro() {
        Livro entrada = Livro.builder().titulo("O Cortiço").autor("Aluísio Azevedo")
                .anoPublicacao(1890).preco(35.0).build();
        when(repository.save(any(Livro.class))).thenAnswer(inv -> {
            Livro l = inv.getArgument(0);
            l.setId(10L);
            return l;
        });

        Livro salvo = service.criar(entrada);

        assertEquals(10L, salvo.getId());
        verify(repository).save(entrada);
    }
}
