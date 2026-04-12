package lets_play.exception;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
        private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorResponse> handleAllExceptions(
                        Exception ex, WebRequest request) {

                log.error("Unhandled exception: {}", ex.getMessage());

                ErrorResponse error = ErrorResponse.builder()
                                .timestamp(Instant.now())
                                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .error("Internal Server Error")
                                .message(ex.getMessage())
                                .path(request.getDescription(false).replace("uri=", ""))
                                .build();

                return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        @ExceptionHandler(NotFoundException.class)
        public ResponseEntity<ErrorResponse> NotFoundException(
                        NotFoundException ex, WebRequest request) {

                log.error("Unhandled exception: {}", ex.getMessage());

                ErrorResponse error = ErrorResponse.builder()
                                .timestamp(Instant.now())
                                .status(HttpStatus.NOT_FOUND.value())
                                .error("Not Found")
                                .message(ex.getMessage())
                                .path(request.getDescription(false).replace("uri=", ""))
                                .build();

                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        @ExceptionHandler(ConflictException.class)
        public ResponseEntity<ErrorResponse> ConflictException(
                        ConflictException ex, WebRequest request) {

                log.error("Unhandled exception: {}", ex.getMessage());

                ErrorResponse error = ErrorResponse.builder()
                                .timestamp(Instant.now())
                                .status(HttpStatus.CONFLICT.value())
                                .error("Conflict")
                                .message(ex.getMessage())
                                .path(request.getDescription(false).replace("uri=", ""))
                                .build();

                return new ResponseEntity<>(error, HttpStatus.CONFLICT);
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ErrorResponse> MethodArgumentNotValidException(
                        MethodArgumentNotValidException ex, WebRequest request) {

                Map<String, String> errors = new HashMap<>();
                ex.getBindingResult().getFieldErrors()
                                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
                System.out.println(errors);

                log.error("Unhandled exception: {}", ex.getMessage());

                ErrorResponse error = ErrorResponse.builder()
                                .timestamp(Instant.now())
                                .status(HttpStatus.BAD_REQUEST.value())
                                .error("Bad Request")
                                .message("Validation failed")
                                .path(request.getDescription(false).replace("uri=", ""))
                                .validationErrors(errors)
                                .build();

                return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(BadRequestException.class)
        public ResponseEntity<ErrorResponse> BadRequestException(
                        BadRequestException ex, WebRequest request) {

                log.error("Unhandled exception: {}", ex.getMessage());

                ErrorResponse error = ErrorResponse.builder()
                                .timestamp(Instant.now())
                                .status(HttpStatus.BAD_REQUEST.value())
                                .error("Bad Request")
                                .message(ex.getMessage())
                                .path(request.getDescription(false).replace("uri=", ""))
                                .build();

                return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
        public ResponseEntity<ErrorResponse> HttpRequestMethodNotSupportedException(
                        HttpRequestMethodNotSupportedException ex, WebRequest request) {

                log.error("Unhandled exception: {}", ex.getMessage());

                ErrorResponse error = ErrorResponse.builder()
                                .timestamp(Instant.now())
                                .status(HttpStatus.METHOD_NOT_ALLOWED.value())
                                .error("Method Not Allowed")
                                .message(ex.getMessage())
                                .path(request.getDescription(false).replace("uri=", ""))
                                .build();

                return new ResponseEntity<>(error, HttpStatus.METHOD_NOT_ALLOWED);
        }

        @ExceptionHandler(NoResourceFoundException.class)
        public ResponseEntity<ErrorResponse> NoResourceFoundException(
                        NoResourceFoundException ex, WebRequest request) {

                log.error("Unhandled exception: {}", ex.getMessage());

                ErrorResponse error = ErrorResponse.builder()
                                .timestamp(Instant.now())
                                .status(HttpStatus.NOT_FOUND.value())
                                .error("Not Found")
                                .message(ex.getMessage())
                                .path(request.getDescription(false).replace("uri=", ""))
                                .build();

                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        @ExceptionHandler(HttpMessageNotReadableException.class)
        public ResponseEntity<ErrorResponse> HttpMessageNotReadableException(
                        HttpMessageNotReadableException ex, WebRequest request) {

                log.error("Unhandled exception: {}", ex.getMessage());

                ErrorResponse error = ErrorResponse.builder()
                                .timestamp(Instant.now())
                                .status(HttpStatus.BAD_REQUEST.value())
                                .error("Bad Request")
                                .message(ex.getMessage())
                                .path(request.getDescription(false).replace("uri=", ""))
                                .build();

                return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(AuthorizationDeniedException.class)
        public ResponseEntity<ErrorResponse> AuthorizationDeniedException(
                        AuthorizationDeniedException ex, WebRequest request) {

                log.error("Unhandled exception: {}", ex.getMessage());

                ErrorResponse error = ErrorResponse.builder()
                                .timestamp(Instant.now())
                                .status(HttpStatus.FORBIDDEN.value())
                                .error("Access denied")
                                .message(ex.getMessage())
                                .path(request.getDescription(false).replace("uri=", ""))
                                .build();

                return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
        }

        @ExceptionHandler(AccessDeniedException.class)
        public ResponseEntity<ErrorResponse> AccessDeniedException(
                        AccessDeniedException ex, WebRequest request) {

                log.error("Unhandled exception: {}", ex.getMessage());

                ErrorResponse error = ErrorResponse.builder()
                                .timestamp(Instant.now())
                                .status(HttpStatus.FORBIDDEN.value())
                                .error("Access denied")
                                .message(ex.getMessage())
                                .path(request.getDescription(false).replace("uri=", ""))
                                .build();

                return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
        }
}
