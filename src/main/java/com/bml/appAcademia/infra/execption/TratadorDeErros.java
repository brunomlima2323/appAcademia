package com.bml.appAcademia.infra.execption;

import com.bml.appAcademia.domain.ValidacaoException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, Object>> tratarErro404(EntityNotFoundException ex){
        Map<String, Object> erro = new LinkedHashMap<>();
        erro.put("timestamp", LocalDateTime.now());
        erro.put("status", HttpStatus.NOT_FOUND.value());
        erro.put("error", "Recurso não encontrado");
        erro.put("message", ex.getMessage());
        erro.put("path", ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400(MethodArgumentNotValidException ex){
        List<FieldError> erros = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new));
    }

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity tratarErro400(ValidacaoException ex){
//        return ResponseEntity.badRequest().body(ex.getMessage());
        Map<String, Object> erro = new LinkedHashMap<>();
        erro.put("timestamp", LocalDateTime.now());
        erro.put("status", HttpStatus.BAD_REQUEST.value());
        erro.put("error", "Erro na requisição");
        erro.put("message", ex.getMessage());
        erro.put("path", ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    private record DadosErroValidacao(String campo, String mensagem){
        public DadosErroValidacao(FieldError fieldErrod){
            this(fieldErrod.getField(),fieldErrod.getDefaultMessage());
        }
    }
}
