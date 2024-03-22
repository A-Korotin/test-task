package org.ra.atomidtesttask.presentation.http.advice;

import io.swagger.v3.oas.annotations.Hidden;
import org.ra.atomidtesttask.application.exception.NotFoundException;
import org.ra.atomidtesttask.application.user.exception.DuplicateUsernameException;
import org.ra.atomidtesttask.presentation.http.dto.ErrorDto;
import org.ra.atomidtesttask.presentation.http.dto.ValidationErrorDto;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
public class ExceptionHandlingRestControllerAdvice {
    @ExceptionHandler(DuplicateUsernameException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @Hidden
    public ErrorDto handle(DuplicateUsernameException cause) {
        return ErrorDto.of(cause.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @Hidden
    public ErrorDto handle(NotFoundException e) {
        return ErrorDto.of(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @Hidden
    public ValidationErrorDto handle(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(error -> {
            String field = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(field, errorMessage);
        });
        return new ValidationErrorDto("Binding error", errors);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @Hidden
    public ValidationErrorDto handle(MethodArgumentTypeMismatchException e) {
        String requiredType = Objects.nonNull(e.getRequiredType()) ? e.getRequiredType().getSimpleName() : "unknown";
        String argumentName = e.getName();
        Map<String, String> errors = new HashMap<>(2);
        errors.put("expectedType", requiredType);
        errors.put("argument", argumentName);

        return new ValidationErrorDto("Argument type mismatch", errors);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PropertyReferenceException.class)
    @Hidden
    public ErrorDto handle(PropertyReferenceException e) {
        return ErrorDto.of(e.getMessage());
    }
}
