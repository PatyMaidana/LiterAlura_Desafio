package br.com.alura.Literalura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Enumerated(EnumType.STRING)
    private Language idioma;

    @Column(name = "download_count")
    private Long downloadCount;

    @ManyToOne(optional = false)
    @JoinColumn(name = "autor_id")
    private Autor autor;

    public Livro() {}

    public Livro(String titulo, Autor autor, Language idioma, Long downloadCount) {
        this.titulo = titulo;
        this.autor = autor;
        this.idioma = idioma;
        this.downloadCount = downloadCount;
    }

    public Long getId() {
        return id;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public Language getIdioma() {
        return idioma;
    }
    public void setIdioma(Language idioma) {
        this.idioma = idioma;
    }
    public Long getDownloadCount() {
        return downloadCount;
    }
    public void setDownloadCount(Long downloadCount) {
        this.downloadCount = downloadCount;
    }
    public Autor getAutor() {
        return autor;
    }
    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        String autorInfo;
        if (autor == null) {
            autorInfo = "N/A";
        } else {
            Integer anoNasc = autor.getAnoNascimento();
            Integer anoMorte = autor.getAnoMorte();
            String morte = (anoMorte == null) ? "" : ", Morte: " + anoMorte;
            autorInfo = "'" + autor.getNome() + "', Nascimento: " + anoNasc + morte;
        }

        return "Título: '" + titulo + '\'' +
                ", Idioma: " + idioma +
                ", Nº de Donwloads: " + downloadCount +
                ", Autor: " + autorInfo;
    }
}
