package br.com.alura.Literalura.model;

public enum Language {
    ESPANHOL("es"),
    INGLES("en"),
    FRANCES("fr"),
    PORTUGUES("pt");

    private final String code;

    Language(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static Language fromCode(String code) {
        for (Language lang : Language.values()) {
            if (lang.getCode().equalsIgnoreCase(code)) {
                return lang;
            }
        }
        throw new IllegalArgumentException("Código de idioma desconhecido: " + code);
    }
}