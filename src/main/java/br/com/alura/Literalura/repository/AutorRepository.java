package br.com.alura.Literalura.repository;

import br.com.alura.Literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    Optional<Autor> findByNome(String nome);

    @Query("select a from Autor a where a.anoNascimento <= :ano and (a.anoMorte is null or a.anoMorte >= :ano)")
    List<Autor> findAutoresVivosEmAno(@Param("ano") Integer ano);
}