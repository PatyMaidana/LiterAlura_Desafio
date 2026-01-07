package br.com.alura.Literalura.DTO;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LivroDTO {
    private Integer id;
    private String title;
    private List<AutorDTO> authors;
    private List<String> languages;

    @JsonProperty("download_count")
    private Integer downloadCount;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public List<AutorDTO> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AutorDTO> authors) {
        this.authors = authors;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }
}