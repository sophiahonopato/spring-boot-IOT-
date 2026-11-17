package com.exemplo.livros.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O título é obrigatório")
    @Size(max = 150, message = "O título deve ter no máximo 150 caracteres")
    private String titulo;

    @NotBlank(message = "O autor é obrigatório")
    private String autor;

    @NotNull(message = "O ano de publicação é obrigatório")
    @Min(value = 1450, message = "Ano inválido")
    @Max(value = 2100, message = "Ano inválido")
    private Integer anoPublicacao;

    @NotNull(message = "O preço é obrigatório")
    @DecimalMin(value = "0.0", inclusive = false, message = "O preço deve ser maior que zero")
    private Double preco;
}
