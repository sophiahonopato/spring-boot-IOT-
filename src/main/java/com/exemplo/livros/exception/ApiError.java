package com.exemplo.livros.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class ApiError {
    private final LocalDateTime timestamp;
    private final int status;
    private final String erro;
    private final List<String> mensagens;
}
