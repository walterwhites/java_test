package com.dummy.myerp.technical.exception;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FunctionalExceptionTest {

    @Test
    public void functionnalExceptionWithMessage() {
        FunctionalException functionalException = assertThrows(FunctionalException.class, () -> {
            throw new FunctionalException("mon message fonctionnel");
        });
        assertEquals("mon message fonctionnel", functionalException.getMessage());
    }

    @Test
    public void functionnalExceptionWithCause() {
        FunctionalException functionalException = assertThrows(FunctionalException.class, () -> {
            Throwable throwable = new Throwable();
            throw new FunctionalException(throwable);
        });
        assertEquals(Throwable.class, functionalException.getCause().getClass());
    }

    @Test
    public void functionnalExceptionWithMessageAndCause() {
        FunctionalException functionalException = assertThrows(FunctionalException.class, () -> {
            Throwable throwable = new Throwable();
            throw new FunctionalException("mon message fonctionnel 2", throwable);
        });
        assertEquals(Throwable.class, functionalException.getCause().getClass());
        assertEquals("mon message fonctionnel 2", functionalException.getMessage());
    }
}