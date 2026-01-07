package br.com.alura.Literalura.service;

import br.com.alura.Literalura.DTO.GutendexResponseDTO;
import br.com.alura.Literalura.DTO.LivroDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class ApiDeLivrosService {
    private final RestTemplate restTemplate = new RestTemplate();

    public String buscarLivrosPorTitulo(String titulo) {
        String url = "https://gutendex.com/books";
        String uri = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("search", titulo)
                .toUriString();
        String respostaJson = restTemplate.getForObject(uri, String.class);
        return respostaJson;

    }

    public LivroDTO buscarLivroPorTitulo(String titulo) {
        String url = "https://gutendex.com/books?search=" + titulo.replace(" ", "+");
        GutendexResponseDTO resposta = restTemplate.getForObject(url, GutendexResponseDTO.class);

        if (resposta != null && resposta.getResults() != null && !resposta.getResults().isEmpty()) {
            return resposta.getResults().get(0);
        }
        return null;
    }
}
