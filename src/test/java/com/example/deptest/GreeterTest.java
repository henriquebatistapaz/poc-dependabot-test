package com.example.deptest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class GreeterTest {
    @Test
    void greetsName() {
        assertEquals("Olá, Ana!", Greeter.greet("ana"));
    }

    @Test
    void usesDefaultWhenBlank() {
        assertEquals("Olá, Mundo!", Greeter.greet("  "));
    }
}
