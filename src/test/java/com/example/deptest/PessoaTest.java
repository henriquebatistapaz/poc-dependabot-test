package com.example.deptest;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class PessoaTest {

    @Test
    void builderGeneratesValueObject() {
        Pessoa a = Pessoa.builder().id(1L).nome("Ana").email("ana@example.com").build();
        Pessoa b = Pessoa.builder().id(1L).nome("Ana").email("ana@example.com").build();

        assertThat(a).isEqualTo(b);
        assertThat(a).hasSameHashCodeAs(b);
        assertThat(a.getNome()).isEqualTo("Ana");
    }

    @Test
    void differentFieldsAreNotEqual() {
        Pessoa a = Pessoa.builder().id(1L).nome("Ana").build();
        Pessoa b = Pessoa.builder().id(2L).nome("Ana").build();

        assertThat(a).isNotEqualTo(b);
    }

    @Test
    void missingFieldsAreNull() {
        Pessoa p = Pessoa.builder().nome("Bia").build();

        assertThat(p.getId()).isNull();
        assertThat(p.getEmail()).isNull();
    }

    @Test
    void toStringContainsFields() {
        Pessoa p = Pessoa.builder().id(7L).nome("Caio").build();

        assertThat(p.toString()).contains("Caio").contains("7");
    }
}
