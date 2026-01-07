package br.com.alura.Literalura.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AutorDTO {
    private String name;

    @JsonProperty("birth_year")
    private Integer birth_year;

    @JsonProperty("death_year")
    private Integer death_year;

    public AutorDTO() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBirth_year() {
        return birth_year;
    }

    public void setBirth_year(Integer birth_year) {
        this.birth_year = birth_year;
    }

    public Integer getDeath_year() {
        return death_year;
    }

    public void setDeath_year(Integer death_year) {
        this.death_year = death_year;
    }

    public Integer getBirthYear() {
        return birth_year;
    }

    public Integer getDeathYear() {
        return death_year;
    }

    @Override
    public String toString() {
        return "AutorDTO{name='" + name + "', birth_year=" + birth_year + ", death_year=" + death_year + "}";
    }
}
