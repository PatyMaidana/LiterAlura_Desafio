package br.com.alura.Literalura.Principal;

import br.com.alura.Literalura.DTO.AutorDTO;
import br.com.alura.Literalura.DTO.LivroDTO;
import br.com.alura.Literalura.model.Autor;
import br.com.alura.Literalura.model.Language;
import br.com.alura.Literalura.model.Livro;
import br.com.alura.Literalura.repository.AutorRepository;
import br.com.alura.Literalura.repository.LivroRepository;
import br.com.alura.Literalura.service.ApiDeLivrosService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class Principal {

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final ApiDeLivrosService apiDeLivrosService;
    private final Scanner leitura = new Scanner(System.in);

    public Principal(LivroRepository livroRepository,
                     AutorRepository autorRepository,
                     ApiDeLivrosService apiDeLivrosService) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
        this.apiDeLivrosService = apiDeLivrosService;
    }

    public void exibeMenu() {
        String opcao = "";
        while (!"9".equals(opcao)) {
            var menu = """
                    *** Escolha sua opção pelo número ***

                    1- Buscar Livro Pelo Título
                    2- Listar Livros Registrados
                    3- Listar Autores Registrados
                    4- Listar Autores Vivos Em Um Determinado Ano
                    5- Listar Livros Pelo Idioma

                    9 - Sair
                    """;
            System.out.println(menu);
            opcao = leitura.nextLine().trim();
            switch (opcao) {
                case "1" -> cadastrarLivros();
                case "2" -> listarLivros();
                case "3" -> listarAutores();
                case "4" -> listarAutoresVivosPorAno();
                case "5" -> listarLivrosPorIdioma();
                case "9" -> System.out.println("Encerrando a aplicação!");
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    public void cadastrarLivros() {
        System.out.println("Insira o nome do livro que deseja procurar:");
        String nomeLivro = leitura.nextLine().trim();

        if (apiDeLivrosService == null) {
            System.out.println("Serviço de livros indisponível.");
            return;
        }

        LivroDTO livroDTO = apiDeLivrosService.buscarLivroPorTitulo(nomeLivro);
        if (livroDTO == null) {
            System.out.println("Livro não encontrado pela API.");
            return;
        }

        System.out.println("DEBUG - LivroDTO recebido:");
        System.out.println("  title = " + livroDTO.getTitle());
        System.out.println("  authors = " + livroDTO.getAuthors());
        System.out.println("  languages = " + livroDTO.getLanguages());
        System.out.println("  downloadCount = " + livroDTO.getDownloadCount());

        String titulo = livroDTO.getTitle();
        List<AutorDTO> autoresDto = livroDTO.getAuthors();
        List<String> languages = livroDTO.getLanguages();
        Integer downloadCountRaw = livroDTO.getDownloadCount();
        Long downloadCount = (downloadCountRaw == null) ? 0L : downloadCountRaw.longValue();
        if (downloadCountRaw == null) {
            System.out.println("Aviso: download_count retornou null. Será usado 0 como padrão.");
        }

        StringBuilder faltando = new StringBuilder();
        if (titulo == null || titulo.isBlank()) faltando.append("titulo ");
        if (autoresDto == null || autoresDto.isEmpty()) faltando.append("autor ");
        if (languages == null || languages.isEmpty()) faltando.append("language ");

        if (faltando.length() > 0) {
            System.out.println("Erro: Dados do livro incompletos (" + faltando.toString().trim() + "). Não será salvo.");
            return;
        }

        AutorDTO autorDto = autoresDto.get(0);
        String autorNome = autorDto.getName();

        Optional<Autor> existente = autorRepository.findByNome(autorNome);
        Autor autor = existente.map(a -> {
            boolean changed = false;
            if (a.getAnoNascimento() == null && autorDto.getBirth_year() != null) {
                a.setAnoNascimento(autorDto.getBirth_year());
                changed = true;
            }
            if (a.getAnoMorte() == null && autorDto.getDeath_year() != null) {
                a.setAnoMorte(autorDto.getDeath_year());
                changed = true;
            }
            return changed ? autorRepository.save(a) : a;
        }).orElseGet(() -> autorRepository.save(new Autor(autorDto)));

        Language idioma;
        try {
            idioma = Language.fromCode(languages.get(0));
        } catch (Exception e) {
            System.out.println("Idioma inválido recebido: " + languages.get(0));
            System.out.println("Erro: Não será salvo.");
            return;
        }

        Livro livro = new Livro();
        livro.setTitulo(titulo);
        livro.setAutor(autor);
        livro.setIdioma(idioma);
        livro.setDownloadCount(downloadCount);

        livroRepository.save(livro);

        System.out.println("Livro salvo: " + titulo);
    }

    private void listarLivros() {
        List<Livro> livros = livroRepository.findAll();
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro cadastrado.");
        } else {
            livros.forEach(System.out::println);
        }
    }

    private void listarAutores() {
        List<Autor> autores = autorRepository.findAll();
        if (autores.isEmpty()) {
            System.out.println("Nenhum autor cadastrado.");
            return;
        } else {
            autores.forEach(System.out::println);
        }
    }

    private void listarAutoresVivosPorAno() {
        System.out.println("Insira o ano que deseja procurar:");
        try {
            var ano = Integer.parseInt(leitura.nextLine().trim());
            List<Autor> vivos = autorRepository.findAutoresVivosEmAno(ano);
            if (vivos.isEmpty()) {
                System.out.println("Nenhum autor vivo encontrado no ano: " + ano);
                return;
            }
            System.out.println("Autores vivos no ano " + ano + ":");
            vivos.forEach(System.out::println);
        } catch (NumberFormatException e) {
            System.out.println("Ano inválido.");
        }
    }

    private void listarLivrosPorIdioma() {
        System.out.println("Insira o idioma para efetuar a busca:");
        for (Language lang : Language.values()) {
            System.out.println(lang.getCode() + " - " + getNomeAmigavel(lang));
        }
        System.out.print("Digite a sigla do idioma desejado: ");
        String sigla = leitura.nextLine().trim();

        try {
            Language idiomaEscolhido = Language.fromCode(sigla);
            List<Livro> livros = livroRepository.findByIdioma(idiomaEscolhido);
            if (livros.isEmpty()) {
                System.out.println("Nenhum livro encontrado para o idioma selecionado.");
            } else {
                System.out.println("Livros encontrados:");
                livros.forEach(l -> System.out.println(l.getTitulo()));
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Sigla inválida! Tente novamente.");
        }
    }

    private String getNomeAmigavel(Language lang) {
        return switch (lang) {
            case PORTUGUES -> "português";
            case INGLES -> "inglês";
            case FRANCES -> "francês";
            case ESPANHOL -> "espanhol";
            default -> lang.name().toLowerCase();
        };
    }
}
