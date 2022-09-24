package br.inatel.aula_lab;

import br.inatel.aula_lab.model.Curso;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.context.ConfigurableWebEnvironment;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class CursoControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    void deveListarCursos() {

        webTestClient.get()
                .uri("/curso")
                .exchange()
                .expectStatus()
                    .isOk()
                .expectBody()
                    .returnResult();
    }
    @Test
    void dadoCursoIDValido_quandoGetCursoId_entaoRespondeComCursoValido(){
        Long idValido=1L;
        Curso cursoRespondido=webTestClient.get()
                .uri("/curso/"+idValido)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Curso.class)
                    .returnResult()
                    .getResponseBody();
        //assertNotNull(cursoRespondido);
        //assertEquals(cursoRespondido.getId(),idValido);

        assertThat(cursoRespondido).isNotNull();
        assertThat(idValido).isEqualTo(cursoRespondido.getId());

    }
    @Test
    void dadoCursoIDInvalido_quandoGetCursoID_entaoRespondeComStatusNotFound(){

        Long cursoIdValido=99L;

        webTestClient.get()
                .uri("/curso/"+cursoIdValido)
                .exchange()
                .expectStatus()
                    .isNotFound();
    }
    @Test
    void dadoNovoCurso_quandoPostCurso_entaorRespondeComStatusCreatedECursoValido(){
        Curso novoCurso=new Curso();
        novoCurso.setDescricao("Testando Rest com  spring webflux");
        novoCurso.setCargaHoraria(120);

        Curso cursoRespondido=webTestClient.post()
                .uri("/curso ")
                .bodyValue(novoCurso)
                .exchange()
                .expectStatus()
                    .isCreated()
                .expectBody(Curso.class)
                    .returnResult()
                    .getResponseBody();

        assertThat(cursoRespondido).isNotNull();
        assertThat(cursoRespondido.getId()).isNotNull();
    }

    @Test
    public void dadoUmCursoExistente_quandoPutCurso_entaoRespondeComStatusAcceptedECursoAtualizado() {
        Curso cursoExistente = new Curso();
        cursoExistente.setId(1L);
        cursoExistente.setDescricao("nova descrição do test");
        cursoExistente.setCargaHoraria(150);
        webTestClient.put()
                .uri("/curso")
                .bodyValue(cursoExistente)
                .exchange()
                .expectStatus()
                    .isAccepted()
                .expectBody()
                    .isEmpty();

    }
    @Test
    public void dadoUmIDDeUmCursoExistente_quandoDeletePorIdDoCurso_entaoRemoverCursoERetornarStatusOk() {
        Long idCursoASerRemovido = 2L;
        webTestClient.delete()
                .uri("/curso/" + idCursoASerRemovido )
                .exchange()
                .expectStatus()
                    .isNoContent()
                .expectBody()
                    .isEmpty();
    }
    @Test
    public void dadoUmIDDeUmCursoInexistente_quandoDeletePorIdDoCurso_entaoResponderComStatusNoContentELancarResponseStatusException() {
        Long idCursoASerRemovido = 99L;
        webTestClient.delete()
                .uri("/curso/"+idCursoASerRemovido)
                .exchange()
                .expectStatus()
                    .isNotFound();

    }

}



