package com.edsonsarmiento.reservacioncoworking.util;

import com.edsonsarmiento.reservacioncoworking.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleMissingBody(HttpMessageNotReadableException exception){
        return ResponseEntity.badRequest().body("El cuerpo de la petición está vacío o contiene errores de sintaxis.");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException exception){
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(EmailExisteException.class)
    public ResponseEntity<Map<String, String>> handleEmailExisteException(EmailExisteException exception){
        Map<String, String> response = new HashMap<>();
        response.put("error", exception.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler({BadCredentialsException.class, InternalAuthenticationServiceException.class})
    public ResponseEntity<Map<String, String>> handleErrorCredenciales(Exception exception){
        Map<String, String> response = new HashMap<>();
        response.put("error", "Correo o contraseña incorrectos");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(SalaNoEncontradaException.class)
    public ResponseEntity<Map<String, String>> handleSalaNoEncontradaException(SalaNoEncontradaException exception){
        Map<String, String> response = new HashMap<>();
        response.put("error", exception.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> handleTypeMismatchException(MethodArgumentTypeMismatchException exception){
        Map<String, Object> errorResponse = new HashMap<>();

        String nombreParametro = exception.getName();
        String tipoRequerido = exception.getRequiredType() != null ? exception.getRequiredType().getSimpleName() : "desconocido";
        Object valorParametro = exception.getValue();

        errorResponse.put("status", HttpStatus.BAD_REQUEST.value());
        errorResponse.put("error", "Parámetro de ruta inválido");
        errorResponse.put("mensaje", String.format(
                "El parámetro '%s' debe ser un número entero de tipo %s. Valor recibido: '%s'",
                nombreParametro, tipoRequerido, valorParametro
        ));

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ChoqueHorariosException.class)
    public ResponseEntity<Map<String, String>> handleChoqueHorarioException(ChoqueHorariosException exception){
        Map<String, String> response = new HashMap<>();
        response.put("error", exception.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(ReservacionNoEncontradaException.class)
    public ResponseEntity<Map<String, String>> handleReservacionNoEncontrada(ReservacionNoEncontradaException exception){
        Map<String, String> response = new HashMap<>();
        response.put("error", exception.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(AccesoReservacionException.class)
    public ResponseEntity<Map<String, String>> handleAccesoReservacionException(AccesoReservacionException exception){
        Map<String, String> response = new HashMap<>();
        response.put("error", exception.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, String>> handleIllegalStateException(IllegalStateException exception){
        Map<String, String> response = new HashMap<>();
        response.put("error", exception.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(ServicioInestableException.class)
    public ResponseEntity<Map<String,String>> handleServicioInestableException(ServicioInestableException exception){
        Map<String, String> response = new HashMap<>();
        response.put("error", exception.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(NumeroHorasMinException.class)
    public ResponseEntity<Map<String,String>> handleNumeroHorasMinException(NumeroHorasMinException exception){
        Map<String, String> response = new HashMap<>();
        response.put("error", exception.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(PagoRechazadoException.class)
    public ResponseEntity<Map<String,String>> handlePagoRechazadoException(PagoRechazadoException exception){
        Map<String, String> response = new HashMap<>();
        response.put("error", exception.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

}
