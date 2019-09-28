package com.dummy.myerp.technical.exception;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TechnicalExceptionTest extends Exception {

    @Test
    public void technicalExceptionWithMessage() {
        TechnicalException technicalException = assertThrows(TechnicalException.class, () -> {
            throw new TechnicalException("mon message technique");
        });
        assertEquals("mon message technique", technicalException.getMessage());
    }

    @Test
    public void technicalExceptionWithCause() {
        TechnicalException technicalException = assertThrows(TechnicalException.class, () -> {
            Throwable throwable = new Throwable();
            throw new TechnicalException(throwable);
        });
        assertEquals(Throwable.class, technicalException.getCause().getClass());
    }

    @Test
    public void technicalExceptionWithMessageAndCause() {
        TechnicalException technicalException = assertThrows(TechnicalException.class, () -> {
            Throwable throwable = new Throwable();
            throw new TechnicalException("mon message technique 2", throwable);
        });
        assertEquals(Throwable.class, technicalException.getCause().getClass());
        assertEquals("mon message technique 2", technicalException.getMessage());
    }
}