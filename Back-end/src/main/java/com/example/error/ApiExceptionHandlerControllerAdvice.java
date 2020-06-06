package com.example.error;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ApiExceptionHandlerControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFountException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ApiError handleEntityNotFound(EntityNotFountException exception) {
        return ApiError
                .builder()
                .status(HttpStatus.NOT_FOUND)
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .subErrors(new ArrayList<>())
                .build();
    }

    @ExceptionHandler(TaskDoneException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ApiError handleTaskDone(TaskDoneException exception) {
        return ApiError
                .builder()
                .status(HttpStatus.NOT_FOUND)
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .subErrors(new ArrayList<>())
                .build();
    }

    @ExceptionHandler(UniqueConstraintViolation.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ApiError handleUniqueConstraintViolation(UniqueConstraintViolation exception) {
        return ApiError
                .builder()
                .status(HttpStatus.NOT_FOUND)
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .subErrors(new ArrayList<>())
                .build();
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ApiError handleEntityAlreadyExists(EntityAlreadyExistsException exception) {
        return ApiError
                .builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .subErrors(new ArrayList<>())
                .build();
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ApiError handleBadCredential(BadCredentialsException exception) {
        return ApiError
                .builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .subErrors(new ArrayList<>())
                .build();
    }

    @ExceptionHandler(OverflowingTaskException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ApiError handleOverflowingTask(OverflowingTaskException exception) {
        return ApiError
                .builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .subErrors(new ArrayList<>())
                .build();
    }

    @ExceptionHandler(UserIsParticipantAlreadyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ApiError handleUserIsParticipantAlready(UserIsParticipantAlreadyException exception) {
        return ApiError
                .builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .subErrors(new ArrayList<>())
                .build();
    }

    @ExceptionHandler(TaskIsNotActiveException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ApiError handleTaskIsNotActive(TaskIsNotActiveException exception) {
        return ApiError
                .builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .subErrors(new ArrayList<>())
                .build();
    }

    @ExceptionHandler(UserIsCreatorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ApiError handleUserIsCreator(UserIsCreatorException exception) {
        return ApiError
                .builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .subErrors(new ArrayList<>())
                .build();
    }

    @ExceptionHandler(UserIsNotCreatorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ApiError handleUserIsNotCreator(UserIsNotCreatorException exception) {
        return ApiError
                .builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .subErrors(new ArrayList<>())
                .build();
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ApiError handleDataIntegrityViolation() {
        return ApiError
                .builder()
                .message("Database error! Possible causes: primary key is NULL or UNIQUE constraint violation")
                .status(HttpStatus.BAD_REQUEST)
                .timestamp(LocalDateTime.now())
                .subErrors(new ArrayList<>())
                .build();
    }

    @ExceptionHandler(PaginationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ApiError handlePagination(PaginationException exception) {
        return ApiError
                .builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .timestamp(LocalDateTime.now())
                .subErrors(new ArrayList<>())
                .build();
    }

    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        ApiError apiError = ApiError
                .builder()
                .status(HttpStatus.BAD_REQUEST)
                .timestamp(LocalDateTime.now())
                .message("Validation error!")
                .subErrors(getMANVESubErrors(exception))
                .build();
        return buildResponseEntity(apiError);
    }

    private List<ApiSubError> getMANVESubErrors(MethodArgumentNotValidException ex) {
        return ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::convertToValidationError)
                .collect(Collectors.toList());
    }

    private ApiValidationError convertToValidationError(FieldError fieldError) {
        return ApiValidationError
                .builder()
                .field(fieldError.getField())
                .message(fieldError.getDefaultMessage())
                .rejectedValue(fieldError.getRejectedValue())
                .build();
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ApiError handleConstraintViolation(ConstraintViolationException exception) {
        return ApiError
                .builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .subErrors(getCVESubErrors(exception))
                .build();
    }


    private List<ApiSubError> getCVESubErrors(ConstraintViolationException exc) {
        return exc.getConstraintViolations()
                .stream()
                .map(this::convertViolationToError)
                .collect(Collectors.toList());
    }

    private ApiValidationError convertViolationToError(ConstraintViolation<?> constraintViolation) {
        return ApiValidationError
                .builder()
                .field(constraintViolation.getPropertyPath().toString())
                .message(constraintViolation.getMessage())
                .rejectedValue(constraintViolation.getInvalidValue())
                .build();
    }

}