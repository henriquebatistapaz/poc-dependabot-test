package com.example.deptest;

import lombok.Builder;
import lombok.Value;

/** Entidade de exemplo: Lombok gera getters, construtor, equals, hashCode e toString. */
@Value
@Builder
public class Pessoa {
    Long id;
    String nome;
    String email;
}
