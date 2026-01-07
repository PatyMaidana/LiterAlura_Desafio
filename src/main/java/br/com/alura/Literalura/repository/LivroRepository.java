package br.com.alura.Literalura.repository;

import br.com.alura.Literalura.model.Language;
import br.com.alura.Literalura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    List<Livro> findByIdioma (Language idioma);
}
