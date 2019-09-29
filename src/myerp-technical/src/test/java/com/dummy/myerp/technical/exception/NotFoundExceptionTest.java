package com.dummy.myerp.technical.exception;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NotFoundExceptionTest {

    @Test
    public void notFoundExceptionWithMessage() {
        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> {
            throw new NotFoundException("mon message");
        });
        assertEquals("mon message", notFoundException.getMessage());
    }

    @Test
    public void notFoundExceptionWithCause() {
        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> {
            Throwable throwable = new Throwable();
            throw new NotFoundException(throwable);
        });
        assertEquals(Throwable.class, notFoundException.getCause().getClass());
    }

    @Test
    public void notFoundExceptionWithMessageAndCause() {
        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> {
            Throwable throwable = new Throwable();
            throw new NotFoundException("mon message 2", throwable);
        });
        assertEquals(Throwable.class, notFoundException.getCause().getClass());
        assertEquals("mon message 2", notFoundException.getMessage());
    }
}