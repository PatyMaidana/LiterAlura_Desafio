package br.com.alura.Literalura.DTO;

import java.util.List;

public class GutendexResponseDTO {
    private List<LivroDTO> results;

    public List<LivroDTO> getResults() {
        return results;
    }

    public void setResults(List<LivroDTO> results) {
        this.results = results;
    }
 }

