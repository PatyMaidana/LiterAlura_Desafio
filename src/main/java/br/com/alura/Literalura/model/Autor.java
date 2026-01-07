package br.com.alura.Literalura.model;

import br.com.alura.Literalura.DTO.AutorDTO;
import jakarta.persistence.*;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(name = "ano_nascimento")
    private Integer anoNascimento;

    @Column(name = "ano_morte")
    private Integer anoMorte;

    public Autor() {}

    public Autor(String nome, Integer anoNascimento, Integer anoMorte) {
        this.nome = nome;
        this.anoNascimento = anoNascimento;
        this.anoMorte = anoMorte;
    }

    public Autor(AutorDTO dto) {
        this(dto.getName(), dto.getBirth_year(), dto.getDeath_year());
    }

    public Long getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public Integer getAnoNascimento() {
        return anoNascimento;
    }
    public void setAnoNascimento(Integer anoNascimento) {
        this.anoNascimento = anoNascimento;
    }
    public Integer getAnoMorte() {
        return anoMorte;
    }
    public void setAnoMorte(Integer anoMorte) {
        this.anoMorte = anoMorte;
    }

    @Override
    public String toString() {
        return "Autor: '" + nome + '\'' +
                ", Nascimento: " + anoNascimento +
                ", Morte: " + anoMorte ;
    }
}
