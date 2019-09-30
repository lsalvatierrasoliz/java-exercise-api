package com.example.university.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.NoSuchElementException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {


    /**
     * Exception handler if NoSuchElementException is thrown
     *
     * @param ex exception
     */
    @ExceptionHandler(NoSuchElementException.class)
    public void notFoundElementHandler(NoSuchElementException ex,  HttpServletResponse res) throws IOException {
        res.sendError(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    /**
     * Handler exception student already registered at course
     *
     * @param ex
     * @param res
     * @throws IOException
     */
    @ExceptionHandler(StudentAlreadyRegistered.class)
    public void studentAlreadyRegistered(StudentAlreadyRegistered ex, HttpServletResponse res) throws IOException {
        res.sendError(HttpStatus.CONFLICT.value(), ex.getMessage());
    }

    /**
     * Handler general exception
     *
     * @param ex
     * @param res
     * @throws IOException
     */
    @ExceptionHandler(Exception.class)
    public void handleException(Exception ex, HttpServletResponse res) throws IOException {
        res.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Something went wrong");
    }
}
