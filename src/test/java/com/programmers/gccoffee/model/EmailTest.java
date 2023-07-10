package com.programmers.gccoffee.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EmailTest {

    @Test
    void testInvalidEmail() {
        assertThrows(IllegalArgumentException.class, () -> {
            Email email = new Email("accccc");
        });
    }

    @Test
    void testValidEmail() {
        Email email = new Email("hello@gmail.com");
        assertEquals(email.getAddress(), "hello@gmail.com");
    }

    @Test
    void testEqualEmail() {
        Email email1 = new Email("hello@gmail.com");
        Email email2 = new Email("hello@gmail.com");
        assertEquals(email1, email2);
    }
}