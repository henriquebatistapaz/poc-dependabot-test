package com.example.deptest;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;
import org.apache.commons.io.FilenameUtils;
import org.junit.jupiter.api.Test;

class LibsSmokeTest {

    @Test
    void guavaJoinsValues() {
        assertThat(Joiner.on(",").join("a", "b")).isEqualTo("a,b");
    }

    @Test
    void commonsIoReadsExtension() {
        assertThat(FilenameUtils.getExtension("relatorio.pdf")).isEqualTo("pdf");
    }

    @Test
    void jacksonWritesJson() throws Exception {
        assertThat(new ObjectMapper().writeValueAsString(java.util.Map.of("k", 1))).isEqualTo("{\"k\":1}");
    }
}
