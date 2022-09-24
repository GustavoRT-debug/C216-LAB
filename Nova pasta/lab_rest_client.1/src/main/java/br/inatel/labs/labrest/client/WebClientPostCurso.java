package br.inatel.labs.labrest.client;

import br.inatel.labs.labrest.client.model.Curso;
import org.springframework.web.reactive.function.client.WebClient;

public class WebClientPostCurso {
    public static void main(String[] args) {
        Curso novoCurso= new Curso();
        novoCurso.setDescricao("Dominando spring webflux");
        novoCurso.setCargaHoraria(80);

        Curso cursoCriado= WebClient.create("http://localhost:8080")
                .post()
                .uri("/curso")
                .bodyValue(novoCurso)
                .retrieve()
                .bodyToMono(Curso.class) //indica que nd é esperado no corpo do response
                .block();

        System.out.println("curso criado: ");
        System.out.println(cursoCriado); //Id
    }
}
